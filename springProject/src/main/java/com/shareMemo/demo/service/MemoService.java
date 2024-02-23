package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.dto.MemoDto;
import com.shareMemo.demo.domain.entity.Memo;

import java.util.Optional;

public interface MemoService {
    public Optional<Memo> getMemoList(Integer memberId, Integer notebookId);
    public Memo addMemo(Integer memberId, Integer notebookId, MemoDto memoDto);
}
