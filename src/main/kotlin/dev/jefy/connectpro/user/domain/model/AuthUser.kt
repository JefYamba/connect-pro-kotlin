package dev.jefy.connectpro.user.domain.model

import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData
import dev.jefy.connectpro.user.domain.vo.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.security.Principal
import java.util.*

data class AuthUser(
    val id: UUID,
    val name: String,
    val email: String,
    private val password: String,
    val imageUrl: String?,
    val portfolio: PortfolioSummaryData?,
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

fun User.toAuthUser(portfolio: PortfolioSummaryData?): AuthUser = AuthUser(
    id = this.id.value,
    name = this.name,
    email = this.email.value,
    password = this.password.value,
    imageUrl = this.profileImage?.value,
    portfolio = portfolio,
    role = this.role,
    isVerified = this.isVerified
)