package com.hirotaka.progressbody.fitness_app.repository;

import com.hirotaka.progressbody.fitness_app.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    // ユーザー名でユーザー情報を取得
    Optional<Users> findByUsername(String username);
}
