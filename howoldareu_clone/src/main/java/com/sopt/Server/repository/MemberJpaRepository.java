package com.sopt.Server.repository;

import com.sopt.Server.domain.Member;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    default Member findByNameOrThrow(String name) {
        return findByName(name)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 이름의 회원이 존재하지 않습니다."));
    }
}
