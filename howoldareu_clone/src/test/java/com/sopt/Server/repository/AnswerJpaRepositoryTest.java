package com.sopt.Server.repository;

import com.sopt.Server.domain.Answer;
import com.sopt.Server.domain.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AnswerJpaRepositoryTest {
    @Autowired
    AnswerJpaRepository answerJpaRepository;

    @Autowired
    QuestionJpaRepository questionJpaRepository;

    @AfterEach
    void tearDown() {
        answerJpaRepository.deleteAllInBatch();
        questionJpaRepository.deleteAllInBatch();
    }

    @Test
    void findByQuestionAndAnswerTypeTest() {
        // given
        Question question = Question.builder().questionContent("텐텐 먹어봤어?").build();
        questionJpaRepository.save(question);
        answerJpaRepository.save(Answer.builder().question(question).answerType(false).build());
        answerJpaRepository.save(Answer.builder().question(question).answerType(true).build());

        // when
        Answer answer = answerJpaRepository.findByQuestionAndAnswerType(question, true).orElseThrow();

        // then
        assertTrue(answer.isAnswerType());
        assertEquals(question.getQuestionContent(), answer.getQuestion().getQuestionContent());
    }
}