package com.hirotaka.progressbody.fitness_app.service;

import com.hirotaka.progressbody.fitness_app.entity.Sets;
import com.hirotaka.progressbody.fitness_app.entity.TrainingLogs;
import com.hirotaka.progressbody.fitness_app.repository.SetsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetsService {

    /**
     * セットのフィールド.
     */
    private final SetsRepository setsRepository;

    /**
     * セットの全件取得.
     *
     * @return セット一覧
     */
    public List<Sets> findAllSets() {
        // セットの全件取得
        return setsRepository.findAll();
    }

    /**
     * セット情報の1件取得処理.
     *
     * @param id セットID.
     * @return セット情報
     */
    public Sets findById(Long id) {
        return setsRepository.findById(id).orElseThrow();
    }

    /**
     * セットの保存.
     *
     * @param sets セット内容
     */
    public void saveSets(Sets sets) {
        // セットの保存処理
        setsRepository.save(sets);
    }

    /**
     * セットの削除処理.
     *
     * @param trainingLogs トレーニング記録
     */
    @Transactional
    public void deleteByTrainingLogs(TrainingLogs trainingLogs) {
        setsRepository.deleteByTrainingLogs(trainingLogs);
    }
}
