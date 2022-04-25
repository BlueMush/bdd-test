package com.example.demo.mock.study;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.mock.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
