package com.skypro.java.course2.examinerservice.service;

import com.skypro.java.course2.examinerservice.domain.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    Set<Question> questions = new HashSet<>();


    @Override
    public Question add(String question, String answer) {
        Question question1 = new Question(question, answer);
        if (questions.contains(question1)) {
            return null;
        }
        questions.add(question1);
        return question1;
    }

    @Override
    public Question add(Question question) {
        if (questions.contains(question)) {
            return null;
        }
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (questions.contains(question)) {
            questions.remove(question);
            return question;
        }
        return null;
    }

    @Override
    public Collection<Question> getAll() {
        Set<Question> getQuestions = new HashSet<>(questions);
        return getQuestions;
    }

    @Override
    public Question getRandomQuestion() {
        if (questions == null || questions.isEmpty()) {
            throw new NoSuchElementException("Вопроса нет в списке");
        }
        List<Question> questionList = new ArrayList<>(questions);
        Random random = new Random();
        int randomIndex = random.nextInt(questionList.size());
        return questionList.get(randomIndex);

    }
}
