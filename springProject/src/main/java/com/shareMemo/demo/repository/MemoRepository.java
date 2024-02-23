package com.shareMemo.demo.repository;

import com.shareMemo.demo.domain.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Integer> {
    public Optional<Memo> findByNotebook_NotebookId(Integer notebookId);
}
