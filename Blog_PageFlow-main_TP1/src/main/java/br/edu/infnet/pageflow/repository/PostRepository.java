package br.edu.infnet.pageflow.repository;

import br.edu.infnet.pageflow.model.Post;
import br.edu.infnet.pageflow.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query("from Post")
    Collection<Post> getAllPosts(Sort by);

    // Buscar postagens de um usuário específico
    List<Post> findByUser(User user);
}
