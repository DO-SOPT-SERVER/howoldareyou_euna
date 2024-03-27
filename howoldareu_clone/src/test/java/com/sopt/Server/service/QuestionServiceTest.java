package com.sopt.Server.service;

import com.sopt.Server.controller.response.GetQuestionResponse;
import com.sopt.Server.domain.Question;
import com.sopt.Server.repository.MemberJpaRepository;
import com.sopt.Server.repository.QuestionJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {
    @Mock
    QuestionJpaRepository questionJpaRepository;

    @InjectMocks
    QuestionService questionService;

    @Test
    @DisplayName("질문 리스트 조회 성공")
    void getQuestionResponseListTest() {
        // given
        List<Question> questionList = List.of(
                Question.builder().questionId(1L).questionContent("텐텐 먹어봤어?").build(),
                Question.builder().questionId(2L).questionContent("첫차타고 집에 가봤어?").build());
        BDDMockito.given(questionJpaRepository.findAll()).willReturn(questionList);
        List<GetQuestionResponse> MockResponse = List.of(
                GetQuestionResponse.of(1L, "텐텐 먹어봤어?"),
                GetQuestionResponse.of(2L, "첫차타고 집에 가봤어?"));

        // when
        List<GetQuestionResponse> response = questionService.getQuestionResponseList();

        //then
        assertEquals(response, MockResponse);
    }
}