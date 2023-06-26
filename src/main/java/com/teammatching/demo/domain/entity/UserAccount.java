package com.teammatching.demo.domain.entity;

import com.teammatching.demo.domain.AuditingField;
import lombok.*;

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
    private final Set<JoinTeam> joinTeams = new LinkedHashSet<>();

    @Builder
    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }
}
