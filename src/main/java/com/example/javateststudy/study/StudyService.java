package com.example.javateststudy.study;

import com.example.javateststudy.domain.Member;
import com.example.javateststudy.domain.Study;
import com.example.javateststudy.member.MemberService;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
//        study.setOwnerId(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: " + memberId)));
        Study newstudy = repository.save(study);
        memberService.notify(newstudy);
        memberService.notify(member.get());
        return newstudy;
    }
}
