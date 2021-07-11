package com.bsa.tracking.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bsa.tracking.entity.People;
import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.enums.SkillLevel;
import com.bsa.tracking.service.PeopleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = PeopleController.class)
@WebAppConfiguration
public class PeopleControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  PeopleService peopleService;

  private final String url = "/people";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getAllPeople() throws Exception {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("Java").skillLevel(SkillLevel.WORKING).build());
    skills.add(Skill.builder().name("C++").skillLevel(SkillLevel.AWARENESS).build());

    List<People> mockPeopleList = new ArrayList<>();
    mockPeopleList.add(People.builder().id(1).name("Sandra").skills(skills).build());
    mockPeopleList.add(People.builder().id(2).name("Jessica").skills(skills).build());

    when(peopleService.getAllPeople()).thenReturn(mockPeopleList);

    String expectedResponse = objectMapper.writeValueAsString(mockPeopleList);

    //when
    MvcResult result = mockMvc.perform(get(url))

        //then
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedResponse))
        .andDo(print())
        .andReturn();

    Assertions.assertEquals(expectedResponse, result.getResponse().getContentAsString());
  }

  @Test
  public void shouldReturn404WhenPeopleNotFound() throws Exception {


    when(peopleService.getAllPeople()).thenReturn(Collections.EMPTY_LIST);

    //when
    mockMvc.perform(get(url))
        //then
        .andExpect(status().isNotFound());
  }

  @Test
 public void updatePeople() throws Exception {

    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().skillId(1).name("Java").skillLevel(SkillLevel.WORKING).build());
    skills.add(Skill.builder().skillId(2).name("C++").skillLevel(SkillLevel.AWARENESS).build());
    People expectedPeople = People.builder().id(123).name("Sadhana").skills(skills).build();

    when(peopleService.updatePeople(expectedPeople)).thenReturn(expectedPeople);

    String expected = objectMapper.writeValueAsString(expectedPeople);

    //when
    MvcResult result = mockMvc.perform(put(url+"/123").with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(expected))
        .andDo(print())
        .andReturn();


    //then
    Assertions.assertEquals(expected, result.getResponse().getContentAsString());

  }

  @Test
  public void addPeople() throws Exception {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("Java").skillLevel(SkillLevel.WORKING).build());
    skills.add(Skill.builder().name("C++").skillLevel(SkillLevel.AWARENESS).build());

    People expectedPeople = People.builder().id(123).skills(skills).build();

    when(peopleService.addPeople(expectedPeople)).thenReturn(expectedPeople);
    String expected = objectMapper.writeValueAsString(expectedPeople);

    //when
    mockMvc.perform(post(url).with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(expected))
        .andExpect(status().isOk())
        .andExpect(content().string(is(equalTo(expected))))
        .andDo(print());

  }

  @Test
  public void deletePeople() throws Exception {
   //when
    mockMvc.perform(delete(url+"/123").with(csrf()))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    //then
    verify(peopleService).deletePeople(any(Integer.class));

  }

  @Test
  public void getPeopleById() throws Exception {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("Java").skillLevel(SkillLevel.WORKING).build());
    skills.add(Skill.builder().name("C++").skillLevel(SkillLevel.AWARENESS).build());

    People expectedPeople = People.builder().id(123).name("Sadhana").skills(skills).build();

    when(peopleService.getPeopleById(123)).thenReturn(Optional.ofNullable(expectedPeople));

    String expected = objectMapper.writeValueAsString(expectedPeople);

    //when
    MvcResult result = mockMvc.perform(get(url+"/123"))

        //then
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expected))
        .andDo(print())
        .andReturn();
    Assertions.assertEquals(expected, result.getResponse().getContentAsString());

  }

  @Test
  public void getSkillsForPeople() throws Exception {
    //given
    Set<Skill> skills = new HashSet<>();
    skills.add(Skill.builder().name("Java").skillLevel(SkillLevel.WORKING).build());
    skills.add(Skill.builder().name("C++").skillLevel(SkillLevel.AWARENESS).build());

    when(peopleService.getSkillsForPeople(123)).thenReturn(skills);

    String expected = objectMapper.writeValueAsString(skills);

    //when
    MvcResult result = mockMvc.perform(get(url+"/123/skills"))

        //then
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expected))
        .andDo(print())
        .andReturn();
    Assertions.assertEquals(expected, result.getResponse().getContentAsString());

  }

  @Test
  public void getDetailsForSkills() throws Exception {
    //given
    Set<Skill> skills = new HashSet<>();
    Skill skillOne = Skill.builder().skillId(1).name("Java").skillLevel(SkillLevel.WORKING).build();
    Skill secondSkill = Skill.builder().skillId(2).name("C++").skillLevel(SkillLevel.AWARENESS).build();

    skills.add(skillOne);
    skills.add(secondSkill);

    People expectedPeople = People.builder().id(123).skills(skills).build();
    when(peopleService.getDetailsForASkill(expectedPeople.getId(),1)).thenReturn(skillOne);

    String expected = objectMapper.writeValueAsString(skillOne);

    //when
    MvcResult result = mockMvc.perform(get(url+"/123/skills/1"))

        //then
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expected))
        .andDo(print())
        .andReturn();
    Assertions.assertEquals(expected, result.getResponse().getContentAsString());
  }

  @Test
  public void should_Return404_When_PeopleNotFound() throws Exception {

    when(peopleService.getPeopleById(678)).thenReturn(Optional.empty());

    //when
    mockMvc.perform(get(url+"/"+678)
        //then
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void should_Return404_When_SkillsNotFound() throws Exception {

    when(peopleService.getSkillsForPeople(678)).thenReturn(Collections.emptySet());

    //when
    mockMvc.perform(get("/people/678/skills")
        .accept(MediaType.APPLICATION_JSON))

        //then
        .andExpect(status().isNotFound());
  }

  @Test
  public void registerSkillsForPeople() throws Exception {
    //given
    Skill newSkill = Skill.builder().name("Java").skillLevel(SkillLevel.WORKING).build();

    when(peopleService.addSkillsForAPerson(123,newSkill)).thenReturn(newSkill);
    String expected = objectMapper.writeValueAsString(newSkill);

    //when
    mockMvc.perform(post(url+"/"+123).with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(expected))
        .andExpect(status().isOk())
        .andExpect(content().string(is(equalTo(expected))))
        .andDo(print());

  }

  @Test
  public void shouldReturn404WhenPeopleNotFoundWhileRegisteringANewSkill() throws Exception {

    //given
    Skill newSkill = Skill.builder().name("Java").skillLevel(SkillLevel.WORKING).build();
    when(peopleService.addSkillsForAPerson(123,newSkill)).thenReturn(null);
    String expected = objectMapper.writeValueAsString(newSkill);

    //when
    mockMvc.perform(post(url+"/"+123).with(csrf())
        .contentType(MediaType.APPLICATION_JSON)

        //then
        .content(expected))
        .andExpect(status().isNotFound());

  }

}