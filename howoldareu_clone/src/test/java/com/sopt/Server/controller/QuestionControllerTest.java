package com.sopt.Server.controller;

import com.sopt.Server.controller.response.GetQuestionResponse;
import com.sopt.Server.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionControllerTest extends RestDocsSupport{
    private final QuestionService questionService = mock(QuestionService.class);
    private final static String QUESTION_GET_URL = "/question";

    @Override
    protected Object initializeController() {
        return new QuestionController(questionService);
    }

    @Test
    @DisplayName("질문 목록을 조회한다")
    void getQuestionResponseListTest() throws Exception {
        // given
        List<GetQuestionResponse> responseList = List.of(
                new GetQuestionResponse(1L, "인생이 부질없다고 생각해본 적 있어?"),
                new GetQuestionResponse(2L, "술먹다가 첫차타고 집간적 있어?"));
        BDDMockito.given(questionService.getQuestionResponseList())
                .willReturn(responseList);

        // when
        ResultActions result = mockMvc.perform(get(QUESTION_GET_URL));

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-questions",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                fieldWithPath("data[].questionContent").type(JsonFieldType.STRING).description("질문 내용")
                        )
                ));
    }


}