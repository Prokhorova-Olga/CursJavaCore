package com.skypro.java.course2.examinerservice.service;

import com.skypro.java.course2.examinerservice.domain.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        int totalQuestions = questionService.getAll().size();
        if (amount <= 0 || amount > totalQuestions) {
            throw new IllegalArgumentException("BAD_REQUEST");
        }
        Set<Question> result = new HashSet<>();
        while (result.size() < amount) {
            Question randomQuestion = questionService.getRandomQuestion();
            result.add(randomQuestion);
        }
        return result;
    }
}


