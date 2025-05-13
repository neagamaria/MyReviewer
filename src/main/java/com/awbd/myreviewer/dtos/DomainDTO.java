package com.awbd.myreviewer.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class DomainDTO {
    private Long id;
    private String name;
    private String image;
}
