package com.make.backendroadmap.domain.service;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.entity.PracticeCode;
import com.make.backendroadmap.domain.repository.PracticeCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PracticeCodeService {
    private final PracticeCodeRepository practiceCodeRepository;

    public Page<PracticeCode> getPracticesByMember(Member member, Pageable pageable) {
        return practiceCodeRepository.findPracticeCodesByMember(member, pageable);
    }

    public void save(String fileName, String filePath, Member member) {
        PracticeCode practiceCode = PracticeCode.createPracticeCode(fileName, String.valueOf(filePath), member);
        practiceCodeRepository.save(practiceCode);
    }
}
