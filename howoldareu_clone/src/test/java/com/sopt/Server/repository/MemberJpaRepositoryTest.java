package com.sopt.Server.repository;

import com.sopt.Server.domain.Member;
import com.sopt.Server.exception.model.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @AfterEach
    void tearDown() {
        memberJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("이름으로 회원을 조회할 수 있다.")
    void findByNameTest() {

        // given
        Member member = Member.builder()
                .name("euna")
                .realAge(24)
                .build();
        memberJpaRepository.save(member);

        // when
        Optional<Member> findMember = memberJpaRepository.findByName("euna");

        // then
        findMember.ifPresent(m ->
                Assertions.assertThat(m)
                        .extracting("name", "realAge")
                        .containsExactly("euna", 24)
        );

    }


    @Test
    @DisplayName("이름으로 회원을 조회할 수 있다.")
    void findByNameOrThrowSuccessTest() {
        // given
        Member member = Member.builder()
                .name("euna")
                .realAge(24)
                .build();
        memberJpaRepository.save(member);

        // when
        Member findMember = memberJpaRepository.findByNameOrThrow("euna");

        // then
        Assertions.assertThat(findMember)
                .extracting("name", "realAge")
                .containsExactly("euna", 24);
    }

    @Test
    @DisplayName("이름으로 회원을 조회할 수 없으면 예외가 발생한다.")
    void findByNameOrThrowFailTest() {
        // given, when, then
        assertThatThrownBy(() -> memberJpaRepository.findByNameOrThrow("euna"))
                .isInstanceOf(CustomException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다");
    }

    /**
     * memberJpaRepository.findByNameOrThrow에서 import jakarta.persistence.EntityNotFoundException;를 던지면,
     * 실제로는 org.springframework.orm.jpa.JpaObjectRetrievalFailureException가 인식되어 테스트가 실패한다...
     * 그렇다면 custom Exception이 최선일까?
    **/
}