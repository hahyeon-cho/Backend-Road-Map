package com.make.backendroadmap.domain.service;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.PracticeCode;
import com.make.backendroadmap.domain.repository.PracticeCodeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PracticeCodeService {
    private final PracticeCodeRepository practiceCodeRepository;

    public List<PracticeCode> getPracticesByMember(Member member) {
        return practiceCodeRepository.findPracticeCodesByMember(member);
    }
}
