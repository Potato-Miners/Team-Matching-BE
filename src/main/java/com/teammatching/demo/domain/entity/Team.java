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
        @Index(columnList = "name"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Team extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String adminId;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(length = 1000)
    private String description;

    @Setter
    @Column(nullable = false)
    private String hashtag;

    @ToString.Exclude
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private final Set<Admission> admissions = new LinkedHashSet<>();

    @Builder
    private Team(String name, String description, String hashtag, Long max) {
        this.name = name;
        this.description = description;
        this.hashtag = hashtag;
    }
}
