package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.dtos.*
import dev.jefy.connectpro.portfolio.application.exceptions.BadgeNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioNotFoundException
import dev.jefy.connectpro.portfolio.application.service.PortfolioAppService
import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.portfolio.domain.model.Social
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform
import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.shared.infrastructure.annotations.CommandService
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService
import dev.jefy.connectpro.shared.infrastructure.messaging.EmailService
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.PortfolioCreatedEmailStrategy
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@CommandService
class PortfolioCommandImpl(
    private val portfolioRepo: PortfolioRepository,
    private val portfolioAppService: PortfolioAppService,
    private val userClient: UserClient,
    private val emailService: EmailService,
    private val imageService: ImageService,
    private val managementClient: ManagementClient
) : PortfolioCommand {

    override fun create(request: CreatePortfolioRequest): PortfolioId {
        val userId = UserId(request.userId)
        val userData = userClient.getById(userId)

        require(!portfolioRepo.existsByUserId(userId)) {
            "Portfolio already exists"
        }

        portfolioAppService.checkConflict(request)
        portfolioAppService.checkSocialLinksConflict(request.socials)

        val portfolio = Portfolio(
            userId = UserId(request.userId),
            name = request.name,
            bio = request.bio,
            details = request.details,
            contact = request.contact.toContactInfo(),
            location = request.location.toDomain(),
        )
        val socials = request.socials.map { 
            Social(platform = it.platform, url = it.url, portfolio = portfolio) 
        }.toMutableSet()
        portfolio.socials = socials
        portfolioRepo.save(portfolio)

        emailService.send(Email(userData.email), PortfolioCreatedEmailStrategy(userData.name))

        return portfolio.id
    }

    override fun update(
        portfolioId: PortfolioId, request: UpdatePortfolioRequest,
    ): PortfolioId =
        getPortfolio(portfolioId)
            .apply {
                portfolioAppService.checkGeneralInfoConflict(portfolioId=portfolioId, name=name)
                update(name = request.name, bio = request.bio, details = request.details)
            }
            .also { portfolioRepo.save(it) }
            .id
    
    override fun updateContact(
        portfolioId: PortfolioId, request: ContactData
    ): PortfolioId = getPortfolio(portfolioId)
            .apply {
                portfolioAppService.checkContactInfoConflict(portfolioId, request)
                updateContact(request.toContactInfo())
            }
            .also { portfolioRepo.save(it) }
            .id

    override fun updateLocation(
        portfolioId: PortfolioId, request: LocationData
    ): PortfolioId = getPortfolio(portfolioId)
            .apply { updateLocation(request.toDomain()) }
            .also { portfolioRepo.save(it) }
            .id

    @Throws(IOException::class)
    override fun setCoverImage(
        portfolioId: PortfolioId, image: MultipartFile
    ): PortfolioId =
        getPortfolio(portfolioId)
            .apply {
                val imageUrl = imageService.save(image)
                setCoverImage(imageUrl)
            }
            .also { portfolioRepo.save(it) }
            .id

    @Throws(IOException::class)
    override fun deleteCoverImage(portfolioId: PortfolioId): PortfolioId = getPortfolio(portfolioId)
            .apply {
                coverImage?.let { imageService.delete(Image(it)) }
                deleteCoverImage()
            }
            .also { portfolioRepo.save(it) }
            .id

    override fun addSocialLink(
        portfolioId: PortfolioId,
        social: SocialData
    ): PortfolioId =
        getPortfolio(portfolioId)
            .apply { addSocial(Social(platform = social.platform, url = social.url, portfolio = this)) }
            .also { portfolioRepo.save(it) }
            .id

    override fun deleteSocialLink(
        portfolioId: PortfolioId,
        platform: SocialPlatform
    ): PortfolioId {
        val portfolio = getPortfolio(portfolioId)
        println("before delete: $platform")
        println(portfolio.socials)
        portfolio.deleteSocial(platform) 
        val saved = portfolioRepo.save(portfolio) 
        println("after delete: $platform")
        println(saved.socials)
        return portfolio.id
    }
        

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