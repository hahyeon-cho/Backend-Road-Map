package com.make.backendroadmap.domain.service;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.Solved;
import com.make.backendroadmap.domain.repository.SolvedRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SolvedService {
    private final SolvedRepository solvedRepository;

    public List<Solved> getSolved(Member member) {
        return solvedRepository.findSolvedByMember(member);
    }
}
