package com.awbd.myreviewer.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class DomainDTO {
    private Long id;
    private String name;
    private String image;
}
