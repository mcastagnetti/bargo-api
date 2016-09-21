package com.bargo.bar;

import com.bargo.beer.Beer;
import com.bargo.beer.BeerRepository;
import com.bargo.View;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@RestController
public class BarController {

    @Autowired
    private BarRepository barDAO;

    @Autowired
    private BeerRepository beerDAO;

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

    @RequestMapping(value = "/bars/{id}/beers", method={RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity addBeerToBar(@PathVariable("id") Long id, @RequestBody List<Beer> beers) {
        Bar bar = barDAO.findOne(id);
        if (bar == null) {
            return new ResponseEntity("No Bar found for ID " + id, HttpStatus.NOT_FOUND);
        } else {
            List arrayBeers = new ArrayList<Beer>();
            for (Beer beer : beers) {

                if(beer.getId() != null) {
                    beer = beerDAO.findOne(beer.getId());
                }

                arrayBeers.add(beer);
            }

            bar.setBeers(arrayBeers);
            barDAO.save(bar);

            return new ResponseEntity(bar, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/bars", produces = "application/json")
    public ResponseEntity createBar(@RequestBody Bar bar) {
        barDAO.save(bar);
        return new ResponseEntity(bar, HttpStatus.OK);
    }
}