package com.piisw.kino.rest.exceptions;

import com.piisw.kino.rest.exceptions.impl.ErrorBase;

public class DataValidationException extends ErrorBase {
    public DataValidationException(String cause) {
        super(cause);
    }
    
}
