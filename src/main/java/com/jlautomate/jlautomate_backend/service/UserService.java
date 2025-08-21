package com.jlautomate.jlautomate_backend.service;

import com.jlautomate.jlautomate_backend.model.User;
import com.jlautomate.jlautomate_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) user.setRoles("USER");
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ✅ Nouvelle méthode pour récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteById(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;}

    public User updateUser(Long id, User userData) {
        return userRepository.findById(id)
                .map(existing -> {
                    if (userData.getFirstName() != null && !userData.getFirstName().isBlank()) {
                        existing.setFirstName(userData.getFirstName());
                    }
                    if (userData.getLastName() != null && !userData.getLastName().isBlank()) {
                        existing.setLastName(userData.getLastName());
                    }
                    if (userData.getEmail() != null && !userData.getEmail().isBlank()) {
                        existing.setEmail(userData.getEmail());
                    }
                    if (userData.getRoles() != null && !userData.getRoles().isBlank()) {
                        existing.setRoles(userData.getRoles());
                    }

                    // ⚡ Mise à jour du mot de passe seulement si renseigné
                    if (userData.getPassword() != null && !userData.getPassword().isBlank()) {
                        existing.setPassword(passwordEncoder.encode(userData.getPassword()));
                    }

                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec id: " + id));
    }



}
