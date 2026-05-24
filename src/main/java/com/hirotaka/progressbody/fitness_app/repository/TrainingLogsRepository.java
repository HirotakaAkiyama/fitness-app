package com.hirotaka.progressbody.fitness_app.repository;

import com.hirotaka.progressbody.fitness_app.entity.Exercises;
import com.hirotaka.progressbody.fitness_app.entity.TrainingLogs;
import com.hirotaka.progressbody.fitness_app.entity.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingLogsRepository extends JpaRepository<TrainingLogs, Long> {
    // ユーザー情報と種目での情報取得
    List<TrainingLogs> findByUsersAndExercises(Users users, Exercises exercises);

    // 種目に紐づく種目の存在チェック
    boolean existsByExercises(Exercises exercises);

    // ユーザー情報からトレーニング記録を取得する
    List<TrainingLogs> findByUsers(Users users, Sort sort);

}
