package com.hirotaka.progressbody.fitness_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ユーザー情報.
 *
 * @author h_akiyama
 */
@Entity
@Table(name = "users")
@Data
public class Users {
    /**
     * ユーザーID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ユーザー名.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * メールアドレス.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * パスワード（ハッシュ）.
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * 作成日時.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * 作成日時の登録.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
