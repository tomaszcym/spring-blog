package com.tomaszcymerys.springblog.controller;

import com.tomaszcymerys.springblog.model.Profile;
import com.tomaszcymerys.springblog.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        Collection<Profile> col = this.profileRepository.findAll();
        if(col == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Collection cannot be null!");
        if(col.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(col);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> get(@PathVariable Long id) {
        return this.profileRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid Profile profile) {
        if(this.profileRepository.findByUsername(profile.getUsername()).isPresent())
            return ResponseEntity.badRequest()
                    .body("Profile with username=['" + profile.getUsername() + "'] already exist!");
        if(this.profileRepository.findByEmail(profile.getEmail()).isPresent())
            return ResponseEntity.badRequest()
                    .body("Profile with email=['" + profile.getEmail() + "'] already exist!");

        try {
            Profile saved = this.profileRepository.save(profile);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity update(@RequestBody @Valid Profile profile, @PathVariable Long id) {
        return this.profileRepository.findById(id)
                .map(p -> {
                    p.setUsername(profile.getUsername());
                    p.setEmail(profile.getEmail());
                    p.setFirstName(profile.getFirstName());
                    p.setLastName(profile.getLastName());
                    Profile saved = this.profileRepository.save(p);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity remove(@PathVariable Long id) {
        try {
            this.profileRepository.deleteById(id);
            return ResponseEntity.ok("Removed entity Profile with ID [" + id + "]");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
