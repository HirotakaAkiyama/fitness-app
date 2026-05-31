package com.hirotaka.progressbody.fitness_app.controller;

import com.hirotaka.progressbody.fitness_app.common.SecurityUtils;
import com.hirotaka.progressbody.fitness_app.entity.Exercises;
import com.hirotaka.progressbody.fitness_app.form.ExercisesForm;
import com.hirotaka.progressbody.fitness_app.repository.UsersRepository;
import com.hirotaka.progressbody.fitness_app.service.ExercisesService;
import com.hirotaka.progressbody.fitness_app.service.TrainingLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 種目コントローラー.
 *
 * @author h_akiyama
 */
@Controller
@RequiredArgsConstructor
public class ExercisesController {
    /**
     * 種目サービスのDI.
     */
    private final ExercisesService exercisesService;

    /**
     * トレーニングログサービスのDI.
     */
    private final TrainingLogsService trainingLogsService;

    /**
     * 認証ユーティリティクラスのDI.
     */
    private final SecurityUtils securityUtils;

    /**
     * 種目一覧表示処理.
     *
     * @return 種目表示一覧
     */
    @GetMapping("/exercises/list")
    public String showExercises(Model model) {

        model.addAttribute("exercises", exercisesService.findAllExercises(securityUtils.getLoginUser()));
        // 種目一覧画面を表示
        return "exercises/exercisesList";
    }

    /**
     * 種目登録ページの表示処理.
     *
     * @return 種目登録ページ
     */
    @GetMapping("/exercises/form")
    public String showRegisterForm() {
        // 画面の返却
        return "exercises/exercisesForm";
    }

    /**
     * 種目登録処理.
     *
     * @param form 種目登録フォーム
     * @return 一覧画面
     */
    @PostMapping("/exercises/save")
    public String saveExercises(ExercisesForm form) {
        // エンティティの初期化
        Exercises exercises = new Exercises();

        // 取得したフォームの値をエンティティへ渡す
        exercises.setUsers(securityUtils.getLoginUser());
        exercises.setName(form.getName());
        exercises.setBodyPart(form.getBodyPart());
        exercises.setIsDefault(false);

        // サービスへ詰め替えた値を渡す
        exercisesService.saveExercises(exercises);

        // 一覧画面を表示する
        return "redirect:/exercises/list";
    }

    /**
     * 種目の削除処理.
     *
     * @param id 種目のプライマリーキー
     * @return 種目一覧ページを再表示
     */
    @PostMapping("/exercises/delete/{id}")
    public String deleteExercises(@PathVariable Long id, RedirectAttributes redirectAttributes){
        // 記録との紐付けを確認し、削除処理を行う
        if (!trainingLogsService.existsByExercises(id)) {
            exercisesService.deleteExercises(id);
        } else {
            // 紐づく記録が存在する場合エラーを返す
            redirectAttributes.addFlashAttribute("errorMessage", "種目に紐づく記録があるため削除できません。");
        }
        return "redirect:/exercises/list";
    }

    /**
     * 種目編集画面表示.
     *
     * @param id 種目ID.
     * @return 種目編集画面
     */
    @GetMapping("/exercises/edit/{id}")
    public String showEditExercises(@PathVariable Long id, Model model) {
        // 初期表示情報の受け渡し
        model.addAttribute("targetExercises", exercisesService.findById(id));

        // 種目編集画面へ遷移
        return "exercises/exercisesEdit";
    }

    /**
     * 種目の編集処理.
     *
     * @param id 種目ID.
     * @param form 種目フォーム
     * @return 種目一覧画面
     */
    @PostMapping("/exercises/edit/{id}")
    public String editExercises(@PathVariable Long id, @ModelAttribute ExercisesForm form) {

        // 既存の種目情報をDBから取得（isDefaultなど既存の値を保持するため）
        Exercises exercises = exercisesService.findById(id);

        // 更新したい値だけ上書き
        exercises.setName(form.getName());
        exercises.setBodyPart(form.getBodyPart());

        // 種目の更新処理
        exercisesService.saveExercises(exercises);

        // 種目一覧画面へ遷移
        return "redirect:/exercises/list";
    }

    /**
     * デフォルト種目更新処理
     *
     * @param id 種目id.
     * @return 種目一覧画面
     */
    @PostMapping("/exercises/default")
    public String updateDefaultExercise(@RequestParam Long id) {
        // デフォルト種目の更新処理呼び出し
        exercisesService.updateDefaultExercise(securityUtils.getLoginUser(), id);

        return "redirect:/exercises/list";
    }
}
