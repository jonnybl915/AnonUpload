package com.theironyard.services;

import com.theironyard.entities.AnonFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jonathandavidblack on 6/27/16.
 */
public interface AnonFileRepository extends CrudRepository<AnonFile, Integer> {


    @Query("SELECT MIN(id) FROM AnonFile")
    int findMinId();

    @Query("SELECT MIN(id) FROM AnonFile WHERE isPerm = false")
    int findMinNonPermId();

}
