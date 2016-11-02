package com.bargo.beer;

import com.bargo.beer.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by marco on 20/09/2016.
 */
public interface BeerRepository extends JpaRepository<Beer, Long> {
}
