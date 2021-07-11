package com.bsa.tracking.service;

import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.repository.SkillsRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SkillsService {
  SkillsRepository skillsRepository;

  @Autowired
  public SkillsService(SkillsRepository skillsRepository){
        this.skillsRepository = skillsRepository;
  }


  /**
   * Returns list of all skills
   * @return list of skills
   */
  public List<Skill> getAllSkills() {
    return (List<Skill>) skillsRepository.findAll();
  }

  /**
   *Adds a new skill to the repository
   * @param newSkill new skill to be added
   * @return Skill that has been added
   */
  public Skill addSkill(Skill newSkill) {
    return skillsRepository.save(newSkill);
  }

  /**
   * Updates the skill in the repository
   * @param updatedSkill skill to be updated
   * @return Skill that has been updated
   */
  public Skill updateSkill(Skill updatedSkill) {
    return skillsRepository.save(updatedSkill);
  }

  /**
   *Deletes a skill from the repository
   * @param skillId skill to be deleted
   */
  public void deleteSkill(Integer skillId) {
    skillsRepository.deleteById(skillId);
  }

  /**
   * Returns a Skill based on skill id
   * @param skillId skillId to be found
   * @return Skill
   */
  public Optional<Skill> getSkillBySkillId(Integer skillId) {
    return skillsRepository.findById(skillId);
  }
}
