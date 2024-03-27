package com.sopt.Server.service;

import com.sopt.Server.controller.request.AnswerListRequest;
import com.sopt.Server.controller.request.AnswerRequest;
import com.sopt.Server.controller.response.AllResultsResponse;
import com.sopt.Server.controller.response.ResultResponse;
import com.sopt.Server.domain.Answer;
import com.sopt.Server.domain.Member;
import com.sopt.Server.domain.Question;
import com.sopt.Server.domain.Result;
import com.sopt.Server.exception.model.CustomException;
import com.sopt.Server.repository.AnswerJpaRepository;
import com.sopt.Server.repository.MemberJpaRepository;
import com.sopt.Server.repository.QuestionJpaRepository;
import com.sopt.Server.repository.ResultJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ResultServiceTest {
    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private QuestionJpaRepository questionJpaRepository;

    @Mock
    private AnswerJpaRepository answerJpaRepository;

    @Mock
    private ResultJpaRepository resultJpaRepository;

    @InjectMocks
    private ResultService resultService;

    @Test
    @DisplayName("결과 저장 성공")
    public void saveResultTest_Success() {
        // given
        AnswerRequest answerRequest1 = new AnswerRequest(1L, true);
        AnswerRequest answerRequest2 = new AnswerRequest(2L, false);
        AnswerListRequest request = new AnswerListRequest("euna", List.of(answerRequest1, answerRequest2));

        Member member = Member.builder().name("euna").realAge(24).build();
        Question question1 = Question.builder().build();
        Question question2 = Question.builder().build();
        Answer answer1 = Answer.builder().question(question1).answerType(true).answerScore(2).build();
        Answer answer2 = Answer.builder().question(question2).answerType(false).answerScore(3).build();
        ResultResponse mockResponse = new ResultResponse("euna",29, "말랑한 깜찍이", "뭐가 뭔지 이제 조금 알 것 같은 당신! 앞으로도 재밌는 것들을 많이 알아가보자",
                "https://github.com/DO-SOPT-SOPKATHON-iOS4/SOPKATHON-Server/blob/main/image/Frame%2011951.png?raw=true",
                "https://github.com/DO-SOPT-SOPKATHON-iOS4/SOPKATHON-Server/blob/main/image/Result_ic_20.png?raw=true");

        BDDMockito.given(memberJpaRepository.findByName("euna")).willReturn(Optional.of(member));
        BDDMockito.given(questionJpaRepository.findById(1L)).willReturn(Optional.of(question1));
        BDDMockito.given(questionJpaRepository.findById(2L)).willReturn(Optional.of(question2));
        BDDMockito.given(answerJpaRepository.findByQuestionAndAnswerType(question1, true)).willReturn(Optional.of(answer1));
        BDDMockito.given(answerJpaRepository.findByQuestionAndAnswerType(question2, false)).willReturn(Optional.of(answer2));

        // when
        ResultResponse response = resultService.saveResult(request);

        // then
        assertThat(response).isEqualTo(mockResponse);
    }

    @Test
    @DisplayName("결과 저장 실패 - 회원을 찾을 수 없음")
    public void saveResultTest_FailMemberNotFound() {
        // given
        AnswerRequest answerRequest1 = new AnswerRequest(1L, true);
        AnswerRequest answerRequest2 = new AnswerRequest(2L, false);
        AnswerListRequest request = new AnswerListRequest("nonexistent", List.of(answerRequest1, answerRequest2));

        BDDMockito.given(memberJpaRepository.findByName("nonexistent")).willReturn(Optional.empty());

        // when, then
        assertThrows(CustomException.class, () -> resultService.saveResult(request));
    }

    @Test
    @DisplayName("모든 결과 조회 성공")
    public void getAllResultsTest() {
        // given
        Member member = Member.builder().name("euna").build();
        List<Result> resultList = List.of(
                Result.builder().id(1L).member(member).resultAge(29).testedDate(LocalDateTime.of(2024, Month.MARCH, 28, 10, 30)).build(),
                Result.builder().id(2L).member(member).resultAge(28).testedDate(LocalDateTime.of(2024, Month.MARCH, 28, 10, 30)).build()
        );
        BDDMockito.given(resultJpaRepository.findAllByMemberIdOrderByIdDesc(any())).willReturn(resultList);
        List<AllResultsResponse> mockResponse = List.of(
                AllResultsResponse.of(resultList.get(0), "말랑한 깜찍이", "뭐가 뭔지 이제 조금 알 것 같은 당신! 앞으로도 재밌는 것들을 많이 알아가보자",
                        "3월 28일",
                        "https://github.com/DO-SOPT-SOPKATHON-iOS4/SOPKATHON-Server/blob/main/image/Frame%2011951.png?raw=true",
                        "https://github.com/DO-SOPT-SOPKATHON-iOS4/SOPKATHON-Server/blob/main/image/Result_ic_20.png?raw=true"),
                AllResultsResponse.of(resultList.get(1), "말랑한 깜찍이", "뭐가 뭔지 이제 조금 알 것 같은 당신! 앞으로도 재밌는 것들을 많이 알아가보자",
                        "3월 28일",
                        "https://github.com/DO-SOPT-SOPKATHON-iOS4/SOPKATHON-Server/blob/main/image/Frame%2011951.png?raw=true",
                        "https://github.com/DO-SOPT-SOPKATHON-iOS4/SOPKATHON-Server/blob/main/image/Result_ic_20.png?raw=true")
        );
        // when
        List<AllResultsResponse> responses = resultService.getAllResults(1L);

        //then
        assertThat(responses).isEqualTo(mockResponse);
    }
}