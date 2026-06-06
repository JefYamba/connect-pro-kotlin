package dev.jefy.connectpro.portfolio.domain.repository

import dev.jefy.connectpro.portfolio.domain.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author  Jôph Yamba
 */
@Repository
interface TagRepository : JpaRepository<Tag, UUID> {
    fun findByNameIn(names: MutableCollection<String>): MutableSet<Tag>
    @Query("""
        SELECT t FROM Tag t 
        WHERE LOWER(t.name) IN :names
    """)
    fun findByNameInIgnoreCase(names: Collection<String>): MutableSet<Tag>
}
