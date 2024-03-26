package com.sopt.Server.controller;

import com.sopt.Server.controller.request.MemberPostRequest;
import com.sopt.Server.controller.response.MemberGetResponse;
import com.sopt.Server.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends RestDocsSupport {
    private final MemberService memberService = mock(MemberService.class);
    private static final String MEMBER_POST_URL = "/member";

    @Override
    protected Object initializeController() {
        return new MemberController(memberService);
    }

    @Test
    @DisplayName("사용자를 등록한다")
    void saveMemberTest() throws Exception {
        // given
        MemberGetResponse response = new MemberGetResponse(1L, "euna", 24);
        BDDMockito.given(memberService.saveMember(any(MemberPostRequest.class))).willReturn(response);
        MemberPostRequest request = new MemberPostRequest("euna", 24);

        // when
        ResultActions result = this.mockMvc.perform(
                post(MEMBER_POST_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(json)
                        .accept(json)
        );

        //then
        result.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("save-member",
                        requestFields(
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data.realAge").type(JsonFieldType.NUMBER).description("회원 나이")
                        )
                ));
    }
}