package com.shareMemo.demo.repository;

import com.shareMemo.demo.domain.entity.MemberNotebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberNotebookRepository extends JpaRepository<MemberNotebook, Integer> {
    public List<MemberNotebook> findByMember_MemberId(Integer memberId);
    boolean existsByMember_MemberIdAndNotebook_NotebookId(Integer memberId, Integer notebookId);
}
