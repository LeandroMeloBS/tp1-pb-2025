package br.edu.infnet.pageflow.repository;

import br.edu.infnet.pageflow.model.Comment;
import br.edu.infnet.pageflow.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    // Consulta para buscar todos os comentários com ordenação
    @Query("from Comment c order by c.id ASC") // Ordenando pelo campo 'id' em ordem crescente
    Collection<Comment> getAllComments(Sort sort);

    // Buscar comentários de um usuário específico
    List<Comment> findByUser(User user);
}


