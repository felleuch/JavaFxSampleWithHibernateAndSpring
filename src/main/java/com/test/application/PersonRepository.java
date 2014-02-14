package com.test.application;

import com.test.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by faiez.elleuch on 02/01/14.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByLastName(String lastName);
}