package com.shareMemo.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignResponse {

    public String resultString;
    public boolean isSuccess;

}
