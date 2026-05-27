package dev.jefy.connectpro.portfolio.domain.repository

import dev.jefy.connectpro.portfolio.domain.model.Social
import dev.jefy.connectpro.portfolio.domain.vo.SocialId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SocialLinkQueryRepository : JpaRepository<Social, SocialId> 