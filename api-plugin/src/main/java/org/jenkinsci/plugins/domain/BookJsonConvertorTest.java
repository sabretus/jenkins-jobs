package org.jenkinsci.plugins.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookJsonConvertorTest {

    @Test
    public void readBooks() throws Exception {
        final String jsonString = "{  \n" +
                "   \"books\":[  \n" +
                "      {  \n" +
                "         \"id\":\"some_hash_id_1\",\n" +
                "         \"name\":\"some_book_name_1\",\n" +
                "         \"author\":\"some_author_1\",\n" +
                "         \"published\":\"01.01.2011\",\n" +
                "         \"price\":99.99\n" +
                "      },\n" +
                "      {  \n" +
                "         \"id\":\"some_hash_id_2\",\n" +
                "         \"name\":\"some_book_name_2\",\n" +
                "         \"author\":\"some_author_2\",\n" +
                "         \"published\":\"02.02.2012\",\n" +
                "         \"price\":199.0\n" +
                "      }\n" +
                "   ],\n" +
                "   \"error\":false\n" +
                "}";
        BookJsonConvertor bookJsonConvertor = new BookJsonConvertor();

        BooksResource booksResource = bookJsonConvertor.readBooks(jsonString);

        assertEquals(2, booksResource.getBooks().size());

        assertEquals("some_hash_id_1", booksResource.getBooks().get(0).getId());
        assertEquals("some_book_name_1", booksResource.getBooks().get(0).getName());
        assertEquals("some_author_1", booksResource.getBooks().get(0).getAuthor());
        assertEquals("01.01.2011", booksResource.getBooks().get(0).getPublished());
        assertEquals("99.99", booksResource.getBooks().get(0).getPrice());

        //TODO update to index=1 and change values
        assertEquals("some_hash_id_1", booksResource.getBooks().get(0).getId());
        assertEquals("some_book_name_1", booksResource.getBooks().get(0).getName());
        assertEquals("some_author_1", booksResource.getBooks().get(0).getAuthor());
        assertEquals("01.01.2011", booksResource.getBooks().get(0).getPublished());
        assertEquals("99.99", booksResource.getBooks().get(0).getPrice());

    }

}