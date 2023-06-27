package com.teammatching.demo.domain.entity;

import com.teammatching.demo.domain.AuditingField;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(indexes = {
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
}
)
@Entity
public class Admission extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 1000)
    private String application;

    @Setter
    @Column(nullable = false)
    private Boolean approval;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false)
    private Team team;

    @Builder
    private Admission(String application, Boolean approval, UserAccount userAccount, Team team) {
        this.application = application;
        this.approval = approval;
        this.userAccount = userAccount;
        this.team = team;
    }
}

