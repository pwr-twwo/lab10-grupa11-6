package com.piisw.kino.rest.exceptions.impl;

import lombok.Data;

public class ErrorBase extends RuntimeException {

    public ErrorBase(String message) { super(message); }

    @Data
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }

    public ErrorResponse getObject() {
        return new ErrorResponse(this.getMessage());
    }
}
