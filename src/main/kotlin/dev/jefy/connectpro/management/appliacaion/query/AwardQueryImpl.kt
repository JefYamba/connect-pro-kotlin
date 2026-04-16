package dev.jefy.connectpro.management.appliacaion.query

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.appliacaion.dtos.toResponse
import dev.jefy.connectpro.management.domain.repository.AwardRepository
import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.portfolio.application.exceptions.AwardNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Jôph Yamba
 */
@Service
@Transactional(readOnly = true)
class AwardQueryImpl(private val awardRepo: AwardRepository) : AwardQuery {

    override fun get(awardId: AwardId): AwardResponse = awardRepo
        .findById(awardId)
        .map{ it.toResponse() }
        .orElseThrow { AwardNotFoundException() }
    

    override fun getAll(): MutableList<AwardResponse> = awardRepo
        .findAll()
        .stream()
        .map{ it.toResponse()}
        .toList()
}
