package com.hirotaka.progressbody.fitness_app.service;

import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    /**
     * ユーザー情報フィールド.
     */
    private final UsersRepository usersRepository;

    /**
     * パスワードエンコーダーフィールド.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * ユーザー情報登録.
     *
     * @param users ユーザー情報
     */
    public void saveUsers(Users users) {
        // パスワードのハッシュ化
        String encoded = passwordEncoder.encode(users.getPasswordHash());
        users.setPasswordHash(encoded);
        usersRepository.save(users);
    }
}
