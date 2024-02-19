package ncnk.make.backendroadmap.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Solved;
import ncnk.make.backendroadmap.domain.repository.SolvedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SolvedService {
    private final SolvedRepository solvedRepository;

    public Page<Solved> getSolved(Member member, Pageable pageable) {
        return solvedRepository.findSolvedByMember(member, pageable);
    }
}
