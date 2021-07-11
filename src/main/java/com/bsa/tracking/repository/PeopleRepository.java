package com.bsa.tracking.repository;

import com.bsa.tracking.entity.People;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends CrudRepository<People,Integer> {

  public People findByName(String name);
}
