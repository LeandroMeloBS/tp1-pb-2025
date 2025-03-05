package br.edu.infnet.pageflow.repository;

import br.edu.infnet.pageflow.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    // exemplos de queries que podem ser criadas além das
    // queries default da classe CrudRepository
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u")
    Collection<User> getAllUsers();

}
