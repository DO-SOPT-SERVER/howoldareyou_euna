package com.sopt.Server.service;

import com.sopt.Server.controller.request.MemberPostRequest;
import com.sopt.Server.controller.response.MemberGetResponse;
import com.sopt.Server.domain.Member;
import com.sopt.Server.repository.MemberJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MemberServiceTest {
    @Mock
    MemberJpaRepository memberJpaRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원을 등록할 수 있다")
    void saveMemberTest() {
        // given
        Member savedMember = Member.builder()
                .name("euna")
                .realAge(24)
                .build();
        BDDMockito.given(memberJpaRepository.findByNameOrThrow(any(String.class))).willReturn(savedMember);
        MemberPostRequest request = new MemberPostRequest("euna", 24);
        MemberGetResponse mockResponse = MemberGetResponse.of(savedMember);

        // when
        MemberGetResponse response = memberService.saveMember(request);

        //then
        Assertions.assertThat(response).isEqualTo(mockResponse);
    }

    // saveMember의 동작이 두개인데.. 이럴 때는 어떻게 나눠서 테스트해야할까
    // findByNameOrThrow에서 예외가 터지면 save를 동작시킴.. 이런 식일때
}