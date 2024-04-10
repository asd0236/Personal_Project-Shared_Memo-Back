package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.entity.Notebook;

import java.util.List;

public interface NotebookService {
     List<Notebook> getNotebookList(Long memberId);
    Notebook addNotebook(Long memberId, String notebookName);
    Notebook deleteMemo(Long memberId, Integer notebookId);
}
