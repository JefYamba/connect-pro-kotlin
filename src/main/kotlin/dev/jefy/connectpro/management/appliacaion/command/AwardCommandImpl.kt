package dev.jefy.connectpro.management.appliacaion.command

import dev.jefy.connectpro.management.appliacaion.AwardAlreadyExistsException
import dev.jefy.connectpro.management.appliacaion.AwardAlreadyInUseException
import dev.jefy.connectpro.management.appliacaion.dtos.AwardRequest
import dev.jefy.connectpro.management.domain.Award
import dev.jefy.connectpro.management.domain.repository.AwardRepository
import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.HexColor
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.application.exceptions.AwardNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

/**
 * @author Jôph Yamba
 */
@Service
@Transactional
class AwardCommandImpl(
    private val awardRepo: AwardRepository,
    private val portfolioClient: PortfolioClient
) : AwardCommand {

    override fun create(request: AwardRequest): AwardId {
        
        if (awardRepo.existsByName(request.name)) throw AwardAlreadyExistsException()
        
        val award = Award(request.name, HexColor.of(request.color), request.description)
        awardRepo.save(award)
        return award.id
    }

    override fun update(awardId: AwardId, request: AwardRequest): AwardId {
        Assert.notNull(request, "request is null")

        val award = getAward(awardId)
        award.update(request.name, HexColor.of(request.color), request.description)
        awardRepo.save(award)
        return award.id
    }

    override fun delete(awardId: AwardId) {
        val award = getAward(awardId)
        if (portfolioClient.isAwardInUse(awardId)) throw AwardAlreadyInUseException()
        awardRepo.delete(award)
    }

    private fun getAward(awardId: AwardId): Award = awardRepo
        .findById(awardId)
        .orElseThrow { AwardNotFoundException() }
    
}