package dev.jefy.connectpro.user.domain.model;

import org.springframework.util.Assert;

import java.time.Instant;

import dev.jefy.connectpro.user.domain.vo.OtpCode;
import dev.jefy.connectpro.user.domain.vo.TokenId;
import dev.jefy.connectpro.user.domain.vo.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tokens")
public class Token {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private TokenId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "otp_code"))
    private OtpCode code;
    
    private Instant createdAt;
    
    private Instant expireAt;
    
    private Instant validatedAt;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;

    public Token(UserId userId, OtpCode code, long verificationTokenExpirationTimeInMinutes) {
        Assert.notNull(userId, "reviewerId cannot be null");
        Instant createdDate = Instant.now();
        Instant expiresAt = createdDate.plusSeconds(verificationTokenExpirationTimeInMinutes * 60);
        this.id = TokenId.generate();
        this.code = code;
        this.createdAt = createdDate;
        this.expireAt = expiresAt;
        this.validatedAt = null;
        this.userId = userId;
    }
    
    public boolean isExpired() {
        return Instant.now().isAfter(expireAt);
    }

    public void validate() {
        this.validatedAt = Instant.now();
    }

    public boolean isNotValidated() {
        return validatedAt == null;
    }
}
