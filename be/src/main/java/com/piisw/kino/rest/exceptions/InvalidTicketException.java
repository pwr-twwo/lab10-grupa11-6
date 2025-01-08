package com.piisw.kino.rest.exceptions;

import com.piisw.kino.rest.exceptions.impl.ErrorBase;

public class InvalidTicketException extends ErrorBase {
    public InvalidTicketException() {
        super("Ticket invalid");
    }
    
}
