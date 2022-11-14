package com.example.javateststudy.study;

import com.example.javateststudy.domain.Member;
import com.example.javateststudy.domain.Study;
import com.example.javateststudy.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
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
}