package com.shareMemo.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    @Id
    @GeneratedValue
    private Integer memoId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate createDate;
}
