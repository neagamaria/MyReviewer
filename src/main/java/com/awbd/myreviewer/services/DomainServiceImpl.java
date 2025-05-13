package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.mappers.DomainMapper;
import com.awbd.myreviewer.repositories.DomainRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainServiceImpl implements DomainService{

    private DomainRepository domainRepository;
    private DomainMapper domainMapper;

    DomainServiceImpl(DomainRepository domainRepository, DomainMapper domainMapper){
        this.domainRepository = domainRepository;
        this.domainMapper = domainMapper;
    }

    @Override
    public List<DomainDTO> findAll(){
        List<Domain> domains = new LinkedList<>();
        domainRepository.findAll().iterator().forEachRemaining(domains::add);

        return domains.stream().map(domainMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DomainDTO> findByIds(List<Long> ids) {
        List<Domain> domains = domainRepository.findByIdIn(ids);

        return domains.stream().map(domain -> new DomainDTO(domain.getId(), domain.getName(), domain.getImage())).collect(Collectors.toList());
    }

    @Override
    public DomainDTO findById(Long l) {
        Optional<Domain> categoryOptional = domainRepository.findById(l);
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Domain not found!");
        }

        return domainMapper.toDto(categoryOptional.get());
    }

    @Override
    public DomainDTO save(DomainDTO categoryDto) {
        Domain savedCategory = domainRepository.save(domainMapper.toDomain(categoryDto));
        return domainMapper.toDto(savedCategory);
    }

    @Override
    public void deleteById(Long id) {
        domainRepository.deleteById(id);
    }


}
