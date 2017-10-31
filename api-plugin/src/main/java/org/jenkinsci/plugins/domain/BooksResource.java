package org.jenkinsci.plugins.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BooksResource {
    private List<Book> books;
    private boolean err;
}
