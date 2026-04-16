package dev.jefy.connectpro.management.domain.repository

import dev.jefy.connectpro.management.domain.Award
import dev.jefy.connectpro.management.domain.vo.AwardId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AwardRepository : JpaRepository<Award, AwardId> {
    fun existsByName(name: String): Boolean
}