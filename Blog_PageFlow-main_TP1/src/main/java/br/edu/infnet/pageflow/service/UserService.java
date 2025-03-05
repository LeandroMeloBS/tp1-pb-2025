package br.edu.infnet.pageflow.service;

import br.edu.infnet.pageflow.model.Author;
import br.edu.infnet.pageflow.model.BlogAdministrator;
import br.edu.infnet.pageflow.model.User;
import br.edu.infnet.pageflow.model.Visitor;
import br.edu.infnet.pageflow.model.Post;
import br.edu.infnet.pageflow.model.Comment;
import br.edu.infnet.pageflow.repository.UserRepository;
import br.edu.infnet.pageflow.repository.PostRepository;
import br.edu.infnet.pageflow.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Collection<User> getUsers() {
        return userRepository.getAllUsers();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Método para buscar usuário pelo e-mail
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para atualizar informações do usuário
    public User updateUser(String email, Map<String, String> updates) {
        User user = getUserByEmail(email);
        if (updates.containsKey("name")) user.setFirstName(updates.get("name"));
        if (updates.containsKey("email")) user.setEmail(updates.get("email"));
        if (updates.containsKey("password")) user.setPassword(updates.get("password")); // Lembre-se de criptografar a senha antes de salvar
        return userRepository.save(user);
    }

    // Método para listar postagens e comentários do usuário
    public Map<String, Object> getUserHistory(String email) {
        User user = getUserByEmail(email);
        List<Post> posts = postRepository.findByUser(user);
        List<Comment> comments = commentRepository.findByUser(user);

        Map<String, Object> history = new HashMap<>();
        history.put("posts", posts);
        history.put("comments", comments);
        return history;
    }

    // TODO fix code smell - repeated code - start//
    public User createBlogAdministrator(BlogAdministrator admin) {
        return userRepository.save(admin);
    }

    public User createAuthor(Author author) {
        return userRepository.save(author);
    }

    public User createVisitor(Visitor visitor) {
        return userRepository.save(visitor);
    }
    // TODO fix code smell - repeated code - end//

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        userRepository.deleteById(id);
    }
}
