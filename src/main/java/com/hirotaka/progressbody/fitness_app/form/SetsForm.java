package com.hirotaka.progressbody.fitness_app.form;

import lombok.Data;
import jakarta.validation.constraints.NotNull;


/**
 * セット用フォーム.
 *
 * @author h_akiyama
 */
@Data
public class SetsForm {

    /**
     * セットID.
     */
    private Long id;

    /**
     * 重量（kg）.
     */
    @NotNull
    private Double weightKg;

    /**
     * 回数.
     */
    @NotNull
    private Integer reps;
}
