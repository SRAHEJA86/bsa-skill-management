package com.bsa.tracking.repository;


import com.bsa.tracking.entity.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends CrudRepository<Skill,Integer> {
  public Skill findByName(String name);
  public void deleteByName(String name);

}
