package com.hirotaka.progressbody.fitness_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "training_logs")
@Data
public class TrainingLogs {
    /**
     * 記録ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ユーザーID.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    /**
     * 種目ID.
     */
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercises exercises;

    /**
     * 記録日.
     */
    @Column(nullable = false)
    private LocalDate logDate;

    /**
     * メモ.
     */
    private String memo;

    /**
     * 作成日.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * 作成日の登録.
     */
    @PrePersist
    public void onCreate() {
        // 現在時刻の取得
        createdAt = LocalDateTime.now();
    }

    /**
     * セット情報.
     */
    @OneToMany(mappedBy = "trainingLogs")
    @OrderBy("setNumber")
    private List<Sets> sets;
}
