package com.shareMemo.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class MemberNotebook {
    @Id
    @GeneratedValue
    private Integer memberNotebookId;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

    @Builder
    public MemberNotebook(Member member, Notebook notebook){
        this.member = member;
        this.notebook = notebook;
    }
}
