package com.bargo.bar;

import com.bargo.bar.Bar;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by marco on 20/09/2016.
 */
public interface BarRepository extends CrudRepository<Bar, Long> {
}
