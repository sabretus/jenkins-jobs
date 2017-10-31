package org.jenkinsci.plugins.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BookJsonConvertor {

    private final Gson gson;

    public BookJsonConvertor() {
        GsonBuilder builder = new GsonBuilder();
        builder.setVersion(2.0);
        this.gson = builder.create();
    }

    public BooksResource readBooks(String jsonString) {
        return gson.fromJson(jsonString, BooksResource.class);
    }
}
