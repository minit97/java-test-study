package com.example.javateststudy.study;

import com.example.javateststudy.domain.Member;
import com.example.javateststudy.domain.Study;
import com.example.javateststudy.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

//    @Mock
//    MemberService memberService;
//
//    @Mock
//    StudyRepository studyRepository;

    @Test
    void createStudyService(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
//        MemberService memberService = Mockito.mock(MemberService.class);
//        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }
}