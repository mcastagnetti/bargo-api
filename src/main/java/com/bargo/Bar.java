package com.bargo;

/**
 * Created by marco on 20/09/2016.
 */

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
public class Bar implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lat;

    @Column(nullable = false)
    private String lon;

    @Column(nullable = false)
    private String place_id;

    @ManyToMany(targetEntity = Beer.class, cascade = {CascadeType.ALL})
    @JoinTable(name = "bars_has_beers", joinColumns = { @JoinColumn(name = "bar_id") },
            inverseJoinColumns = { @JoinColumn(name = "beer_id") })
    private List<Beer> beers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public List<Beer> getBeers() {
        return beers;
    }

    public void setBeers(List<Beer> beers) {
        this.beers = beers;
    }
}
