package com.bsa.tracking.service;

import com.bsa.tracking.entity.People;
import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.repository.PeopleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PeopleService {

   PeopleRepository peopleRepository;

   @Autowired
   PeopleService(PeopleRepository peopleRepository) {
     this.peopleRepository = peopleRepository;
   }

  /**
   * Returns list of all people
   * @return list of people in th repos
   */
  public List<People> getAllPeople() {
    return (List<People>) peopleRepository.findAll();
  }

  /**
   * Updates people in the repository
   * @param updatedPeople people
   * @return the updated people
   */
  public People updatePeople(People updatedPeople) {
    return peopleRepository.save(updatedPeople);
  }

  /**
   * Adds people to the repository
   * @param newPeople newPeople
   * @return People added to the repository
   */
  public People addPeople(People newPeople) {
    return peopleRepository.save(newPeople) ;
  }

  /**
   * Deletes people from the repository
   * @param personId people to be deleted
   */
  public void deletePeople(Integer personId) {
    peopleRepository.deleteById(personId);
  }

  /**
   * Retrieves skills for people
   * @param personId personId
   * @return returns skills recorded for people
   */
  public Set<Skill> getSkillsForPeople(Integer personId) {
    Optional<People> optionalPeople = getPeopleById(personId);
    if (optionalPeople.isPresent())
      return optionalPeople.get().getSkills();
    else
      return Collections.emptySet();
  }

  /**
   * Returns skill for a person
   * @param personId personId
   * @param skillId skillId
   * @return skills
   */
  public Skill getDetailsForASkill(Integer personId, Integer skillId) {
    Optional<People> optionalPeople = getPeopleById(personId);

    //Picked first element since one skill Id will only have one skill associated
    return optionalPeople.map(people -> people.getSkills().stream().filter
        (skill -> skill.getSkillId().equals(skillId)).findFirst().get()).orElse(null);

  }

  /**
   *
   * @param personId person Id
   * @param newSkills new skills to be added
   * @return skills added
   */
  public Skill addSkillsForAPerson(Integer personId, Skill newSkills) {
    Optional<People> optionalPeople = peopleRepository.findById(personId);
    if(optionalPeople.isPresent()){
        People people = optionalPeople.get();
        people.getSkills().add(newSkills);
        peopleRepository.save(people);

        return newSkills;

    }
    return null;
  }

  /**
   * Return the people by Id
   * @param personId personId
   * @return people based on Id
   */
  public Optional<People> getPeopleById(Integer personId) {
    return peopleRepository.findById(personId);
  }

}
