package com.hirotaka.progressbody.fitness_app.service;

import com.hirotaka.progressbody.fitness_app.dto.VolumeResultDTO;
import com.hirotaka.progressbody.fitness_app.entity.Exercises;
import com.hirotaka.progressbody.fitness_app.entity.Sets;
import com.hirotaka.progressbody.fitness_app.entity.TrainingLogs;
import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.repository.ExercisesRepository;
import com.hirotaka.progressbody.fitness_app.repository.TrainingLogsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingLogsService {

    /**
     * トレーニングログのフィールド.
     */
    private final TrainingLogsRepository trainingLogsRepository;

    /**
     * 種目のフィールド.
     */
    private final ExercisesRepository exercisesRepository;

    /**
     * セットサービスのDI.
     */
    private final SetsService setsService;

    /**
     * トレーニング記録の全件取得.
     *
     * @return トレーニングログ
     */
    public List<TrainingLogs> findAllLogs(Users users) {
        Sort sort = Sort.by("logDate");
        // 取得した値を返却
        return trainingLogsRepository.findByUsers(users, sort);
    }

    /**
     * トレーニングログをIDで1件取得.
     *
     * @param id 記録ID.
     * @return トレーニングログ
     */
    public TrainingLogs findById(Long id) {
        // 取得した1件を返却
        return trainingLogsRepository.findById(id).orElseThrow();
    }

    /**
     * トレーニングログの記録.
     *
     * @param trainingLogs トレーニングログのエンテティ
     */
    public void saveLog(TrainingLogs trainingLogs) {
        // トレーニングログの登録処理
        trainingLogsRepository.save(trainingLogs);
    }

    /**
     * トレーニングログの削除.
     *
     * @param id 記録ID.
     */
    public void deleteLog(Long id) {
        // 記録情報を取得
        TrainingLogs trainingLogs = trainingLogsRepository.findById(id).orElseThrow();

        // 記録をもとにセットを削除
        setsService.deleteByTrainingLogs(trainingLogs);
        // 記録を削除.
        trainingLogsRepository.delete(trainingLogs);
    }

    /**
     * ボリューム計算処理.
     *
     * @param id 種目ID.
     * @return トレーニング強度計算結果.
     */
    public List<VolumeResultDTO> calcVolume(Long id, Users users) {
        // 種目情報を種目IDから取得.
        Exercises exercises = exercisesRepository.findById(id).orElseThrow();

        // ユーザーと種目が一致するトレーニング記録を取得する
        List<TrainingLogs> targetLogsList =  trainingLogsRepository.findByUsersAndExercises(users, exercises);

        // ボリューム計算結果リストの初期化
        List<VolumeResultDTO> volumeResult = new ArrayList<VolumeResultDTO>();

        // 該当の種目を含むトレーニング記録分計算処理を行う
        for (TrainingLogs trainingLog : targetLogsList) {
            // ボリューム格納先の初期化
            VolumeResultDTO dto = new VolumeResultDTO();

            // セット情報を取得
            List<Sets> setsList = trainingLog.getSets();

            // トレーニング強度の合計値初期化
            double sumVolume = 0;

            // 強度計算処理
            for (Sets set : setsList) {
                double volume = set.getWeightKg() * set.getReps();
                sumVolume += volume;
            }

            // 記録日の取得
            dto.setLogDate(trainingLog.getLogDate());
            // 強度の計算結果を格納
            dto.setVolume(sumVolume);
            volumeResult.add(dto);
        }

        // トレーニング強度計算結果を返却
        return volumeResult;
    }

    /**
     * 種目と記録の紐付け確認処理.
     *
     * @param id 種目id.
     * @return 記録存在有無
     */
    public boolean existsByExercises(Long id) {
        // 種目情報を取得
        Exercises exercises = exercisesRepository.findById(id).orElseThrow();

        // 種目情報に紐づく記録の存在有無を返却
        return trainingLogsRepository.existsByExercises(exercises);
    }
}
