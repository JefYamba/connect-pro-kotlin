package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.dtos.*
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

/**
 * @author Jôph Yamba
 */
interface PortfolioCommand {
    fun create(request: PortfolioRequest): PortfolioId
    fun updateGeneralInfo(portfolioId: PortfolioId, request: GeneralInfoRequest): PortfolioId
    fun updateProfessional(portfolioId: PortfolioId, request: ProfessionalInfoRequest): PortfolioId
    fun updateContactInfo(portfolioId: PortfolioId, request: ContactInfoData): PortfolioId
    fun updateLocationInfo(portfolioId: PortfolioId, request: LocationInfoData): PortfolioId

    @Throws(IOException::class)
    fun setCoverImage(portfolioId: PortfolioId, image: MultipartFile): PortfolioId

    @Throws(IOException::class)
    fun deleteCoverImage(portfolioId: PortfolioId): PortfolioId
    fun addSocialLink(portfolioId: PortfolioId, linkData: SocialLinkData): PortfolioId
    fun deleteSocialLink(portfolioId: PortfolioId, socialLinkId: SocialLinkId): PortfolioId
    fun activate(portfolioId: PortfolioId): PortfolioId
    fun deactivate(portfolioId: PortfolioId): PortfolioId
    fun bloc(portfolioId: PortfolioId): PortfolioId
    fun changeType(portfolioId: PortfolioId, type: PortfolioType): PortfolioId
    fun setBadge(portfolioId: PortfolioId, badgeId: BadgeId): PortfolioId
}
