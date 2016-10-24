package com.bargo.bar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class BarController {

    @Autowired
    private BarRepository barDAO;

    @GetMapping("/bars")
    public ResponseEntity getBars() {

        List<Bar> bars = barDAO.findAll();

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

    @DeleteMapping("/bars/{id}")
    public ResponseEntity deleteBar(@PathVariable Long id) {

        Bar bar = barDAO.findOne(id);
        if (bar == null) {
            return new ResponseEntity("No Bar found for ID " + id, HttpStatus.NOT_FOUND);
        }

        barDAO.delete(bar);

        return new ResponseEntity("Bar " + id + " as been deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/bars/search", params = {"lat", "lon", "distance"})
    public ResponseEntity getBarByGps(@RequestParam("lat") Float lat, @RequestParam("lon") Float lon, @RequestParam("distance") Integer distance) {
        List<Bar> bars = barDAO.findAll();
        List<Bar> closest = new ArrayList<>();
        for (Bar bar : bars) {
            float test = getDistance(lat, lon, bar.getLat(), bar.getLon());
            if(distance <= test) {
                closest.add(bar);
            }
        }

        return new ResponseEntity(closest, HttpStatus.OK);
    }

    public static float getDistance(float lat1, float lon1, float lat2, float lon2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}