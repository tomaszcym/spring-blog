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
@AllArgsConstructor
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
    @Getter(AccessLevel.NONE)
    private LocalDateTime dateCreated = LocalDateTime.now();

    @NotNull
    private LocalDateTime dateUpdated = LocalDateTime.now();

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
