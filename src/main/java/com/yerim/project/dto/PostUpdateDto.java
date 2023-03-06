package com.yerim.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {
    private Long id;
    private String title;
    private String text;
}
