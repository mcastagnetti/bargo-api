package com.bargo.bar;

import com.bargo.beer.Beer;
import com.bargo.beer.BeerRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class BarController {

    @Autowired
    private BarRepository barDAO;

    @Autowired
    private BeerRepository beerDAO;

    @GetMapping("/bars")
    public ResponseEntity getBars() {

        List<Bar> bars = (List<Bar>) barDAO.findAll();

        if (bars == null) {
            return new ResponseEntity("No Bar found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(bars, HttpStatus.OK);
    }

    @PostMapping(value = "/bars", produces = "application/json")
    public ResponseEntity createBar(@RequestBody Bar bar) {
        barDAO.save(bar);
        return new ResponseEntity(bar, HttpStatus.OK);
    }

    @GetMapping("/bars/{id}")
    public ResponseEntity getBar(@PathVariable("id") Long id) {

        Bar bar = barDAO.findOne(id);
        if (bar == null) {
            return new ResponseEntity("No Bar found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(bar, HttpStatus.OK);
    }

    @PutMapping("/bars/{id}")
    public ResponseEntity updateBar(@PathVariable Long id, @RequestBody Bar updated) {

        Bar bar = barDAO.findOne(id);
        if (bar == null) {
            return new ResponseEntity("No Bar found for ID " + id, HttpStatus.NOT_FOUND);
        }

        bar.setLat(updated.getLat());
        bar.setLon(updated.getLon());
        bar.setName(updated.getName());
        bar.setPlace_id(updated.getPlace_id());

        barDAO.save(bar);

        return new ResponseEntity(bar, HttpStatus.OK);
    }

    @PostMapping(value = "/bars/{id}/beers")
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

    @GetMapping("/bars/{id}/beers")
    public ResponseEntity getBeers(@PathVariable("id") Long id) {

        Bar bar = barDAO.findOne(id);
        if (bar == null) {
            return new ResponseEntity("No Beers found for bar with ID " + id, HttpStatus.NOT_FOUND);
        }

        List<Beer> beers = bar.getBeers();

        return new ResponseEntity(beers, HttpStatus.OK);
    }
}