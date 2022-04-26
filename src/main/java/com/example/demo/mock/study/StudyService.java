package com.example.demo.mock.study;

import java.util.Optional;

import com.example.demo.mock.domain.Member;
import com.example.demo.mock.domain.Study;
import com.example.demo.mock.member.MemberService;

public class StudyService {
    private final MemberService memberService;

    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
    	assert memberService != null;
    	assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {				//	�Ű����� 1L, study��ü ���� 
        Optional<Member> member = memberService.findById(memberId);			//	memberService class�� findById �޼��忡 1L���� ȣ��Ǹ�
        																	//	StudyService studyService = new StudyService(memberServiceMock, studyRepoMock);
        																	//	������ ������ �����Ͽ� member ��ü�� ���ϵ�
        
        study.setOwner(member.orElseThrow(()->												//	study Owner�� member�� set�ϰ� member�� null�̸�
        new RuntimeException("Member doesn't exist for id: '" + memberId + "'")));			//	RuntimeException�� �߻�
        
        Study newStudy = studyRepository.save(study);		//	
        memberService.notify(newStudy);						//	
        
        
        
        return newStudy;
    }
}
