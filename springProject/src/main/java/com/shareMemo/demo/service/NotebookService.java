package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.entity.Notebook;

import java.util.List;

public interface NotebookService {
     List<Notebook> getNotebookList(Integer memberId);
    Notebook addNotebook(Integer memberId, String notebookName);
    Notebook deleteMemo(Integer memberId, Integer notebookId);
}
