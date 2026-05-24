package com.hirotaka.progressbody.fitness_app.repository;

import com.hirotaka.progressbody.fitness_app.entity.Exercises;
import com.hirotaka.progressbody.fitness_app.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExercisesRepository extends JpaRepository<Exercises, Long> {
    // ユーザー単位での種目取得
    List<Exercises> findByUsers(Users users);

    // デフォルト種目の取得
    Optional<Exercises> findByIsDefaultAndUsers(Boolean isDefault, Users users);
}
