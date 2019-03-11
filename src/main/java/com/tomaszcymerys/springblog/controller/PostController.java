package com.tomaszcymerys.springblog.controller;

import com.tomaszcymerys.springblog.model.Post;
import com.tomaszcymerys.springblog.model.Profile;
import com.tomaszcymerys.springblog.repository.PostRespository;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostRespository postRespository;

    public PostController(PostRespository postRespository) {
        this.postRespository = postRespository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        Collection<Post> col = this.postRespository.findAll();
        if(col == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Collection cannot be null!");
        if(col.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(col);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable Long id) {
        return this.postRespository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid Post post) {
        try {
            Post saved = this.postRespository.save(post);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity update(@RequestBody @Valid Post post, @PathVariable Long id) {
        return this.postRespository.findById(id)
                .map(p -> {
                    p.setDateUpdated(LocalDateTime.now());
                    p.setAuthor(post.getAuthor());
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());
                    Post saved = this.postRespository.save(p);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity remove(@PathVariable Long id) {
        try {
            this.postRespository.deleteById(id);
            return ResponseEntity.ok("Removed entity Post with ID [" + id + "]");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity getByProfile(@PathVariable Long id) {
        Collection<Post> col = this.postRespository.findByAuthorId(id);
        if(col.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(col);
    }
}
