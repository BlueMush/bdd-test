
package com.example.demo.mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
		
		
		//	�ѤѤѤѤѤѤ�Stubbing�ѤѤѤѤѤѤ�
		given(memberServiceMock.findById(1L))				//	given MemberService class�� findById�� �Ű������� 1L ȣ��Ǹ�
				.willReturn(Optional.of(optMember))			//	willReturn Null�� �ƴ�(Optional.of��) optMember ��ü�� ������
				.willThrow(new RuntimeException());			//	�ι�° ȣ�� �� RuntimeException�� ����
		
		doThrow(new RuntimeException()).when(memberServiceMock).validate(1L);				//	doThrow ������ -> RuntimeException��;;
																							//	when memberService�� validate�� 1L ������ ȣ�������
		// �ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
		
		Study study = new Study(10, "java");
		studyService.createNewStudy(1L, study);		//	studyService class�� createNewStudy �޼��� ù��° ȣ��
		
		assertThrows(RuntimeException.class, ()-> {
			memberServiceMock.findById(1L);			//	studyService class�� createNewStudy �޼��� �ι�° ȣ��
		});
		
		assertThrows(RuntimeException.class, ()-> {
			memberServiceMock.validate(1L);				//	memberService interface�� validate �޼��� ȣ��
		});
		
		
	}
}
