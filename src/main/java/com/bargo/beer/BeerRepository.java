package com.bargo.beer;

import com.bargo.beer.Beer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by marco on 20/09/2016.
 */
public interface BeerRepository extends CrudRepository<Beer, Long> {
}
