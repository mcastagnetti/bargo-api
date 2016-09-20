package com.bargo;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class ApiController {

    @Autowired
    private BarRepository barRepository;

    @JsonView(View.Summary.class)
    @RequestMapping("/bars")
    public Iterable<Bar> getBars() {
        Bar bar = new Bar("Test", "Test", "Test", "test");
        Bar newbar = barRepository.save(bar);
        return barRepository.findAll();
    }
}