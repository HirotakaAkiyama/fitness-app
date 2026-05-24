package com.hirotaka.progressbody.fitness_app.controller;

import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.form.UsersForm;
import com.hirotaka.progressbody.fitness_app.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ユーザー情報コントローラー.
 *
 * @author h_akiyama
 */
@Controller
@RequiredArgsConstructor
public class UsersController {

    /**
     * ユーザーサービスのDI.
     */
    private final UsersService usersService;

    /**
     * ログインページの表示処理.
     *
     * @return ログインページ
     */
    @GetMapping("/login")
    public String showLogin() {
        return "/users/login";
    }

    /**
     * ユーザー登録画面表示処理.
     *
     * @return ユーザー登録画面
     */
    @GetMapping("/register")
    public String showRegister() {
        return "/users/register";
    }

    /**
     * ユーザー登録処理.
     *
     * @param form ユーザー情報フォーム
     * @return ログイン画面
     */
    @PostMapping("/save")
    public String saveUsers(@ModelAttribute UsersForm form) {
        Users users = new Users();
        users.setUsername(form.getUsername());
        users.setEmail(form.getEmail());
        users.setPasswordHash(form.getPasswordHash());
        usersService.saveUsers(users);
        return "redirect:/login";
    }
}
