package dev.jefy.connectpro.portfolio.application.service

import dev.jefy.connectpro.portfolio.application.dtos.ContactData
import dev.jefy.connectpro.portfolio.application.dtos.CreatePortfolioRequest
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioAlreadyExistsException
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

    fun checkConflict(request: CreatePortfolioRequest) {
        
        val isConflict = portfolioRepos.checkPortfolioConflict(
            request.name,
            Email.of(request.contact.email)
        )

        if (isConflict) throw PortfolioAlreadyExistsException()
    }

    fun checkGeneralInfoConflict(portfolioId: PortfolioId, name: String) {
        val isConflict = portfolioRepos.checkNameConflict(portfolioId, name)
        if (isConflict) throw PortfolioAlreadyExistsException()
    }

    fun checkContactInfoConflict(portfolioId: PortfolioId, request: ContactData) {
        val email = if (request.email != null) Email(request.email) else null;
        
        val isConflict = portfolioRepos.checkEmailConflict(portfolioId, email)

        if (isConflict) throw PortfolioAlreadyExistsException()
    }
}

