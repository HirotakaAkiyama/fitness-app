package com.hirotaka.progressbody.fitness_app.service;

import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

/**
 * ユーザー情報の検索処理クラス.
 *
 * @author h_akiyama
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    /**
     * ユーザーのリポジトリのDI.
     */
    private final UsersRepository usersRepository;

    /**
     * ユーザー情報の検索処理.
     *
     * @param username ユーザー名.
     * @return ユーザー情報
     * @throws UsernameNotFoundException ユーザーが見つからなかった場合にスローする例外
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "は見つかりませんでした。"));

        return User.withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .roles("USER")
                .build();
    }
}
