package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.entity.User;
import ru.vabrosimov.moneymanager.entity.UserRole;
import ru.vabrosimov.moneymanager.repository.NoteCategoryRepository;
import ru.vabrosimov.moneymanager.repository.UserRepository;
import ru.vabrosimov.moneymanager.repository.UserRoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean saveUser(User user) {
        User userFromDB = findByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            UserRole userRole = userRoleRepository.findByName("ROLE_USER");
            user.setUserRole(userRole);

            user.setNoteCategories(new ArrayList<>());

            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(Long id) {
        if (this.findById(id) != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addNoteCategory(Authentication authentication, NoteCategory noteCategory) {
        String username = authentication.getName();
        User user = findByUsername(username);
        if (user != null) {
            user.getNoteCategories().add(noteCategory);
            userRepository.flush();
            return true;
        }
        return false;
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUsernameUsed(String username) {
        return findByUsername(username) != null;
    }


    public User findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
