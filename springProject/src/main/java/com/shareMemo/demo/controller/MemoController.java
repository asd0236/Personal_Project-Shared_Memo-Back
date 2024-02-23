package com.shareMemo.demo.controller;

import com.shareMemo.demo.domain.dto.MemoDto;
import com.shareMemo.demo.domain.entity.Memo;
import com.shareMemo.demo.domain.entity.Notebook;
import com.shareMemo.demo.service.MemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/{notebookId}")
    @ResponseBody
    public Optional<Memo> memoList(@SessionAttribute(name = "memberId", required = false) Integer memberId,
                                   @PathVariable(value = "notebookId") Integer notebookId) {
        return memoService.getMemoList(memberId, notebookId);
    }

    @PostMapping("/add/{notebookId}")
    @ResponseBody
    public Memo addMemo(@SessionAttribute(name = "memberId", required = false) Integer memberId,
                                      @PathVariable(value = "notebookId") Integer notebookId,
                                      @ModelAttribute MemoDto memoDto) {
        log.info(String.valueOf(memoDto));
        return memoService.addMemo(memberId, notebookId, memoDto);
    }

}
