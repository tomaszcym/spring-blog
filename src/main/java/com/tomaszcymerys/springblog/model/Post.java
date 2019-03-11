package com.tomaszcymerys.springblog.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Profile author;

    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateCreated = LocalDateTime.now();

    private LocalDateTime dateUpdated;

    @OneToOne
    private Profile updatedBy;

    @NotBlank
    @Size(min = 4, max = 150)
    private String title;

    @NotBlank
    @Size(min = 20)
    private String content;

    public Post(Profile author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
