package com.bsa.tracking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.enums.SkillLevel;
import com.bsa.tracking.repository.SkillsRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SkillsServiceTest {

  SkillsService skillsService;

  @Mock
  SkillsRepository skillsRepository;

  @BeforeEach
  void setUp(){
    MockitoAnnotations.openMocks(this);
    skillsService = new SkillsService(skillsRepository);
  }

  @Test
  void getAllSkills() {
    //given
    List<Skill> expectedSkills = Arrays.asList(
        Skill.builder().name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build(),
        Skill.builder().name("NODE").skillLevel(SkillLevel.WORKING).build());

    when(skillsRepository.findAll()).thenReturn(expectedSkills);

    //when
    List<Skill> actualSkills = skillsService.getAllSkills();

    //then
    assertEquals(expectedSkills,actualSkills);
    assertEquals(2,actualSkills.size());
    verify(skillsRepository, times(1))
        .findAll();

  }

  @Test
  void addSkill() {
    //given
     Skill expectedSkill = Skill.builder().name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build();
     when(skillsRepository.save(expectedSkill)).thenReturn(expectedSkill);

    //when
    Skill actualSkill = skillsService.addSkill(expectedSkill);

    //then
    assertEquals(expectedSkill,actualSkill);
    assertEquals(expectedSkill.getName(),actualSkill.getName());
    verify(skillsRepository, times(1))
        .save(expectedSkill);

  }

  @Test
  void updateSkill() {
    //given
    Skill expectedSkill = Skill.builder().name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build();
    when(skillsRepository.save(expectedSkill)).thenReturn(expectedSkill);

    //when
    Skill actualSkill = skillsService.updateSkill(expectedSkill);

    //then
    assertEquals(expectedSkill,actualSkill);
    assertEquals(expectedSkill.getName(),actualSkill.getName());
    verify(skillsRepository, times(1))
        .save(expectedSkill);

  }

  @Test
  void deleteSkill() {
    //when
    skillsService.deleteSkill(1);

    //then
    verify(skillsRepository, times(1))
        .deleteById(1);
  }

  @Test
  void getSkillBySkillId() {
    //given
    Skill expectedSkill = Skill.builder().skillId(1).name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build();
    when(skillsRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(expectedSkill));

    //when
    Optional<Skill> actualSkill = skillsService.getSkillBySkillId(1);

    //then
    assertEquals(expectedSkill.getName(),actualSkill.get().getName());
    verify(skillsRepository, times(1))
        .findById(1);
  }
}