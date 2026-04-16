package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.dtos.ContactInfoData
import dev.jefy.connectpro.portfolio.application.dtos.GeneralInfoRequest
import dev.jefy.connectpro.portfolio.application.dtos.LocationInfoData
import dev.jefy.connectpro.portfolio.application.dtos.PortfolioRequest
import dev.jefy.connectpro.portfolio.application.dtos.ProfessionalInfoRequest
import dev.jefy.connectpro.portfolio.application.dtos.SocialLinkData
import dev.jefy.connectpro.portfolio.application.exceptions.BadgeNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioNotFoundException
import dev.jefy.connectpro.portfolio.application.service.PortfolioAppService
import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService
import dev.jefy.connectpro.shared.infrastructure.messaging.EmailService
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.PortfolioCreatedEmailStrategy
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
@Transactional
class PortfolioCommandImpl(
    private val portfolioRepo: PortfolioRepository,
    private val portfolioAppService: PortfolioAppService,
    private val userClient: UserClient,
    private val emailService: EmailService,
    private val imageService: ImageService,
    private val managementClient: ManagementClient
) : PortfolioCommand {

    override fun create(request: PortfolioRequest): PortfolioId {
        val userId = UserId(request.userId)
        val userData = userClient.getData(userId)

        require(!portfolioRepo.existsByUserId(userId)) {
            "Portfolio already exists"
        }

        portfolioAppService.checkConflict(request)
        portfolioAppService.checkSocialLinksConflict(request.socialLinks)

        val portfolio = Portfolio(
            userId,
            request.type,
            request.generalInfo.toDomain(),
            request.professionalInfo.toDomain(),
            request.contactInfo.toContactInfo(),
            request.locationInfo.toDomain(),
            request.socialLinks
        )

        portfolioRepo.save(portfolio)

        emailService.sendEmail(Email(userData.email), PortfolioCreatedEmailStrategy(userData.name))

        return portfolio.id
    }

    override fun updateGeneralInfo(
        portfolioId: PortfolioId, request: GeneralInfoRequest
    ): PortfolioId =
        getPortfolio(portfolioId)
            .apply {
                portfolioAppService.checkGeneralInfoConflict(portfolioId, request)
                updateGeneralInfo(request.toDomain())
            }
            .also { portfolioRepo.save(it) }
            .id

    override fun updateProfessional(
        portfolioId: PortfolioId, request: ProfessionalInfoRequest
    ): PortfolioId = getPortfolio(portfolioId)
            .apply { updateProfessionalInfo(request.toDomain()) }
            .also { portfolioRepo.save(it) }
            .id

    override fun updateContactInfo(
        portfolioId: PortfolioId, request: ContactInfoData
    ): PortfolioId = getPortfolio(portfolioId)
            .apply {
                portfolioAppService.checkContactInfoConflict(portfolioId, request)
                updateContactInfo(request.toContactInfo())
            }
            .also { portfolioRepo.save(it) }
            .id

    override fun updateLocationInfo(
        portfolioId: PortfolioId, request: LocationInfoData
    ): PortfolioId = getPortfolio(portfolioId)
            .apply { updateLocationInfo(request.toDomain()) }
            .also { portfolioRepo.save(it) }
            .id

    @Throws(IOException::class)
    override fun setCoverImage(
        portfolioId: PortfolioId, image: MultipartFile
    ): PortfolioId =
        getPortfolio(portfolioId)
            .apply {
                val imageUrl = imageService.save(image)
                setCoverImageUrl(imageUrl)
            }
            .also { portfolioRepo.save(it) }
            .id

    @Throws(IOException::class)
    override fun deleteCoverImage(portfolioId: PortfolioId): PortfolioId = getPortfolio(portfolioId)
            .apply {
                generalInfo.coverImageUrl?.let { imageService.delete(it) }
                deleteCoverImageUrl()
            }
            .also { portfolioRepo.save(it) }
            .id

    override fun addSocialLink(
        portfolioId: PortfolioId,
        linkData: SocialLinkData
    ): PortfolioId =
        getPortfolio(portfolioId)
            .apply { addSocialLink(linkData) }
            .also { portfolioRepo.save(it) }
            .id

    override fun deleteSocialLink(
        portfolioId: PortfolioId,
        socialLinkId: SocialLinkId
    ): PortfolioId =
        getPortfolio(portfolioId)
            .apply { deleteSocialLink(socialLinkId) }
            .also { portfolioRepo.save(it) }
            .id

    override fun activate(portfolioId: PortfolioId): PortfolioId =
        getPortfolio(portfolioId)
            .apply { activate() }
            .also { portfolioRepo.save(it) }
            .id

    override fun deactivate(portfolioId: PortfolioId): PortfolioId =
        getPortfolio(portfolioId)
            .apply { deactivate() }
            .also { portfolioRepo.save(it) }
            .id

    override fun bloc(portfolioId: PortfolioId): PortfolioId =
        getPortfolio(portfolioId)
            .apply { block() }
            .also { portfolioRepo.save(it) }
            .id

    override fun changeType(
        portfolioId: PortfolioId,
        type: PortfolioType
    ): PortfolioId {
        return getPortfolio(portfolioId)
            .apply { changeType(type) }
            .also { portfolioRepo.save(it) }
            .id
    }

    override fun setBadge(portfolioId: PortfolioId, badgeId: BadgeId): PortfolioId {

        if (managementClient.notExistsBadge(badgeId)) throw BadgeNotFoundException()

        return getPortfolio(portfolioId)
            .apply { addBadgeId(badgeId) }
            .also { portfolioRepo.save(it) }
            .id
    }

    private fun getPortfolio(portfolioId: PortfolioId): Portfolio = portfolioRepo
        .findById(portfolioId).orElseThrow { PortfolioNotFoundException() }
    
}