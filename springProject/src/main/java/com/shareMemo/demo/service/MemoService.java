package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.dto.MemoDto;
import com.shareMemo.demo.domain.entity.Memo;

import java.util.List;
import java.util.Optional;

public interface MemoService {
    public List<Memo> getMemoList(Integer memberId, Integer notebookId);
    public Memo addMemo(Integer memberId, Integer notebookId, MemoDto memoDto);
    public Memo deleteMemo(Integer memberId, Integer memoId);
}
