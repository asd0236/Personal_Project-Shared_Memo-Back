package com.shareMemo.demo.repository;

import com.shareMemo.demo.domain.entity.MemberNotebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberNotebookRepository extends JpaRepository<MemberNotebook, Integer> {
}
