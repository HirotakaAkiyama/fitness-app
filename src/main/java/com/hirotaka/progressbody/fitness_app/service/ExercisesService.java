package com.hirotaka.progressbody.fitness_app.service;

import com.hirotaka.progressbody.fitness_app.entity.Exercises;
import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.repository.ExercisesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExercisesService {
    /**
     * 種目フィールド.
     */
    private final ExercisesRepository exercisesRepository;

    /**
     * 種目の全件取得.
     *
     * @return 種目全件
     */
    public List<Exercises> findAllExercises(Users users) {
        // ソート条件を指定
        Sort sort = Sort.by("id");
        // 種目を返す
        return exercisesRepository.findByUsers(users, sort);
    }

    /**
     * 種目の1件取得
     *
     * @param id 種目のプライマリーキー
     * @return 種目の検索結果
     */
    public Exercises findById(Long id) {
        // 種目を一件だけ返す
        return exercisesRepository.findById(id).orElseThrow();
    }

    /**
     * 種目の保存.
     *
     * @param exercises 種目
     */
    public void saveExercises(Exercises exercises) {
        exercisesRepository.save(exercises);
    }

    /**
     * 種目の削除.
     *
     * @param id 種目のプライマリーキー
     */
    public void deleteExercises(Long id) {
        exercisesRepository.deleteById(id);
    }

    /**
     * デフォルト種目取得処理.
     *
     * @return デフォルト種目情報
     */
    public Optional<Exercises> findDefaultExercise(Users users) {
        // デフォルト種目の取得
        return exercisesRepository.findByIsDefaultAndUsers(true, users);
    }

    /**
     * デフォルト種目更新処理.
     *
     * @param users ユーザー情報
     * @param id 種目id.
     */
    public void updateDefaultExercise(Users users, Long id) {
        // 現在のデフォルト種目を取得
        Optional<Exercises> oldDefault = exercisesRepository.findByIsDefaultAndUsers(true, users);
        // すでにデフォルト種目がある場合、フラグを外す
        if (oldDefault.isPresent()) {
            oldDefault.get().setIsDefault(false);
            saveExercises(oldDefault.get());
        }

        // 新デフォルト種目を取得
        Exercises newDefault = exercisesRepository.findById(id).orElseThrow();
        newDefault.setIsDefault(true);
        saveExercises(newDefault);
    }
}
