package com.skypro.java.course2.examinerservice.controller;

import com.skypro.java.course2.examinerservice.domain.Question;
import com.skypro.java.course2.examinerservice.service.ExaminerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ExamController {
    private final ExaminerService examinerService;

    public ExamController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping("/exam/get/{amount}")
    public Collection<Question> getQuestions(@PathVariable int amount) {
        // ExamController ИСПОЛЬЗУЕТ examinerService для получения вопросов
        return examinerService.getQuestions(amount);
    }
}
