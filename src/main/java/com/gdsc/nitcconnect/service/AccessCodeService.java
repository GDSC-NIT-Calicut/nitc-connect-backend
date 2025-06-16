package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.AccessCode;
import com.gdsc.nitcconnect.repository.AccessCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class AccessCodeService {

    private final AccessCodeRepository accessCodeRepository;

    @Autowired
    public AccessCodeService(AccessCodeRepository accessCodeRepository) {
        this.accessCodeRepository = accessCodeRepository;
    }

    public Optional<Integer> getIgIdByValidCode(String code) {
        return accessCodeRepository.findIgIdByValidCode(code, LocalDateTime.now());
    }

    public AccessCode createAccessCode(AccessCode accessCode){
        accessCode.setCreatedAt(LocalDateTime.now());
        return accessCodeRepository.save(accessCode);
    }
}