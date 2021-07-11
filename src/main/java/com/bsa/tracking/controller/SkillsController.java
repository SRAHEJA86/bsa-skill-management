package com.bsa.tracking.controller;

import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.service.SkillsService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/skills")
public class SkillsController {

  @Autowired
  SkillsService skillsService;

  /**
   *Gets the list of all skills
   * @return returns list of skills
   */
  @GetMapping
  public List<Skill> getAllSkills(HttpServletResponse response) throws IOException {
    List<Skill> skillList = skillsService.getAllSkills();

    if(skillList == null || skillList.isEmpty()) {
      log.error("skills not found");
      response.sendError(404);
      return Collections.emptyList();

    }
    return skillList;
  }

  /**
   * Gets a skill based on skill id
   * @param skillId skillId
   * @return skills
   */
  @GetMapping("/{skillId}")
  public Skill getSkillBySkillId(@PathVariable("skillId") Integer skillId, HttpServletResponse response)
      throws IOException {
    Optional<Skill> optionalSkill = skillsService.getSkillBySkillId(skillId);
    if(optionalSkill.isPresent()) {
      return optionalSkill.get();
    }
      log.error("skill not found");
      response.sendError(404);
      return null;
  }

  /**
   *Creates a skill in the repository
   * @param newSkill new skill to be added
   * @return skills added to the repo
   */
  @PostMapping("/{skillId}")
  public Skill addSkill(@Valid @RequestBody Skill newSkill){
    return skillsService.addSkill(newSkill);
  }

  /**
   *Updates a skill
   * @param updatedSkill skill to be updated
   * @return skills that has been updated
   */
  @PutMapping("/{skillId}")
  public Skill updateSkill(@Valid @RequestBody Skill updatedSkill){
    return skillsService.updateSkill(updatedSkill);
  }

  /**
   * Deletes a skill from the repository
   * @param skillId skill Id to be deleted
   */
  @DeleteMapping("/{skillId}")
  public void deleteSkill(@PathVariable Integer skillId){
    skillsService.deleteSkill(skillId);
  }

}
