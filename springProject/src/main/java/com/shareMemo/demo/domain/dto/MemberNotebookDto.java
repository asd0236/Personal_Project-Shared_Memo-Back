package com.shareMemo.demo.domain.dto;

import com.shareMemo.demo.domain.entity.Member;
import com.shareMemo.demo.domain.entity.MemberNotebook;
import com.shareMemo.demo.domain.entity.Notebook;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberNotebookDto {

    private Member member;
    private Notebook notebook;

    public MemberNotebook toEntity(){
        return MemberNotebook.builder()
                .member(this.member)
                .notebook(this.notebook)
                .build();
    }

}
