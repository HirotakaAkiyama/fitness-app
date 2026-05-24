package com.hirotaka.progressbody.fitness_app.common;

import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 認証ユーティリティクラス.
 *
 * @author h_akiyama
 */
@Component
@RequiredArgsConstructor
public class SecurityUtils {

    /**
     * ユーザーリポジトリのDI.
     */
    private final UsersRepository usersRepository;

    public Users getLoginUser() {
        // セッション情報からユーザー名を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // ユーザー名をもとにユーザー情報を取得する
        return usersRepository.findByUsername(auth.getName()).orElseThrow();

    }

}
