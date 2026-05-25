package dev.jefy.connectpro.portfolio.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "tags")
open class Tag(
    @Id
    var id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    var name: String,
){
    init {
        require(name.isNotBlank()) { "tag must not be blank" }
    }
}