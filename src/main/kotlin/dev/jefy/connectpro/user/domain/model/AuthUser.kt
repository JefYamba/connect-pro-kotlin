package dev.jefy.connectpro.user.domain.model;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.user.domain.vo.UserRole;

/**
 * @author Jôph Yamba
 */
@NullMarked
public record AuthUser(
        UUID id,
        String name,
        String email,
        String password,
        @Nullable String imageUrl,
        @Nullable PortfolioSummaryData portfolio,
        UserRole role,
        boolean isVerified
) implements UserDetails, Principal {


    @Override
    public String getName() {
        return email;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        if (this.portfolio == null) return true;
        return this.portfolio.active();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.isVerified;
    }


    public static UserDetails fromUser(User user, @Nullable PortfolioSummaryData portfolio) {
        return new AuthUser(
                user.getId().value(),
                user.getName(),
                user.getEmail().value(),
                user.getPassword().value(), 
                user.getProfileImage().value(),
                portfolio,
                user.getRole(),
                user.isVerified()
        );
    }
}

