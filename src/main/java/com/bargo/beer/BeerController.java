package com.bargo.beer;

import com.bargo.beer.Beer;
import com.bargo.bar.Bar;
import com.bargo.bar.BarRepository;
import com.bargo.beer.BeerRepository;

import javax.persistence.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class BeerController {

    @Autowired
    private BarRepository barDAO;

    @Autowired
    private BeerRepository beerDAO;

    @PersistenceContext
    private EntityManager manager;


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
    public ResponseEntity getBeersForBar(@PathVariable("id") Long id) {

        Bar bar = barDAO.findOne(id);
        if (bar == null) {
            return new ResponseEntity("No Beers found for bar with ID " + id, HttpStatus.NOT_FOUND);
        }

        List<Beer> beers = bar.getBeers();

        return new ResponseEntity(beers, HttpStatus.OK);
    }

    @PutMapping("/beers/{id}")
    public ResponseEntity updateBeer(@PathVariable Long id, @RequestBody Beer updated) {

        Beer beer = beerDAO.findOne(id);
        if (beer == null) {
            return new ResponseEntity("No Beer found for ID " + id, HttpStatus.NOT_FOUND);
        }

        beer.setName(updated.getName());

        beerDAO.save(beer);

        return new ResponseEntity(beer, HttpStatus.OK);
    }

    @DeleteMapping("bars/{bar_id}/beers/{beer_id}")
    public ResponseEntity deleteBeerForBar(@PathVariable Long bar_id, @PathVariable Long beer_id) {

        Beer beer = beerDAO.findOne(beer_id);
        Bar bar   = barDAO.findOne(bar_id);

        if (beer == null) {
            return new ResponseEntity("No Beer found for ID " + beer_id, HttpStatus.NOT_FOUND);
        }
        if (bar == null) {
            return new ResponseEntity("No bar found for ID " + bar_id, HttpStatus.NOT_FOUND);
        }

        List<Beer> beers = bar.getBeers();
        beers.remove(beer);

        bar.setBeers(beers);
        barDAO.save(bar);

        return new ResponseEntity("Beer " + beer_id + " as been deleted", HttpStatus.OK);
    }

    @GetMapping("beers/{id}/bars")
    public ResponseEntity getBarFromBeer(@PathVariable Long id) {

        Beer beer   = beerDAO.findOne(id);

        if (beer == null) {
            return new ResponseEntity("No Beer found for ID " + id, HttpStatus.NOT_FOUND);
        }

        List<Bar> bars = beer.getBars();

        return new ResponseEntity(bars, HttpStatus.OK);
    }
}
