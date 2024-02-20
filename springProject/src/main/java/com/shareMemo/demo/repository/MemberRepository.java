package com.shareMemo.demo.repository;

import com.shareMemo.demo.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public boolean existByLoginId(String loginId);
    public boolean existByNickname(String nickname);
}
