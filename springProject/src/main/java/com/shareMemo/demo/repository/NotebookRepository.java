package com.shareMemo.demo.repository;

import com.shareMemo.demo.domain.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook, Integer> {
}
