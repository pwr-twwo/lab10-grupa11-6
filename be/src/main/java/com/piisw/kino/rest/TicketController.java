package com.piisw.kino.rest;


import com.piisw.kino.dto.TicketTO.TicketResponse;
import com.piisw.kino.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets/{code}")
    public TicketResponse findByCode(@PathVariable String code) {
        return ticketService.findTicketByCode(code);
    }

    @PatchMapping("/tickets/{code}/punch")
    public void punchTicket(@PathVariable String code) {
       ticketService.punchTicket(code);
    }

}

