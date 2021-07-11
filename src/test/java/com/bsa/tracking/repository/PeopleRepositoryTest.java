package com.bsa.tracking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bsa.tracking.entity.People;
import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.enums.SkillLevel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PeopleRepositoryTest {

  @Autowired
  PeopleRepository peopleRepository;

  @Autowired
  SkillsRepository skillsRepository;

  @Test
  void getAllPeople() {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().skillId(1).name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().skillId(2).name("NODE").skillLevel(SkillLevel.AWARENESS).build());

    People people = People.builder().id(123).name("Sadhana Raheja").skills(skills).build();
    skills.forEach(skill -> skillsRepository.save(skill));
    peopleRepository.save(people);

    //when
    List<People> actualPeople = (List<People>) peopleRepository.findAll();

    //then
    assertTrue(actualPeople.size() > 0);
    assertEquals("Sadhana Raheja",actualPeople.get(0).getName());
    assertEquals(2, actualPeople.get(0).getSkills().size());

  }

  @Test
  void addPeople() {

    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().skillId(1).name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().skillId(2).name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    People people = People.builder().id(123).name("Sadhana Raheja").skills(skills).build();
    skills.forEach(skill -> skillsRepository.save(skill));

    //when
    People actualPeople = peopleRepository.save(people);

    //then
    assertEquals(2,actualPeople.getSkills().size());
    assertEquals("Sadhana Raheja",actualPeople.getName());
  }

  @Test
  void updatePeople() {

    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().skillId(1).name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().skillId(2).name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    People people = People.builder().id(123).name("Sadhana Raheja").skills(skills).build();
    skills.forEach(skill -> skillsRepository.save(skill));

    //when
    People savedPeople = peopleRepository.save(people);
    Skill newSkill = Skill.builder().skillLevel(SkillLevel.PRACTITIONER).name("C Sharp").build();
    savedPeople.getSkills().add(newSkill);
    skillsRepository.save(newSkill);
    People updatedPeople = peopleRepository.save(savedPeople);

    //then
    assertEquals(3,updatedPeople.getSkills().size());

  }

  @Test
  void deletePeople() {

    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().skillId(1).name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().skillId(2).name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    skills.forEach(skill -> skillsRepository.save(skill));

    List<People> peopleList = new ArrayList<>();
    peopleList.add(People.builder().id(10).name("Sadhana Raheja").skills(skills).build());
    peopleList.add(People.builder().id(20).name("Sadhana Raheja").skills(skills).build());

    skills.forEach(skill -> skillsRepository.save(skill));
    peopleList.forEach(people -> peopleRepository.save(people));

    //when
    peopleRepository.deleteById(1);

    //then
    assertFalse(peopleRepository.existsById(1));

  }

}
