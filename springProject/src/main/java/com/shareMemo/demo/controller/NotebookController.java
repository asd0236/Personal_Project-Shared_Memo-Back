package com.shareMemo.demo.controller;


import com.shareMemo.demo.domain.entity.Memo;
import com.shareMemo.demo.domain.entity.Notebook;
import com.shareMemo.demo.service.NotebookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notebook")
public class NotebookController {

    private final NotebookService notebookService;

    @GetMapping
    @ResponseBody
    public List<Notebook> getNotebookList(@SessionAttribute(name = "memberId", required = false) Integer memberId) {
        return notebookService.getNotebookList(memberId);
    }

    @PostMapping("/add")
    @ResponseBody
    public Notebook addMemo(@SessionAttribute(name = "memberId", required = false) Integer memberId,
                            @RequestParam String notebookName) { // 1:1 매핑인 경우 @RequestParam 사용
        return notebookService.addNotebook(memberId, notebookName);
    }

    @DeleteMapping("/del/{notebookId}")
    @ResponseBody
    public Notebook deleteMemo(@SessionAttribute(name = "memberId", required = false) Integer memberId,
                           @PathVariable(value = "notebookId") Integer notebookId){
        return notebookService.deleteMemo(memberId, notebookId);
    }

}
