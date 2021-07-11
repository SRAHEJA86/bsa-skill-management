package com.bsa.tracking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bsa.tracking.entity.People;
import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.enums.SkillLevel;
import com.bsa.tracking.repository.PeopleRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
class PeopleServiceTest {

  PeopleService peopleService;

  @Mock
  PeopleRepository peopleRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    peopleService = new PeopleService(peopleRepository);
  }

  @Test
  void testRetrievalOfListOfAllPeopleInAnOrganisation() {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    List<People> expectedPeopleList = setUpPeopleList(skills);

    when(peopleRepository.findAll()).thenReturn(expectedPeopleList);

    //when
    List<People> actualPeopleList = peopleService.getAllPeople();

    //then
    assertEquals(2,actualPeopleList.size());
    assertEquals(2,actualPeopleList.get(0).getSkills().size());
    assertEquals(expectedPeopleList,actualPeopleList);

  }



  @Test
  void updatePeople() {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    skills.add(Skill.builder().name("C++").skillLevel(SkillLevel.PRACTITIONER).build());

    People expectedPeople = People.builder().name("Sadhana Raheja").skills(skills).build();

    when(peopleRepository.save(expectedPeople)).thenReturn(expectedPeople);

    //when
    People actualPeople = peopleService.updatePeople(expectedPeople);

    //then
    assertEquals(3,actualPeople.getSkills().size());
    assertEquals(expectedPeople.getName(),actualPeople.getName());

  }

  @Test
  void testAddingPeopleAndTheirSkills() {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    skills.add(Skill.builder().name("C++").skillLevel(SkillLevel.PRACTITIONER).build());

    People expectedPeople = People.builder().name("Sandra").skills(skills).build();

    when(peopleRepository.save(expectedPeople)).thenReturn(expectedPeople);

    //when
    People actualPeople = peopleService.addPeople(expectedPeople);

    //then
    assertEquals(3,actualPeople.getSkills().size());
    assertEquals(expectedPeople.getName(),actualPeople.getName());
  }

  @Test
  void deletePeople() {
    //when
    peopleService.deletePeople(1);

    //then
    verify(peopleRepository, times(1))
        .deleteById(1);

  }

  @Test
  void getSkillsForPeople() {
    //given
    Set<Skill> expectedSkills = new HashSet<>();
    expectedSkills.add(Skill.builder().skillId(1).name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    expectedSkills.add(Skill.builder().skillId(2).name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    expectedSkills.add(Skill.builder().skillId(3).name("C++").skillLevel(SkillLevel.PRACTITIONER).build());
    expectedSkills.add(Skill.builder().skillId(4).name("Communication").skillLevel(SkillLevel.WORKING).build());

    People expectedPeople = People.builder().id(1).name("Sandra").skills(expectedSkills).build();

    when(peopleRepository.findById(1)).thenReturn(Optional.ofNullable(expectedPeople));

    //when
    Set<Skill> actualSkills = peopleService.getSkillsForPeople(1);

    //then
    assertEquals(4,actualSkills.size());
    assertEquals(expectedSkills.stream().findFirst().get().getName(),
        actualSkills.stream().findFirst().get().getName());
  }

  @Test
  void getDetailsForASkill() {
    //given
    Set<Skill> expectedSkills = new HashSet<>();
    expectedSkills.add(Skill.builder().skillId(1).name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    expectedSkills.add(Skill.builder().skillId(2).name("NODE").skillLevel(SkillLevel.AWARENESS).build());
    expectedSkills.add(Skill.builder().skillId(3).name("Communication").skillLevel(SkillLevel.WORKING).build());

    People expectedPeople = People.builder().id(1).name("Sandra").skills(expectedSkills).build();

    when(peopleRepository.findById(1)).thenReturn(Optional.ofNullable(expectedPeople));

    //when
    Skill actualSkill = peopleService.getDetailsForASkill(1,1);

    //then
    assertEquals("JAVA",actualSkill.getName());
    assertEquals("EXPERT",actualSkill.getSkillLevel().name());
  }

  @Test
  void addSkillsForAPerson() {
    //given
    Set<Skill> expectedSkills = new HashSet<>();
    Skill newSkill = Skill.builder().name("JAVA").skillLevel(SkillLevel.EXPERT).build();
    expectedSkills.add(Skill.builder().name("C++").skillLevel(SkillLevel.PRACTITIONER).build());
    expectedSkills.add(newSkill);

    People expectedPeople = People.builder().id(1).name("Sandra").skills(expectedSkills).build();
    when(peopleRepository.findById(1)).thenReturn(Optional.ofNullable(expectedPeople));
    assert expectedPeople != null;
    when(peopleRepository.save(expectedPeople)).thenReturn(expectedPeople);

    //when
    Skill actualSkills = peopleService.addSkillsForAPerson(1,newSkill);

    //then
    assertEquals(newSkill,actualSkills);
    assertEquals("JAVA",actualSkills.getName());


  }

  @Test
  void getPeopleById() {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("JAVA").skillLevel(SkillLevel.EXPERT).build());
    skills.add(Skill.builder().name("NODE").skillLevel(SkillLevel.AWARENESS).build());

    People expectedPeople = People.builder().id(1).name("Sandra").skills(skills).build();

    when(peopleRepository.findById(1)).thenReturn(Optional.of(expectedPeople));

    //when
    Optional<People> actualPeople = peopleService.getPeopleById(1);

    //then
    assertTrue(actualPeople.isPresent());
    assertEquals(expectedPeople, actualPeople.get());

  }

  private List<People> setUpPeopleList(Set<Skill> skills) {
    return Arrays.asList(
        People.builder().name("Sadhana Raheja").skills(skills).build(),
        People.builder().name("Sunil Raheja").skills(skills).build());
  }
}