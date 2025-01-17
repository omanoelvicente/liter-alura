package com.mvicente.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(String title,
                   List<String> languages,
                   String download_count,
                   List<Author> authors)
{
}
