package com.shareMemo.demo.service.impl;

import com.shareMemo.demo.domain.dto.MemoDto;
import com.shareMemo.demo.domain.entity.Memo;
import com.shareMemo.demo.domain.entity.Notebook;
import com.shareMemo.demo.repository.MemberNotebookRepository;
import com.shareMemo.demo.repository.MemoRepository;
import com.shareMemo.demo.repository.NotebookRepository;
import com.shareMemo.demo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final NotebookRepository notebookRepository;
    private final MemberNotebookRepository memberNotebookRepository;

    public Optional<Memo> getMemoList(Integer memberId, Integer notebookId){
        // 멤버가 해당 노트북을 갖고 있는지 확인
        if(!memberNotebookRepository.existsByMember_MemberIdAndNotebook_NotebookId(memberId, notebookId))
            return Optional.empty();
        return memoRepository.findByNotebook_NotebookId(notebookId);
    }

    public Memo addMemo(Integer memberId, Integer notebookId, MemoDto memoDto){
        // 멤버가 해당 노트북을 갖고 있는지 확인
        if(memberId == null ||
                !memberNotebookRepository.existsByMember_MemberIdAndNotebook_NotebookId(memberId, notebookId))
            return Memo.builder()
                    .notebook(null)
                    .title(null)
                    .content(null)
                    .createDate(null)
                    .build();

        // 기본키 값으로 notebookId를 가진 Notebook 객체를 가져옴
        Optional<Notebook> currentNotebook = notebookRepository.findById(notebookId);

        // currentNotebook가 존재하면 memoRepository에 해당 노트북을 외래키로 한 객체 넣기
        return currentNotebook.map(notebook -> memoRepository.save(memoDto.toEntity(notebook)))
                .orElseGet(() -> Memo.builder()
                .notebook(null)
                .title(null)
                .content(null)
                .createDate(null)
                .build());

    }

}
