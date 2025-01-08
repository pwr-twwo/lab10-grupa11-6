package com.piisw.kino.rest.exceptions;

import com.piisw.kino.rest.exceptions.impl.ErrorBase;

public class EntityNotFoundException extends ErrorBase {
    public <T>EntityNotFoundException(Long id, Class<T> type) {
        super("Could not find entity " + type.getName() + " with id " + id);
    }
}
