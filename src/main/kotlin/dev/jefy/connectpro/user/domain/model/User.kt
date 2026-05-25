package dev.jefy.connectpro.user.domain.model

import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.EncodedPassword
import dev.jefy.connectpro.user.domain.vo.UserId
import dev.jefy.connectpro.user.domain.vo.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "users")
open class User(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: UserId = UserId.generate(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email"))
    var email: Email,
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password"))
    var password: EncodedPassword,
    @Enumerated(EnumType.STRING)
    var role: UserRole,
    var name: String,
    var image: String? = null,
    var isVerified: Boolean = false
) {
    
    init {
        require(name.isNotBlank()) { "name must not be blank" }
    }

    fun setVerified() { isVerified = true }

    fun updatePassword(password: EncodedPassword) {
        this.password = password
    }

    fun updateEmail(email: Email) { this.email = email }

    fun updateName(name: String) {
        require(name.isNotBlank()) { "name must not be blank" }
        this.name = name
    }

    fun changeProfileImage(image: Image) { this.image = image.value }
}