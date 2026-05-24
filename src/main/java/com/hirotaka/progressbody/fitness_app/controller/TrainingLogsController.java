package com.hirotaka.progressbody.fitness_app.controller;

import com.hirotaka.progressbody.fitness_app.dto.VolumeResultDTO;
import com.hirotaka.progressbody.fitness_app.entity.Exercises;
import com.hirotaka.progressbody.fitness_app.entity.Sets;
import com.hirotaka.progressbody.fitness_app.entity.TrainingLogs;
import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.form.SetsForm;
import com.hirotaka.progressbody.fitness_app.form.TrainingLogsForm;
import com.hirotaka.progressbody.fitness_app.repository.UsersRepository;
import com.hirotaka.progressbody.fitness_app.service.ExercisesService;
import com.hirotaka.progressbody.fitness_app.service.SetsService;
import com.hirotaka.progressbody.fitness_app.service.TrainingLogsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TrainingLogsController {

    /**
     * トレーニングログのサービスフィールド.
     */
    private final TrainingLogsService trainingLogsService;

    /**
     * ユーザーリポジトリのDI.
     */
    private final UsersRepository usersRepository;

    /**
     * 種目サービスのDI.
     */
    private final ExercisesService exercisesService;

    /**
     * セットサービスのDI.
     */
    private final SetsService setsService;

    /**
     * トレーニングログのビューへ値を渡す.
     *
     * @param model 値
     * @return トレーニングログの画面
     */
    @GetMapping("training/logs")
    public String showTrainingView(Model model) {

        // セッション情報からユーザー名を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // ユーザー名をもとにユーザー情報を取得する
        Users users = usersRepository.findByUsername(username).orElseThrow();

        model.addAttribute("trainingLogs", trainingLogsService.findAllLogs(users));
        return "/training/trainingLogs";
    }

    /**
     * トレーニング登録用フォーム
     *
     * @return トレーニング登録フォームページ
     */
    @GetMapping("training/register")
    public String showRegisterForm(Model model) {

        // セッション情報からユーザー名を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // ユーザー名をもとにユーザー情報を取得する
        Users users = usersRepository.findByUsername(username).orElseThrow();

        // 種目の全件取得
        List<Exercises> list = exercisesService.findAllExercises(users);

        // 種目の登録プルダウン用に値を渡す
        model.addAttribute("training", list);

        // 画面の返却
        return "/training/trainingRegistrationForm";
    }

    /**
     * トレーニング登録処理.
     *
     * @param trainingForm トレーニング登録フォーム
     * @return 一覧画面
     */
    @PostMapping("training/save")
    public String saveLogs(@Valid @ModelAttribute TrainingLogsForm trainingForm, BindingResult result, Model model) {

        // バリデーションチェックでエラーがある場合
        if (result.hasErrors()) {
            // フォームに種目リストを再セットして戻す
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users users = usersRepository.findByUsername(auth.getName()).orElseThrow();
            model.addAttribute("training", exercisesService.findAllExercises(users));

            // 前回の入力内容を再セット
            model.addAttribute("trainingLogsForm", trainingForm);

            return "/training/trainingRegistrationForm";
        }

        // エンティティの初期化
        TrainingLogs trainingLogs = new TrainingLogs();

        // セッション情報からユーザー情報を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String username = auth.getName();

         // 取得した情報から検索処理を行う
        Users users = usersRepository.findByUsername(username).orElseThrow();

        // 種目の検索
        Exercises exercises = exercisesService.findById(trainingForm.getExerciseId());

         // トレーニングログエンティティへ情報の詰め替え
        trainingLogs.setUsers(users);
        trainingLogs.setExercises(exercises);
        trainingLogs.setLogDate(trainingForm.getLogDate());
        trainingLogs.setMemo(trainingForm.getMemo());

        // トレーニングログの保存処理
        trainingLogsService.saveLog(trainingLogs);

        // セット情報リストをフォームから取得
        List<SetsForm> setsList = trainingForm.getSets();

        // セットごとに情報を保存する
        for (int i = 0; i < setsList.size(); i++) {
            // １セットごとにエンティティを初期化する
            Sets sets = new Sets();

            // 対象のセット情報を取得
            SetsForm set = setsList.get(i);

            // セットのエンティティへ情報の詰め替え
            sets.setTrainingLogs(trainingLogs);
            sets.setSetNumber(i + 1);
            sets.setWeightKg(set.getWeightKg());
            sets.setReps(set.getReps());

            // セット情報の保存処理
            setsService.saveSets(sets);
        }

        return "redirect:/training/logs";
    }

    /**
     * 記録編集画面呼び出し.
     *
     * @param id 記録ID.
     * @param model 値
     * @return 記録ID.
     */
    @GetMapping("training/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // 対象記録の取得
        TrainingLogs target = trainingLogsService.findById(id);

        // 値の受け渡し
        model.addAttribute("target", target);

        return "training/edit";
    }

    /**
     * 記録編集処理.
     *
     * @param id 記録ID.
     * @param trainingLogsForm 記録フォーム
     * @return 記録一覧画面
     */
    @PostMapping("training/edit/{id}")
    public String editLog(@PathVariable Long id, @ModelAttribute TrainingLogsForm trainingLogsForm) {

        // エンティティの初期化
        TrainingLogs trainingLogs = new TrainingLogs();

        // セッション情報からユーザー名を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // ユーザー名をもとにユーザー情報を取得する
        Users users = usersRepository.findByUsername(username).orElseThrow();

        // 種目情報を取得する
        Exercises exercises = exercisesService.findById(trainingLogsForm.getExerciseId());

        // エンティティへの値の受け渡し
        trainingLogs.setId(id);
        trainingLogs.setUsers(users);
        trainingLogs.setExercises(exercises);
        trainingLogs.setLogDate(trainingLogsForm.getLogDate());
        trainingLogs.setMemo(trainingLogsForm.getMemo());

        // トレーニング記録の編集処理呼び出し
        trainingLogsService.saveLog(trainingLogs);

        // セット情報を取得する
        List<SetsForm> setList = trainingLogsForm.getSets();

        // 取得したセット情報から編集処理を行う
        for (SetsForm set : setList) {
            Sets sets = setsService.findById(set.getId());

            sets.setTrainingLogs(trainingLogs);
            sets.setWeightKg(set.getWeightKg());
            sets.setReps(set.getReps());

            setsService.saveSets(sets);
        }

        // トレーニング記録一覧画面へリダイレクト
        return "redirect:/training/logs";
    }

    /**
     * 記録削除処理.
     *
     * @param id 記録ID.
     * @return 記録一覧へリダイレクト
     */
    @PostMapping("training/delete/{id}")
    public String deleteLog(@PathVariable Long id) {
        // 記録削除処理の呼び出し
        trainingLogsService.deleteLog(id);
        return "redirect:/training/logs";
    }

    /**
     * トレーニング強度遷移画面表示処理.
     *
     * @param model model
     * @return トレーニング強度遷移画面
     */
    @GetMapping("training/showGraph")
    public String showGraph(@RequestParam(required = false) Long exerciseId, Model model) {

        // セッション情報からユーザー名を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // ユーザー名をもとにユーザー情報を取得する
        Users users = usersRepository.findByUsername(username).orElseThrow();

        // 種目リストの取得
        List<Exercises> exercisesList = exercisesService.findAllExercises(users);

        // デフォルト種目の取得
        Optional<Exercises> exercises =  exercisesService.findDefaultExercise(users);

        // リストの初期化
        List<VolumeResultDTO> volumeResultDTOList;

        // 種目の存在有無による処理分岐
        // 種目が存在する場合、種目の計算を行いリストを用意する
        if (exercises.isPresent()) {
            // ボリューム計算処理呼び出し
            if (Objects.isNull(exerciseId)) {
                // デフォルト指定した種目のボリュームを取得
                volumeResultDTOList = trainingLogsService.calcVolume(exercises.get().getId(), users);
            } else {
                // プルダウンから取得した種目のボリュームを取得
                volumeResultDTOList = trainingLogsService.calcVolume(exerciseId, users);
            }
        } else {
            // 種目が存在しない場合空のリストを用意する
            volumeResultDTOList = new ArrayList<>();
        }

        // 取得した値を画面へ渡す
        model.addAttribute("exercises",exercisesList);
        model.addAttribute("showDefault", volumeResultDTOList);
        model.addAttribute("selectExercise", exerciseId);

        // デフォルト取得有無による分岐
        model.addAttribute("defaultExercise", exercises.orElse(null));

        // グラフ表示画面へ遷移
        return "training/trainingGraph";
    }
}
