package com.tomaszcymerys.springblog.repository;

import com.tomaszcymerys.springblog.model.Post;
import com.tomaszcymerys.springblog.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRespository extends JpaRepository<Post, Long> {
    Collection<Post> findByAuthorId(Long id);
}
