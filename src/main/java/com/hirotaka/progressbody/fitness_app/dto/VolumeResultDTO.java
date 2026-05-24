package com.hirotaka.progressbody.fitness_app.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * トレーニング強度結果格納DTO.
 *
 * @author h_akiyama
 */
@Data
public class VolumeResultDTO {
    /**
     * 記録日.
     */
    private LocalDate logDate;

    /**
     * トレーニング強度.
     */
    private double volume;
}
