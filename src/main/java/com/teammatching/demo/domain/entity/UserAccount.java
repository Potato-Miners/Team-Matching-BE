package com.teammatching.demo.domain.entity;

import com.teammatching.demo.domain.AuditingField;
import com.teammatching.demo.domain.dto.RoleType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingField {

    @Id
    @Column(nullable = false, length = 50)
    private String userId;

    @Setter
    @Column(nullable = false)
    private String userPassword;

    @Setter
    @Column(length = 100)
    private String email;

    @Setter
    @Column(length = 100)
    private String nickname;

    @Setter
    private String memo;

    @Setter
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Setter
    private String refreshToken;

    @OrderBy("createdAt DESC")
    @ToString.Exclude
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private final Set<Post> posts = new LinkedHashSet<>();

    @OrderBy("createdAt DESC")
    @ToString.Exclude
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private final Set<Comment> comments = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private final Set<Admission> admissions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "adminUserAccount", cascade = CascadeType.ALL)
    private final Set<Team> teams = new LinkedHashSet<>();

    @Builder
    private UserAccount(String userId, String userPassword, String email, String nickname, String memo, RoleType role, String refreshToken) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.role = role;
        this.refreshToken = refreshToken;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;

    }
}
