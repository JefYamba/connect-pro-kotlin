package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.dtos.*
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

/**
 * @author Jôph Yamba
 */
interface PortfolioCommand {
    fun create(request: CreatePortfolioRequest): PortfolioId
    fun update(portfolioId: PortfolioId, request: UpdatePortfolioRequest,): PortfolioId
    fun updateContact(portfolioId: PortfolioId, request: ContactData): PortfolioId
    fun updateLocation(portfolioId: PortfolioId, request: LocationData): PortfolioId

    @Throws(IOException::class)
    fun setCoverImage(portfolioId: PortfolioId, image: MultipartFile): PortfolioId

    @Throws(IOException::class)
    fun deleteCoverImage(portfolioId: PortfolioId): PortfolioId
    fun addSocialLink(portfolioId: PortfolioId, social: SocialData): PortfolioId
    fun deleteSocialLink(portfolioId: PortfolioId, platform: SocialPlatform): PortfolioId
    fun activate(portfolioId: PortfolioId): PortfolioId
    fun deactivate(portfolioId: PortfolioId): PortfolioId
    fun bloc(portfolioId: PortfolioId): PortfolioId
    fun setBadge(portfolioId: PortfolioId, badgeId: BadgeId): PortfolioId
}
