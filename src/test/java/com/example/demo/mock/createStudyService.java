
package com.example.demo.mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

// Extension 등록
@ExtendWith(MockitoExtension.class)
public class createStudyService {
	
	@Mock
	MemberService memberServiceMock;	//	Mock 객체 등록
	
	@Mock
	StudyRepository studyRepoMock;
	
	@Test
	void createStudyService() {
		StudyService studyService = new StudyService(memberServiceMock, studyRepoMock);		//	StudyService가 사용할 MemberService, StudyRepogitory 객체를 주입하기 위해
																							//	Mock으로 등록 후 의존성 주입
		Member optMember = new Member();		//	Member 객체 선언
		optMember.setId(1L);
		
		Study study = new Study(10, "java");
		//	ㅡㅡㅡㅡㅡㅡㅡStubbingㅡㅡㅡㅡㅡㅡㅡ
		given(memberServiceMock.findById(1L))				//	given MemberService class의 findById가 매개변수로 1L 호출되면
				.willReturn(Optional.of(optMember))			//	willReturn Null이 아닌(Optional.of인) optMember 객체를 리턴함
				.willThrow(new RuntimeException());			//	두번째 호출 시 RuntimeException을 던짐
		
		given(studyRepoMock.save(study)).willReturn(study);
		
		doThrow(new RuntimeException()).when(memberServiceMock).validate(1L);				//	doThrow 던지다 -> RuntimeException을;;
																							//	when memberService의 validate가 1L 값으로 호출됬을때
		// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		
		studyService.createNewStudy(1L, study);		//	studyService class의 createNewStudy 메서드 첫번째 호출
		
		assertThrows(RuntimeException.class, ()-> {
			memberServiceMock.findById(1L);			//	studyService class의 createNewStudy 메서드 두번째 호출
		});
		
		assertThrows(RuntimeException.class, ()-> {
			memberServiceMock.validate(1L);				//	memberService interface의 validate 메서드 호출
		});
		
		verify(memberServiceMock, times(1)).notify(study);	//	memberservice mock에 notify가 study값을 가지고 한번 호출되어야 하는것을 검증
		verify(memberServiceMock, never()).validate(2L);	//	2L 값을 가지고 한번도 호출되지 않는지 검증
		
		verifyNoMoreInteractions(memberServiceMock);		//	더 이상 mock객체를 사용하지 않는지 검증
	}
}
