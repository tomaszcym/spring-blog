package com.tomaszcymerys.springblog.controller;

import com.tomaszcymerys.springblog.model.Post;
import com.tomaszcymerys.springblog.repository.PostRepository;
import com.tomaszcymerys.springblog.util.JsonMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        Collection<Post> col = this.postRepository.findAll();
        if(col == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Collection cannot be null!");
        if(col.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(col);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable Long id) {
        return this.postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid Post post) {
        try {
            Post saved = this.postRepository.save(post);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity update(@RequestBody Post post, @PathVariable Long id) {
        return this.postRepository.findById(id)
                .map(p -> {
                    p.setDateUpdated(LocalDateTime.now());
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());
                    Post saved = this.postRepository.save(p);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity remove(@PathVariable Long id) {
        try {
            this.postRepository.deleteById(id);
            return ResponseEntity.ok(JsonMsg.removed(Post.class, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity getByProfile(@PathVariable Long id) {
        Collection<Post> col = this.postRepository.findByAuthorId(id);
        if(col.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(col);
    }
}
