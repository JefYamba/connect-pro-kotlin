package dev.jefy.connectpro.engagement.domain.repository

import org.springframework.stereotype.Repository
import java.util.UUID
import dev.jefy.connectpro.engagement.domain.Like
import dev.jefy.connectpro.engagement.domain.vo.LikeId
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface LikeRepository : JpaRepository<Like, LikeId> {
    fun countByIdServiceId(serviceId: UUID): Long
}