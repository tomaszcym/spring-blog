package com.tomaszcymerys.springblog.repository;

import com.tomaszcymerys.springblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Collection<Post> findByAuthorId(Long id);

    @Transactional
    void removeByAuthorId(Long id);
}
