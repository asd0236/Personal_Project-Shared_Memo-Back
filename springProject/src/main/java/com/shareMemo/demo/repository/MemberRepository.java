package com.shareMemo.demo.repository;

import com.shareMemo.demo.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findById(Long memberId);
    public boolean existsById(Long memberId);
    public Optional<Member> findByLoginId(String loginId);
    public boolean existsByLoginId(String loginId);
    public boolean existsByEmail(String email);
    boolean existsByKakaoId(Long memberId);
    Member findByKakaoId(Long memberId);
}
