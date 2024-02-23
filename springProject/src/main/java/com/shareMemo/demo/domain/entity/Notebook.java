package com.shareMemo.demo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notebook {
    @Id
    @GeneratedValue
    private Integer notebookId;
    private String name;
    private LocalDate createDate;

}
