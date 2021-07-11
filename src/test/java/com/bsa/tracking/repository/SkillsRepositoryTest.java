package com.bsa.tracking.repository;

import static org.junit.Assert.assertEquals;

import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.enums.SkillLevel;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SkillsRepositoryTest {

    @Autowired
    SkillsRepository skillsRepository;

    @Test
    void getAllSkills() {
      Skill skills = Skill.builder().skillId(1).name("Java/J2EE").skillLevel(SkillLevel.EXPERT).build();
      skillsRepository.save(skills);

      List<Skill> savedSkills = (List<Skill>) skillsRepository.findAll();

      Assert.isTrue(savedSkills.size()  > 0,"Skill list is not zero");
      assertEquals("Java/J2EE",savedSkills.get(0).getName());
    }

    @Test
    void addSkill() {
      Skill skills = Skill.builder().skillId(1).name("Java/J2EE").skillLevel(SkillLevel.EXPERT).build();
      Skill savedSkill =skillsRepository.save(skills);
      Assert.notNull(savedSkill,"The saved skill is not null");
      assertEquals("Java/J2EE",savedSkill.getName());
    }

    @Test
    void updateSkill() {
      String skillName = "Java";
      Skill skills = Skill.builder().skillId(1).name(skillName).skillLevel(SkillLevel.EXPERT).build();
      skillsRepository.save(skills);

      Skill updatedSkills = skillsRepository.findByName(skillName);
      assertEquals(skillName, updatedSkills.getName());
    }

    @Test
    void deleteSkill() {
      String skillName = "Java/J2EE";
      Skill skills = Skill.builder().skillId(4).name(skillName).skillLevel(SkillLevel.EXPERT).build();
      skillsRepository.save(skills);

      Skill skillBeforeDelete = skillsRepository.findByName(skillName);
      skillsRepository.deleteByName(skillName);

      Skill skillAfterDelete = skillsRepository.findByName(skillName);
      Assert.notNull(skillBeforeDelete,"The skill exists before delete");
      Assert.isNull(skillAfterDelete,"The skill is null after delete");
    }
}
