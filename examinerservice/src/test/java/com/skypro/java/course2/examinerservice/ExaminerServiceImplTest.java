package com.skypro.java.course2.examinerservice;

import com.skypro.java.course2.examinerservice.domain.Question;
import com.skypro.java.course2.examinerservice.service.ExaminerServiceImpl;
import com.skypro.java.course2.examinerservice.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;
    @InjectMocks
    private ExaminerServiceImpl out;

    @Test
    void testGetQuestions_WhenAmountIsValid_ThenReturnUniqueQuestions() {

        int amount = 3;
        Question question1 = new Question("Вопрос 1", "Ответ 1");
        Question question2 = new Question("Вопрос 2", "Ответ 2");
        Question question3 = new Question("Вопрос 3", "Ответ 3");
        when(questionService.getAll()).thenReturn(Set.of(question1, question2, question3));
        when(questionService.getRandomQuestion())
                .thenReturn(question1)
                .thenReturn(question2)
                .thenReturn(question3);

        Collection<Question> result = out.getQuestions(amount);

        assertNotNull(result, "Результат не должен быть null");
        assertEquals(amount, result.size(), "Должно вернуться ровно 3 вопроса");
        Set<Question> resultSet = new HashSet<>(result);
        assertEquals(amount, result.size(), "Все вопросы должны быть уникальными");
        verify(questionService, times(1)).getAll();
        verify(questionService, times(amount)).getRandomQuestion();

    }

    @Test
    void testGetQuestions_WhenAmountIsZero_ThenThrowException() {

        int amount = 0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> out.getQuestions(amount),
                "Метод должен выбрасывать исключение при amount = 0");

        assertTrue(exception.getMessage().contains("BAD_REQUEST"));

    }

    @Test
    void testGetQuestions_WhenAmountExceedsTotalQuestions_ThenThrowException() {

        int amount = 5;
        Question question1 = new Question("Вопрос 1", "Ответ 1");
        Question question2 = new Question("Вопрос 2", "Ответ 2");
        Question question3 = new Question("Вопрос 3", "Ответ 3");
        when(questionService.getAll()).thenReturn(Set.of(question1, question2, question3));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> out.getQuestions(amount),
                "Метод должен выбрасывать исключение, если amount > размера коллекции");
        assertTrue(exception.getMessage().contains("BAD_REQUEST"));
        verify(questionService, never()).getRandomQuestion();
    }

    @Test
    void testGetQuestions_WhenRandomServiceReturnsDuplicates_ThenReturnUniqueQuestions() {

        int amount = 2;
        Question question1 = new Question("Вопрос 1", "Ответ 1");
        Question question2 = new Question("Вопрос 2", "Ответ 2");
        when(questionService.getAll()).thenReturn(Set.of(question1, question2));
        when(questionService.getRandomQuestion())
                .thenReturn(question1)
                .thenReturn(question1)
                .thenReturn(question2);
        Collection<Question> result = out.getQuestions(amount);

        assertEquals(amount, result.size(), "Должно вернуться 2 вопроса");
        Set<Question> resultSet = new HashSet<>(result);
        assertEquals(amount, resultSet.size(), "Результат должен содержать только уникальные вопросы");
        verify(questionService, times(3)).getRandomQuestion();
    }

}
