package dev.jefy.connectpro.user.domain.model;


import org.springframework.util.Assert;

import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.user.domain.vo.Email;
import dev.jefy.connectpro.user.domain.vo.EncodedPassword;
import dev.jefy.connectpro.user.domain.vo.UserId;
import dev.jefy.connectpro.user.domain.vo.UserRole;
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
@Table(name = "users")
public class User {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private UserId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email"))
    private Email email;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private EncodedPassword password;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    private boolean isVerified;
    
    private String name;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "profile_image"))
    private ImageUrl profileImage;

    public User(Email email, EncodedPassword password, UserRole role, String name) {
        Assert.notNull(email, "value cannot be null");
        Assert.notNull(password, "passwordHash cannot be null");
        Assert.notNull(role, "role cannot be null");
        Assert.notNull(name, "name cannot be null");
        this.id = UserId.generate();
        this.email = email;
        this.password = password;
        this.role = role;
        this.isVerified = false;
        this.name = name;
    }

    public void setVerified() {
        this.isVerified = true;
    }

    public void updatePassword(EncodedPassword password) {
        Assert.notNull(password, "value cannot be null");
        this.password = password;
    }
    
    public void updateEmail(Email email) {
        Assert.notNull(email, "value cannot be null");
        this.email = email;
    }
    
    public void updateName(String name) {
        Assert.notNull(name, "value cannot be null");
        this.name = name;
    }
    public void changeProfileImage(ImageUrl profileImage) {
        this.profileImage = profileImage;
    }
}
