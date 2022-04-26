
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

// Extension ���
@ExtendWith(MockitoExtension.class)
public class createStudyService {
	
	@Mock
	MemberService memberServiceMock;	//	Mock ��ü ���
	
	@Mock
	StudyRepository studyRepoMock;
	
	@Test
	void createStudyService() {
		StudyService studyService = new StudyService(memberServiceMock, studyRepoMock);		//	StudyService�� ����� MemberService, StudyRepogitory ��ü�� �����ϱ� ����
																							//	Mock���� ��� �� ������ ����
		Member optMember = new Member();		//	Member ��ü ����
		optMember.setId(1L);
		
		Study study = new Study(10, "java");
		//	�ѤѤѤѤѤѤ�Stubbing�ѤѤѤѤѤѤ�
		given(memberServiceMock.findById(1L))				//	given MemberService class�� findById�� �Ű������� 1L ȣ��Ǹ�
				.willReturn(Optional.of(optMember))			//	willReturn Null�� �ƴ�(Optional.of��) optMember ��ü�� ������
				.willThrow(new RuntimeException());			//	�ι�° ȣ�� �� RuntimeException�� ����
		
		given(studyRepoMock.save(study)).willReturn(study);
		
		doThrow(new RuntimeException()).when(memberServiceMock).validate(1L);				//	doThrow ������ -> RuntimeException��;;
																							//	when memberService�� validate�� 1L ������ ȣ�������
		// �ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
		
		studyService.createNewStudy(1L, study);		//	studyService class�� createNewStudy �޼��� ù��° ȣ��
		
		assertThrows(RuntimeException.class, ()-> {
			memberServiceMock.findById(1L);			//	studyService class�� createNewStudy �޼��� �ι�° ȣ��
		});
		
		assertThrows(RuntimeException.class, ()-> {
			memberServiceMock.validate(1L);				//	memberService interface�� validate �޼��� ȣ��
		});
		
		verify(memberServiceMock, times(1)).notify(study);	//	memberservice mock�� notify�� study���� ������ �ѹ� ȣ��Ǿ�� �ϴ°��� ����
		verify(memberServiceMock, never()).validate(2L);	//	2L ���� ������ �ѹ��� ȣ����� �ʴ��� ����
		
		verifyNoMoreInteractions(memberServiceMock);		//	�� �̻� mock��ü�� ������� �ʴ��� ����
	}
}
