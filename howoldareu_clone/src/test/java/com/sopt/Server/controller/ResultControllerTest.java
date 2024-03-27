package com.sopt.Server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sopt.Server.controller.request.AnswerListRequest;
import com.sopt.Server.controller.request.AnswerRequest;
import com.sopt.Server.controller.response.AllResultsResponse;
import com.sopt.Server.controller.response.ResultResponse;
import com.sopt.Server.domain.Member;
import com.sopt.Server.domain.Result;
import com.sopt.Server.service.ResultService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResultControllerTest extends RestDocsSupport{
    private final ResultService resultService = mock(ResultService.class);
    private final static String RESULT_POST_URL = "/result";
    private final static String RESULTS_GET_URL = "/result/{memberId}";
    private final static Long memberId = 1L;

    @Override
    protected Object initializeController() {
        return new ResultController(resultService);
    }

    @Test
    @DisplayName("문답 결과를 저장한다")
    void saveResultTest() throws Exception {
        // given
        ResultResponse response = new ResultResponse("euna", 20, "성장중인 새싹이", "매일 매일 자라고 있는 당신! 다음 달엔 얼마나 발전했을지 궁금한 걸?", "img1", "img2");
        BDDMockito.given(resultService.saveResult(any(AnswerListRequest.class)))
                .willReturn(response);
        AnswerListRequest request = new AnswerListRequest("euna", List.of(new AnswerRequest(1L, true),
                new AnswerRequest(2L, false),
                new AnswerRequest(3L, true)));

        // when
        ResultActions result = mockMvc.perform(post(RESULT_POST_URL)
                .content(objectMapper.writeValueAsString(request))
                .contentType(json)
                .accept(json)
        );

        // then
        result.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("save-result",
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("results").type(JsonFieldType.ARRAY).description("회원 답변 결과"),
                                fieldWithPath("results[].questionId").type(JsonFieldType.NUMBER).description("질문 id"),
                                fieldWithPath("results[].answerType").type(JsonFieldType.BOOLEAN).description("질문에 대한 답변 결과")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("회원 식별자"),
                                fieldWithPath("data.resultAge").type(JsonFieldType.NUMBER).description("결과에 따른 회원 나이"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("검사 결과 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("검사 결과 설명"),
                                fieldWithPath("data.imgUrl1").type(JsonFieldType.STRING).description("검사 결과 카드 앞면 사진"),
                                fieldWithPath("data.imgUrl2").type(JsonFieldType.STRING).description("검사 결과 카드 뒷면 사진")
                        )
                ));
    }

    @Test
    @DisplayName("모든 문답 결과를 조회한다")
    void getAllResultsTest() throws Exception {
        // given
        Member member = new Member("euna", 24);
        List<AllResultsResponse> response = List.of(
                AllResultsResponse.of(
                        Result.builder()
                                .id(1L)
                                .member(member)
                                .resultAge(20)
                                .build(),
                        "성장중인 새싹이",
                        "매일 매일 자라고 있는 당신! 다음 달엔 얼마나 발전했을지 궁금한 걸?",
                        "2021년 10월 10일",
                        "img2",
                        "img2"),
                AllResultsResponse.of(
                        Result.builder()
                                .id(2L)
                                .member(member)
                                .resultAge(20)
                                .build(),
                        "성장중인 새싹이",
                        "매일 매일 자라고 있는 당신! 다음 달엔 얼마나 발전했을지 궁금한 걸?",
                        "2021년 10월 10일",
                        "img2",
                        "img2")
        );

        BDDMockito.given(resultService.getAllResults(1L))
                .willReturn(response);

        // when: pathParameters를 사용할거면 MockMvcBuilders 보다 RestDocumentationRequestBuilders를 이용하는 것이 좋다.
        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get(RESULTS_GET_URL, memberId));

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-results",
                        pathParameters(
                                parameterWithName("memberId").description("회원 식별자")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("결과 식별자"),
                                fieldWithPath("data[].resultAge").type(JsonFieldType.NUMBER).description("결과에 따른 회원 나이"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("검사 결과 제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("검사 결과 설명"),
                                fieldWithPath("data[].testedDate").type(JsonFieldType.STRING).description("검사 날짜"),
                                fieldWithPath("data[].imgUrl1").type(JsonFieldType.STRING).description("검사 결과 카드 앞면 사진"),
                                fieldWithPath("data[].imgUrl2").type(JsonFieldType.STRING).description("검사 결과 카드 뒷면 사진")
                        )
                ));
    }
}