package dev.jefy.connectpro.management.domain.repository

import dev.jefy.connectpro.management.domain.Badge
import dev.jefy.connectpro.management.domain.vo.BadgeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BadgeRepository : JpaRepository<Badge, BadgeId> {
    fun existsByName(name: String): Boolean
}