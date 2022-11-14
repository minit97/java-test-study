package com.example.javateststudy.member;

import com.example.javateststudy.domain.Member;
import com.example.javateststudy.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}