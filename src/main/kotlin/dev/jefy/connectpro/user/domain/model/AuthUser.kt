package dev.jefy.connectpro.user.domain.model

import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import dev.jefy.connectpro.user.domain.vo.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.security.Principal
import java.util.*

data class AuthUser(
    val id: UUID,
    val fullname: String,
    val email: String,
    private val password: String,
    val image: String?,
    val portfolio: PortfolioData?,
    val role: UserRole,
    private val isVerified: Boolean
) : UserDetails, Principal {

    override fun getName(): String = email
    override fun getAuthorities(): Collection<GrantedAuthority> = setOf(SimpleGrantedAuthority(role.name))
    override fun getPassword(): String = password
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = portfolio?.active ?: true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = isVerified
}

fun User.toAuthUser(portfolio: PortfolioData?, resolver: ImageUrlResolver): AuthUser = AuthUser(
    id = this.id.value,
    fullname = this.name,
    email = this.email.value,
    password = this.password.value,
    image = resolver.resolve(this.image),
    portfolio = portfolio,
    role = this.role,
    isVerified = this.isVerified
)