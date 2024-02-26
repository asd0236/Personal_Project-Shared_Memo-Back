package com.shareMemo.demo.service.impl;

import com.shareMemo.demo.domain.dto.MemberNotebookDto;
import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.domain.entity.MemberNotebook;
import com.shareMemo.demo.domain.entity.Notebook;
import com.shareMemo.demo.repository.MemberNotebookRepository;
import com.shareMemo.demo.repository.MemberRepository;
import com.shareMemo.demo.repository.NotebookRepository;
import com.shareMemo.demo.service.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotebookServiceImpl implements NotebookService {

    private final MemberNotebookRepository memberNotebookRepository;
    private final MemberRepository memberRepository;
    private final NotebookRepository notebookRepository;

    public List<Notebook> getNotebookList(Integer memberId) {
        // memberId에 해당하는 회원 노트북 리스트 조회
        Optional<MemberNotebook> memberNotebooks = memberNotebookRepository.findByMember_MemberId(memberId);

        // 회원의 노트북 리스트에서 노트북 객체들을 추출하여 반환
        return memberNotebooks.stream()
                .map(MemberNotebook::getNotebook)
                .collect(Collectors.toList());
    }

    public Notebook addNotebook(Integer memberId, String notebookName) {
        if(memberId == null) // 로그인중이 아닐 때
            return Notebook.builder()
                    .notebookId(null)
                    .name(null)
                    .createDate(null)
                    .build();
        
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if(optionalMember.isEmpty()) // 없는 유저의 정보를 조회할 때
            return Notebook.builder()
                    .notebookId(null)
                    .name(null)
                    .createDate(null)
                    .build();

        Member member = optionalMember.get();
        Notebook savedNotebook = notebookRepository.save(
                Notebook.builder()
                        .name(notebookName)
                        .createDate(LocalDate.now())
                        .build()
        ); // 노트북  정보 저장
        MemberNotebookDto memberNotebookDto = new MemberNotebookDto(member, savedNotebook);

        memberNotebookRepository.save(memberNotebookDto.toEntity()); // 멤버-노트북 중간 테이블 정보 저장

        return savedNotebook;

    }

    public Notebook deleteMemo(Integer memberId, Integer notebookId) {
        if(memberId == null) // 로그인중이 아닐 때
            return Notebook.builder()
                    .notebookId(null)
                    .name(null)
                    .createDate(null)
                    .build();

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        // 없는 유저 또는 없느 노트북 정보를 조회할 때
        if(memberRepository.findById(memberId).isEmpty() || notebookRepository.findById(notebookId).isEmpty())
            return Notebook.builder()
                    .notebookId(null)
                    .name(null)
                    .createDate(null)
                    .build();

        Notebook targetNotebook = notebookRepository.findById(notebookId).get();
        notebookRepository.deleteById(notebookId);
        return targetNotebook;
    }

}
