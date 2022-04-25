
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
	
	@Mock
	MemberService memberServiceMock;
	
	@Mock
	StudyRepository studyRepoMock;
	
	@Test
	void createStudyService() {
		
	}
}
