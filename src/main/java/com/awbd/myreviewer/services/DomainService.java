package com.awbd.myreviewer.services;

import com.awbd.myreviewer.dtos.DomainDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DomainService {
    List<DomainDTO> findAll();
    DomainDTO findById(Long id);
    DomainDTO save(DomainDTO domain);
    void deleteById(Long id);
}
