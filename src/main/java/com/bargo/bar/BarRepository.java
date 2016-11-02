package com.bargo.bar;

import com.bargo.bar.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {


    /*@Query("SELECT b, ( 3959 * acos( cos( radians(:lat) ) * cos( radians( lat ) ) * cos( radians( lon ) - radians(:lon) ) + sin( radians(:lat) ) * sin( radians( lat ) ) ) ) AS distance " +
            "FROM Bar HAVING distance < 25" +
            "ORDER BY distance LIMIT 0 , 50")
    List<Bar> findByGps(@Param("lat") String lat,
                        @Param("lon") String lon);*/
}
