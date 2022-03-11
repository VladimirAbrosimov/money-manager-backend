package ru.vabrosimov.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vabrosimov.moneymanager.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String name);
}
