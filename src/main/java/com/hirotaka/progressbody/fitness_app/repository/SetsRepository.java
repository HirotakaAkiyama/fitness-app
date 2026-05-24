package com.hirotaka.progressbody.fitness_app.repository;

import com.hirotaka.progressbody.fitness_app.entity.Sets;
import com.hirotaka.progressbody.fitness_app.entity.TrainingLogs;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SetsRepository extends JpaRepository<Sets, Long> {
    void deleteByTrainingLogs(TrainingLogs trainingLogs);
}
