package com.teammatching.demo.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class TeamUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false)
    private Team team;

    @Builder
    private TeamUser(UserAccount userAccount, Team team) {
        this.userAccount = userAccount;
        this.team = team;
    }
}
