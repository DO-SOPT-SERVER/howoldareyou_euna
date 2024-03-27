package com.sopt.Server.repository;

import com.sopt.Server.domain.Member;
import com.sopt.Server.domain.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ResultJpaRepositoryTest {
    @Autowired
    ResultJpaRepository resultJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @AfterEach
    void tearDown() {
        resultJpaRepository.deleteAllInBatch();
        memberJpaRepository.deleteAllInBatch();
    }

    @Test
    void findAllByMemberIdOrderByIdDesc() {
        // given
        Member member = Member.builder().name("euna").realAge(24).build();
        memberJpaRepository.save(member);
        resultJpaRepository.save(saveResult(member, 24));
        resultJpaRepository.save(saveResult(member, 26));

        // when
        List<Result> results = resultJpaRepository.findAllByMemberIdOrderByIdDesc(member.getId());

        // then
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(26, results.get(0).getResultAge());
        assertEquals(24, results.get(1).getResultAge());



    }

    private Result saveResult(Member member, int resultAge) {
        return Result.builder().member(member).resultAge(resultAge).build();
    }
}