package ru.vabrosimov.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vabrosimov.moneymanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
