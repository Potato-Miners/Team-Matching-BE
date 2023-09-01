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
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Post extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtag;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @OrderBy("createdAt DESC")
    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final Set<Comment> comments = new LinkedHashSet<>();

    @Builder
    private Post(String title, String content, String hashtag, UserAccount userAccount) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.userAccount = userAccount;
    }
}
