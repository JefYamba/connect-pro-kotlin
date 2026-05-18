package dev.jefy.connectpro.portfolio.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tags")
open class Tag(name: String){
    @Id
    var id: UUID = UUID.randomUUID()
        protected set
    var name: String = name
        protected set
    @ManyToMany(mappedBy = "tags")
    var services: MutableSet<PService> = mutableSetOf()
    protected set
    
    @ManyToMany(mappedBy = "tags")
    var jobPosts: MutableSet<JobPost> = mutableSetOf()
    protected set
    
    init {
        require(name.isNotBlank()) { "tag must not be blank" }
    }
}