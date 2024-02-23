package com.shareMemo.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class MemberNotebook {
    @Id
    @GeneratedValue
    private Integer memberNotebookId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

    @Builder
    public MemberNotebook(Member member, Notebook notebook){
        this.member = member;
        this.notebook = notebook;
    }
}
