package com.hirotaka.progressbody.fitness_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * メニュー画面コントローラー.
 *
 * @author h_akiyama
 */
@Controller
public class MenuController {
    /**
     * メニュー画面表示処理.
     *
     * @return メニュー画面
     */
    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }
}
