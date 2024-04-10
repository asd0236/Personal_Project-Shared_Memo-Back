package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.dto.MemoDto;
import com.shareMemo.demo.domain.entity.Memo;

import java.util.List;
import java.util.Optional;

public interface MemoService {
    public List<Memo> getMemoList(Long memberId, Integer notebookId);
    public Memo addMemo(Long memberId, Integer notebookId, MemoDto memoDto);
    public Memo deleteMemo(Long memberId, Integer memoId);
}
