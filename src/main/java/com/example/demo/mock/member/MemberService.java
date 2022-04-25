package com.example.demo.mock.member;

import java.util.Optional;

import com.example.demo.mock.domain.Member;
import com.example.demo.mock.domain.Study;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

	void notify(Study newStudy);
	
	void validate(Long memberId) throws RuntimeException;
}
