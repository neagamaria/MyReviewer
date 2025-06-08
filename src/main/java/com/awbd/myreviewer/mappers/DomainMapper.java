package com.awbd.myreviewer.mappers;

import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.dtos.DomainDTO;
import org.springframework.stereotype.Component;

@Component
public class DomainMapper {
    public DomainDTO toDto(Domain domain) {
        Long id = domain.getId();
        String name = domain.getName();
        String image = domain.getImage();

        return new DomainDTO(id, name, image);
    }

    public Domain toDomain(DomainDTO domainDTO) {
        Domain domain = new Domain();
        domain.setId(domainDTO.getId());
        domain.setName(domainDTO.getName());
        domain.setImage(domainDTO.getImage());

        return domain;
    }
}

