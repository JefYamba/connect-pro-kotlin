package dev.jefy.connectpro.portfolio.domain.repository

import dev.jefy.connectpro.portfolio.domain.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author  Jôph Yamba
 */
@Repository
interface TagRepository : JpaRepository<Tag, UUID> {
    fun findByNameIn(names: MutableCollection<String>): MutableSet<Tag>
}
