package dev.jefy.connectpro.engagement.domain.repository

import dev.jefy.connectpro.engagement.domain.Like
import dev.jefy.connectpro.engagement.domain.vo.LikeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LikeRepository : JpaRepository<Like, LikeId> {
    fun countByIdServiceId(serviceId: UUID): Long
    
    @Modifying
    @Query("delete from Like like where like.id.serviceId = :serviceId")
    fun deleteAllByServiceId(serviceId: UUID)
}