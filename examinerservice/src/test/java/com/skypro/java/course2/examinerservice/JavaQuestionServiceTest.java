package com.skypro.java.course2.examinerservice;


import com.skypro.java.course2.examinerservice.domain.Question;
import com.skypro.java.course2.examinerservice.service.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class JavaQuestionServiceTest {

    private JavaQuestionService out;

    @BeforeEach
    void setUp() {
        out = new JavaQuestionService();
    }

    @Test
    void testAddQuestion_WhenQuestionNotExists_ThenReturnAddedQuestion() {

        String questionText = "Что такое ООП?";
        String answerText = "Объектно-ориентированное программирование";

        Question result = out.add(questionText, answerText);

        assertNotNull(result, "Метод add должен возвращать не-null объект");
        assertEquals(questionText, result.getQuestion(), "Текст вопроса должен совпадать");
        assertEquals(answerText, result.getAnswer(), "Текст ответа должен совпадать");
        Collection<Question> allQuestions = out.getAll();
        assertTrue(allQuestions.contains(result), "Вопрос должен быть в коллекции после добавления");
        assertEquals(1, allQuestions.size(), "В коллекции должен быть ровно один вопрос");
    }

    @Test
    void testAddQuestion_WhenQuestionExists_ThenReturnNull() {

        String questionText = "Что такое Java?";
        String answerText = "Язык программирования";
        Question firstAdd = out.add(questionText, answerText);
        assertNotNull(firstAdd, "Первый вызов add должен вернуть вопрос");
        int initialSize = out.getAll().size();

        Question secondAdd = out.add(questionText, answerText);

        assertNull(secondAdd, "При добавлении дубликата должен возвращаться null");
        assertEquals(initialSize, out.getAll().size(), "Размер коллекции не должен измениться при добавлении дубликата");
    }

    @Test
    void testRemoveQuestion_WhenQuestionExists_ThenReturnRemovedQuestion() {

        String questionText = "Что такое Spring?";
        String answerText = "Фреймворк для Java";
        Question addedQuestion = out.add(questionText, answerText);
        int initialSize = out.getAll().size();

        Question removedQuestion = out.remove(addedQuestion);

        assertNotNull(removedQuestion, "При удалении существующего вопроса должен возвращаться вопрос");
        assertEquals(addedQuestion, removedQuestion, "Удалённый вопрос должен совпадать с добавленным");
        assertEquals(initialSize - 1, out.getAll().size(), "Размер коллекции должен уменьшиться на 1");
        assertFalse(out.getAll().contains(addedQuestion), "Коллекция не должна содержать удалённый вопрос");
    }

    @Test
    void testRemoveQuestion_WhenQuestionNotExists_ThenReturnNull() {

        Question nonExistentQuestion = new Question("Несуществующий вопрос", "Ответ");
        int initialSize = out.getAll().size();

        Question result = out.remove(nonExistentQuestion);

        assertNull(result, "При удалении несуществующего вопроса должен возвращаться null");
        assertEquals(initialSize, out.getAll().size(), "Размер коллекции не должен измениться");
    }

    @Test
    void testGetRandomQuestion_WhenCollectionEmpty_ThenThrowException() {

        assertThrows(NoSuchElementException.class, () -> out.getRandomQuestion(), "Метод должен выбросить исключение NoSuchElementException, если коллекция пустая");

    }

    @Test
    void testGetRandomQuestion_WhenCollectionNotEmpty_ThenReturnQuestionFromCollection() {

        Question question1 = out.add("Вопрос 1", "Ответ 1");
        Question question2 = out.add("Вопрос 2", "Ответ 2");
        Question question3 = out.add("Вопрос 3", "Ответ 3");
        Collection<Question> allQuestions = out.getAll();

        boolean foundQuestion1 = false;
        boolean foundQuestion2 = false;
        boolean foundQuestion3 = false;
        for (int i = 0; i < 10; i++) {
            Question randomQuestion = out.getRandomQuestion();
            assertNotNull(randomQuestion, "Случайный вопрос не должен быть null");
            assertTrue(allQuestions.contains(randomQuestion), "Возвращенный вопрос должен быть из коллекции");
            if (randomQuestion.equals(question1)) foundQuestion1 = true;
            if (randomQuestion.equals(question2)) foundQuestion2 = true;
            if (randomQuestion.equals(question3)) foundQuestion3 = true;
        }

        assertTrue(foundQuestion1, "Должен возвращаться вопрос 1");
        assertTrue(foundQuestion2, "Должен возвращаться вопрос 2");
        assertTrue(foundQuestion3, "Должен возвращаться вопрос 3");
    }

    @Test
    void testGetAll_WhenQuestionsExist_ThenReturnAllQuestions() {

        Question question1 = out.add("Вопрос 1", "Ответ 1");
        Question question2 = out.add("Вопрос 2", "Ответ 2");

        Collection<Question> result = out.getAll();

        assertNotNull(result, "Метод getAll не должен возвращать null");
        assertEquals(2, result.size(), "Должно вернуться 2 вопроса");
        assertTrue(result.contains(question1), "Результат должен содержать вопрос 1");
        assertTrue(result.contains(question2), "Результат должен содержать вопрос 2");
    }

}






















