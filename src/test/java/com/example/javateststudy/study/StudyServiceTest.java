package com.example.javateststudy.study;

import com.example.javateststudy.domain.Member;
import com.example.javateststudy.domain.Study;
import com.example.javateststudy.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    void createNewService(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("phm@email.com");
        
        Mockito.when(memberService.findById(Mockito.any())).thenReturn(Optional.of(member));   // memberService.findById() 호출되면 Optional.of(member) 리턴

        assertEquals("phm@email.com",memberService.findById(1L).get().getEmail());
        assertEquals("phm@email.com",memberService.findById(2L).get().getEmail());

        Mockito.when(memberService.findById(1L)).thenThrow(new RuntimeException()); // 리턴타입이 있을 때 예외던지기
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(1L);   // 리턴타입이 void 일때
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        Mockito.when(memberService.findById(Mockito.any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());
        assertEquals("phm@email.com",memberService.findById(1L).get().getEmail());
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });
        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    @DisplayName("Mock객체 Stubbing 연습문제")
    void mockStubbingPractice(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10,"테스트");
        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 member 객체를 리턴하도록 Stubbing
        Member member = new Member();
        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));

        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        Mockito.when(studyRepository.save(study)).thenReturn(study);
        studyService.createNewStudy(1L, study);
        assertEquals(member, study.getOwnerId());

        Mockito.verify(memberService, Mockito.times(1)).notify(study);  // memberService.notify(study)의 호출횟수 확인
        Mockito.verify(memberService, Mockito.never()).validate(Mockito.any());  // memberService.validate() 은 호출 X

        // 순서 확인
        InOrder inOrder = Mockito.inOrder(memberService);
        inOrder.verify(memberService).notify(study);

        // 액션이 없어야 한다는 것을 테스트
        Mockito.verifyNoInteractions(memberService);

        inOrder.verify(memberService).notify(member);
    }
}