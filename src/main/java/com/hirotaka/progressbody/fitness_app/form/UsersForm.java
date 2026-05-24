package com.hirotaka.progressbody.fitness_app.form;

import lombok.Data;

@Data
public class UsersForm {

    /**
     * ユーザー名.
     */
    private String username;

    /**
     * メールアドレス.
     */
    private String email;

    /**
     * パスワード（ハッシュ化）.
     */
    private String passwordHash;
}
