package ru.wanderer.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wanderer.network.domain.User;

public interface UserDetailsRepository extends JpaRepository<User, String> {
}
