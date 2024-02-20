package com.shareMemo.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@ToString
@Getter
public class MemberNotebook {
    @Id
    @GeneratedValue
    private Integer memberNotebookId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;
}
