
package com.example.demo.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.mock.domain.Member;
import com.example.demo.mock.domain.Study;
import com.example.demo.mock.member.MemberService;
import com.example.demo.mock.study.StudyRepository;
import com.example.demo.mock.study.StudyService;


@ExtendWith(MockitoExtension.class)
public class createStudyService {
	// 메소드로 만드는 방법
//	MemberService memberServiceMock = Mockito.mock(MemberService.class);
//	StudyRepository studyRepoMock = Mockito.mock(StudyRepository.class);
	
	@Mock
	MemberService memberServiceMock;
	
	@Mock
	StudyRepository studyRepoMock;
	
	@Test
	void createStudyService() {
		StudyService studyService = new StudyService(memberServiceMock, studyRepoMock);
		assertNotNull(studyService);
		
		
		
		Member member = new Member();
		member.setId(1L);
		member.setEmail("imgusah@gmail.com");;
		Study study = new Study(10, "java");
		
		// bdd
		given(memberServiceMock.findById(1L)).willReturn(Optional.of(member));
		//
		// tdd
//		when(memberServiceMock.findById(any()))
//			.thenReturn(Optional.of(member));
//			.thenThrow(new RuntimeException())
//			.thenReturn(Optional.empty());
		
		// bdd
		given(studyRepoMock.save(study)).willReturn(study);
		// tdd
//		when(studyRepoMock.save(study)).thenReturn(study);
		
		
		Optional<Member> m1 = memberServiceMock.findById(1L);
		assertEquals("imgusah@gmail.com", m1.get().getEmail());
//		assertThrows(RuntimeException.class, () -> {
//			memberServiceMock.findById(2L);
//		});
//		assertEquals(Optional.empty(), memberServiceMock.findById(3L));
		
		studyService.createNewStudy(1L, study);
		assertEquals(member, study.getOwner());
		
		
		// bdd
		then(memberServiceMock).should(times(1)).notify(study);
		// tdd
		verify(memberServiceMock, times(1)).notify(study);
		
		// bdd
		then(memberServiceMock).shouldHaveNoMoreInteractions();
		// tdd
		verify(memberServiceMock, never()).validate(any());
		
		InOrder inOrder = inOrder(memberServiceMock);
		inOrder.verify(memberServiceMock).notify(study);
		
		verifyNoMoreInteractions(memberServiceMock);
	}
}