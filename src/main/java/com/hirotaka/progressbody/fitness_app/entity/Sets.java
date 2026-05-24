package com.hirotaka.progressbody.fitness_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sets")
@Data
public class Sets {
    /**
     * セットID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 記録ID.
     */
    @ManyToOne
    @JoinColumn(name = "training_log_id", nullable = false)
    private TrainingLogs trainingLogs;

    /**
     * セット番号.
     */
    @Column(nullable = false)
    private int setNumber;

    /**
     * 重量（kg）.
     */
    @Column(nullable = false)
    private double weightKg;

    /**
     * 回数.
     */
    @Column(nullable = false)
    private int reps;
}
