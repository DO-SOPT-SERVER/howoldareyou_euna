package com.sopt.Server.repository;

import com.sopt.Server.domain.Member;
import com.sopt.Server.exception.model.CustomException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.sopt.Server.exception.Error.NOT_FOUND_MEMBER_EXCEPTION;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    default Member findByNameOrThrow(String name) {
        return findByName(name)
                .orElseThrow(() ->
                        new CustomException(NOT_FOUND_MEMBER_EXCEPTION, NOT_FOUND_MEMBER_EXCEPTION.getMessage()));
    }
}
