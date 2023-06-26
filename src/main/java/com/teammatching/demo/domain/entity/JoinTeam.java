package com.teammatching.demo.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class JoinTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private boolean approval;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false)
    private Team team;

    @Builder
    private JoinTeam(UserAccount userAccount, Team team) {
        this.userAccount = userAccount;
        this.team = team;
    }
}
