package com.hirotaka.progressbody.fitness_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 種目情報.
 *
 * @author h_akiyama
 */
@Entity
@Table(name = "exercises")
@Data
public class Exercises {
    /**
     * 種目ID.
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
     * 種目名.
     */
    @Column(nullable = false)
    private String name;

    /**
     * 部位.
     */
    private String bodyPart;

    /**
     * デフォルト種目フラグ.
     */
    private Boolean isDefault;

    /**
     * 作成日時.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * 作成日時の登録.
     */
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
