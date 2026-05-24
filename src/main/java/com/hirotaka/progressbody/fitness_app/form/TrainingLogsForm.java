package com.hirotaka.progressbody.fitness_app.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * トレーニングログ登録フォーム.
 *
 * @author h_akiyama
 */
@Data
public class TrainingLogsForm {

    /**
     * ユーザーID.
     */
    private Long userId;

    /**
     * 種目ID.
     */
    @NotNull
    private Long exerciseId;

    /**
     * 記録日.
     */
    @NotNull
    private LocalDate logDate;

    /**
     * メモ.
     */
    private String memo;

    /**
     * セット情報.
     */
    @Valid
    private List<SetsForm> sets;
}
