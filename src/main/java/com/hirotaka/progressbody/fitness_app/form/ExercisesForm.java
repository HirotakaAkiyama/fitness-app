package com.hirotaka.progressbody.fitness_app.form;

import lombok.Data;

@Data
public class ExercisesForm {
    /**
     * 種目ID.
     */
    private Long id;
    /**
     * ユーザーID.
     */
    private Long userId;
    /**
     * 種目名.
     */
    private String name;
    /**
     * 部位.
     */
    private String bodyPart;

}
