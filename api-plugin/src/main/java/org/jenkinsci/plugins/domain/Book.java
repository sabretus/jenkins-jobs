package org.jenkinsci.plugins.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Book {
    private String id;
    private String name;
    private String author;
    private String published;
    private String price;
}