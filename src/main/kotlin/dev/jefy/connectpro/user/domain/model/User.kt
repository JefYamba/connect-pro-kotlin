package dev.jefy.connectpro.user.domain.model

import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.EncodedPassword
import dev.jefy.connectpro.user.domain.vo.UserId
import dev.jefy.connectpro.user.domain.vo.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "users")
open class User(email: Email, password: EncodedPassword, role: UserRole, name: String) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: UserId = UserId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email"))
    var email: Email = email 
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password"))
    var password: EncodedPassword = password
        protected set

    @Enumerated(EnumType.STRING)
    var role: UserRole = role
        protected set

    var name: String = name
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "image"))
    var image: Image? = null
        protected set

    var isVerified: Boolean = false

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

    fun changeProfileImage(profileImage: Image) { this.image = profileImage }
}