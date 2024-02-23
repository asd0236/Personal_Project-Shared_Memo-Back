package com.shareMemo.demo.domain.dto;

import com.shareMemo.demo.domain.entity.Memo;
import com.shareMemo.demo.domain.entity.Notebook;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoDto {

    private String title;
    private String content;

    public Memo toEntity(Notebook notebook){
        return Memo.builder()
                .notebook(notebook)
                .title(this.title)
                .content(this.content)
                .createDate(LocalDate.now())
                .build();
    }

}
