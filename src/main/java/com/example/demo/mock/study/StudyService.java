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

    public Study createNewStudy(Long memberId, Study study) {				//	매개변수 1L, study객체 전달 
        Optional<Member> member = memberService.findById(memberId);			//	memberService class의 findById 메서드에 1L값이 호출되면
        																	//	StudyService studyService = new StudyService(memberServiceMock, studyRepoMock);
        																	//	지정한 동작을 수행하여 member 객체가 리턴됨
        
        study.setOwner(member.orElseThrow(()->												//	study Owner에 member를 set하고 member가 null이면
        new RuntimeException("Member doesn't exist for id: '" + memberId + "'")));			//	RuntimeException을 발생
        
        Study newStudy = studyRepository.save(study);		//	
        memberService.notify(newStudy);						//	
        
        
        
        return newStudy;
    }
}
