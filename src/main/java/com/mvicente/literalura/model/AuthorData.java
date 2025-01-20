package com.mvicente.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(String name,
                         String birth_year,
                         String death_year) {
}
