package com.mvicente.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Author(String name,
                     String birth_year,
                     String death_year) {
}
