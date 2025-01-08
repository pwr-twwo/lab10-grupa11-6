package com.piisw.kino.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.piisw.kino.dto.TicketTO.TicketBookingRequest;
import com.piisw.kino.dto.TicketTO.TicketBookingResponse;
import com.piisw.kino.service.BookSerivce;
import com.piisw.kino.service.DataValidationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookSerivce bookSerivce;
    private final DataValidationService validator;


    @PostMapping("/book")
    public TicketBookingResponse bookTicket(@RequestBody TicketBookingRequest bookInfo) {
        validator.throwOnInvalidData(bookInfo);
        return bookSerivce.bookTicket(bookInfo);
    }
}
