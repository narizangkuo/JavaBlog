package com.tianmaying.model;

public class BlogAppException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String command;

    public BlogAppException(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Exception happened when executing command " + command;
    }
}