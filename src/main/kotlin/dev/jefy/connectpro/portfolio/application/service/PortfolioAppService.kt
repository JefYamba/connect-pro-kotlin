package dev.jefy.connectpro.portfolio.application.service

import dev.jefy.connectpro.portfolio.application.dtos.ContactInfoData
import dev.jefy.connectpro.portfolio.application.dtos.GeneralInfoRequest
import dev.jefy.connectpro.portfolio.application.dtos.PortfolioRequest
import dev.jefy.connectpro.portfolio.application.dtos.SocialLinkData
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.SocialLinkAlreadyExistsException
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.repository.SocialLinkQueryRepository
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.user.domain.vo.Email
import org.springframework.stereotype.Service

/**
 * @author Jôph Yamba
 */
@Service
class PortfolioAppService(
    private val portfolioRepos: PortfolioRepository,
    private val socialLinkRepo: SocialLinkQueryRepository
) {

    fun checkConflict(request: PortfolioRequest) {
        val phones = listOfNotNull(
            request.contactInfo.phone1,
            request.contactInfo.phone2
        )

        if (phones.isEmpty()) {
            throw IllegalArgumentException("At least one phone number is required")
        }

        val isConflict = portfolioRepos.existsPortfolioConflict(
            request.generalInfo.name,
            Email.of(request.contactInfo.email),
            phones
        )

        if (isConflict) throw PortfolioAlreadyExistsException()
    }

    fun checkSocialLinksConflict(socialLinks: List<SocialLinkData>) {
        val urls = socialLinks.mapNotNull { it.url }

        if (urls.isEmpty()) return

        val exists = socialLinkRepo.existsByUrls(urls)

        if (exists) {
            throw SocialLinkAlreadyExistsException()
        }
    }

    fun checkGeneralInfoConflict(portfolioId: PortfolioId, request: GeneralInfoRequest) {
        val isConflict = portfolioRepos.existsNameConflict(portfolioId, request.name)
        if (isConflict) throw PortfolioAlreadyExistsException()
    }

    fun checkContactInfoConflict(portfolioId: PortfolioId, request: ContactInfoData) {
        val phones = listOfNotNull(
            request.phone1,
            request.phone2
        )

        if (phones.isEmpty()) {
            throw IllegalArgumentException("At least one phone number is required")
        }

        val isConflict = portfolioRepos.existsContactConflict(
            portfolioId,
            request.email,
            phones
        )

        if (isConflict) throw PortfolioAlreadyExistsException()
    }
}

