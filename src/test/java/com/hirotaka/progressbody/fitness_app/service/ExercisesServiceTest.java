package com.hirotaka.progressbody.fitness_app.service;

import com.hirotaka.progressbody.fitness_app.entity.Exercises;
import com.hirotaka.progressbody.fitness_app.entity.Users;
import com.hirotaka.progressbody.fitness_app.repository.ExercisesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExercisesServiceTest {

    @Mock
    private ExercisesRepository exercisesRepository;

    @InjectMocks
    private ExercisesService exercisesService;

    @Test
    void findByIdTest() {

        // Arrange
        // モックユーザー情報
        Long userId = 1L;

        // モックユーザーの準備
        Users mockUser = new Users();
        mockUser.setId(userId);

        // モック種目情報
        Long exercisesId = 1L;
        String exerciseName = "ベンチプレス";
        String bodyPart = "大胸筋";
        Boolean isDefault = false;

        // モック種目の準備
        Exercises mockExercise = new Exercises();
        mockExercise.setId(exercisesId);
        mockExercise.setUsers(mockUser);
        mockExercise.setName(exerciseName);
        mockExercise.setBodyPart(bodyPart);
        mockExercise.setIsDefault(isDefault);

        when(exercisesRepository.findById(exercisesId)).thenReturn(Optional.of(mockExercise));

        // Act
        Exercises result =  exercisesService.findById(exercisesId);

        // Assert
        assertEquals(exerciseName, result.getName());
    }

    @Test
    void findByIdErr() {

        // Arrange
        // モック種目情報
        Long exercisesId = 99L;
        when(exercisesRepository.findById(exercisesId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> exercisesService.findById(exercisesId));

    }
}