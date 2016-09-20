package com.bargo;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class BarController {

    @Autowired
    private BarRepository barDAO;

    @JsonView(View.Summary.class)
    @GetMapping("/bars")
    public Iterable<Bar> getBars() {
        return barDAO.findAll();
    }

    @GetMapping("/bars/{id}")
    public ResponseEntity getBar(@PathVariable("id") Long id) {

        Bar bar = barDAO.findOne(id);
        if (bar == null) {
            return new ResponseEntity("No Bar found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(bar, HttpStatus.OK);
    }

    @PostMapping(value = "/bars", produces = "application/json")
    public ResponseEntity createBar(@RequestBody Bar bar) {
        barDAO.save(bar);
        return new ResponseEntity(bar, HttpStatus.OK);
    }
}