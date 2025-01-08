package com.piisw.kino.rest.exceptions;

import com.piisw.kino.rest.exceptions.impl.ErrorBase;

// wiem wiem można by pozostać przy id ale typ string a long + kode jest bardziej reprezentujący dla encji tickets
// można skorzystać z tego enuma i guess xd
public class CodeEntityNotFoundException extends ErrorBase {
    public CodeEntityNotFoundException(String code) {
        super("Ticket with code: " + code + ", doesn't exist");
    }
}