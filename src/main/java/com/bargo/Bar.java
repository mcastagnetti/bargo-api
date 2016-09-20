package com.bargo;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
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

    @Column(nullable = false)
    private Date created_at;

    @Column(nullable = false)
    private Date updated_at;

    @ManyToMany(targetEntity = Beer.class, cascade = {CascadeType.ALL})
    @JoinTable(name = "bars_has_beers", joinColumns = { @JoinColumn(name = "bar_id") },
            inverseJoinColumns = { @JoinColumn(name = "beer_id") })
    private List<Beer> beers;

    public Bar(String name, String lat, String lon, String place_id) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.place_id = place_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(View.Summary.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(View.Summary.class)
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonView(View.Summary.class)
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @JsonView(View.Summary.class)
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

    @PrePersist
    protected void onCreate() {
        created_at = new Date();
        updated_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }
}
