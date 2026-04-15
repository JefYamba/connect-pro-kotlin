package dev.jefy.connectpro.user.applicaion.service;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.user.domain.model.AuthUser;
import dev.jefy.connectpro.user.domain.model.User;
import dev.jefy.connectpro.user.domain.repository.UserRepository;
import dev.jefy.connectpro.user.domain.vo.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jôph Yamba
 */
@Slf4j
@NullMarked
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PortfolioClient portfolioClient;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(new Email(username))
                .orElseThrow(() -> new UsernameNotFoundException("AppUserDetails not found"));
        
        var portfolioData = portfolioClient.getPortfolioSummary(user.getId())
                .orElse(null);
        
        log.info("Loaded user: {} with portfolio data: {}", user.getEmail(), portfolioData);
        
        return AuthUser.fromUser(user, portfolioData);
    }
}
