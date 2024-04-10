package com.shareMemo.demo.service;

import com.shareMemo.demo.domain.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

public interface SessionLoginService {
     void sessionLogin(Member member, HttpServletRequest httpServletRequest);
}
