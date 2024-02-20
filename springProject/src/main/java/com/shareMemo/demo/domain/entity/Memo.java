package com.shareMemo.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    @Id
    @GeneratedValue
    Integer memoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
}
