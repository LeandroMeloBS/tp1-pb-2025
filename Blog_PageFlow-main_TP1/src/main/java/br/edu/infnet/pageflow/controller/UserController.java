package br.edu.infnet.pageflow.controller;

import br.edu.infnet.pageflow.model.Author;
import br.edu.infnet.pageflow.model.BlogAdministrator;
import br.edu.infnet.pageflow.model.User;
import br.edu.infnet.pageflow.model.Visitor;
import br.edu.infnet.pageflow.service.PostService;
import br.edu.infnet.pageflow.service.CommentService;
import br.edu.infnet.pageflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    // Endpoint para buscar detalhes do usuário logado
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    // Endpoint para atualizar informações do usuário
    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody Map<String, String> updates) {
        User updatedUser = userService.updateUser(userDetails.getUsername(), updates);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint para listar postagens e comentários do usuário
    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getUserHistory(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> history = userService.getUserHistory(userDetails.getUsername());
        return ResponseEntity.ok(history);
    }

    @GetMapping("/")
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TODO fix code smell - repeated code - start//
    @PostMapping("/administrator")
    public ResponseEntity<User> createAdministrator(@RequestBody @Valid BlogAdministrator admin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createBlogAdministrator(admin));
    }

    @PostMapping("/author")
    public ResponseEntity<User> createAuthor(@RequestBody @Valid Author author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createAuthor(author));
    }

    @PostMapping("/visitor")
    public ResponseEntity<User> createVisitor(@RequestBody @Valid Visitor visitor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createVisitor(visitor));
    }
    // TODO fix code smell - repeated code - end//

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
