package com.skypro.java.course2.examinerservice.service;

import com.skypro.java.course2.examinerservice.domain.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}
