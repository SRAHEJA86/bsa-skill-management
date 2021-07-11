package com.bsa.tracking.controller;

import com.bsa.tracking.entity.People;
import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.service.PeopleService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/people")
public class PeopleController {

  @Autowired
  PeopleService peopleService;

 /**
   *Returns list of people
   * @return List of People
   */
  @GetMapping
  public List<People> getAllPeople(HttpServletResponse response) throws IOException {
    List<People> people = peopleService.getAllPeople();
    if(people.isEmpty()) {
      log.error("people not found");
      response.sendError(404);
    }
    return people;
  }

  /**
   * Returns People based on personId
   * @param personId person Id
   * @return People
   */
  @GetMapping("/{personId}")
  public People getPeopleById(@PathVariable Integer personId,HttpServletResponse response)
      throws IOException {
    Optional<People> optionalPeople = peopleService.getPeopleById(personId);
    if(optionalPeople.isPresent())
      return optionalPeople.get();

      log.error("people not found");
      response.sendError(404);
      return null;

  }

  /**
   *Returns list of skills associated with a person
   * @param personId person Id
   * @return list of skills
   */
  @GetMapping("/{personId}/skills")
  public Set<Skill> getSkillsForPeople(@PathVariable Integer personId,HttpServletResponse response)
      throws IOException {
    Set<Skill> skill = peopleService.getSkillsForPeople(personId);

    if(null == skill || skill.isEmpty()) {
      log.error("skills not found");
      response.sendError(404);
    }
   return skill ;
  }

  /**
   *Return Skill
   * @param personId person Id
   * @param skillId skill Id
   * @return returns a specific associated with a person
   */
  @GetMapping("/{personId}/skills/{skillId}")
  public Skill getDetailsForSkills(@PathVariable Integer personId,
      @PathVariable Integer skillId) {
    return peopleService.getDetailsForASkill(personId, skillId);
  }


  /**
   *Updates people in the repository
   * @param updatedPeople people to be updated
   * @return Updated people
   */
  @PutMapping("/{personId}")
  public People updatePeople(@Valid @RequestBody People updatedPeople){
    return peopleService.updatePeople(updatedPeople);
  }

  /**
   *Adds people to the repository
   * @param newPeople new people to be added
   * @return People added to the repository
   */
  @PostMapping
  public People addPeople(@Valid @RequestBody People newPeople){
    return peopleService.addPeople(newPeople);
  }

  /**
   *Deletes people from the repository
   * @param personId people to be deleted
   */
  @DeleteMapping("/{personId}")
  public void deletePeople(@PathVariable Integer personId){
    peopleService.deletePeople(personId);
  }


  /**
   *
   * @param personId person Id
   * @param newSkill new skills to be added
   * @return ResponseEntity
   */

  @PostMapping("/{personId}")
  public Skill registerSkillsForPeople(
      @PathVariable Integer personId, @Valid @RequestBody Skill newSkill,HttpServletResponse response)
      throws IOException {

    Skill skill = peopleService.addSkillsForAPerson(personId,newSkill);

    if (skill == null) {
      log.error("skills not found");
      response.sendError(404);
      return null;
    }

    return skill;
  }

}
