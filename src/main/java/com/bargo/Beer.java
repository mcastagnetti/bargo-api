package com.bargo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Beer implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "beers", cascade = CascadeType.ALL)
    private List<Bar> bars;

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

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }
}
