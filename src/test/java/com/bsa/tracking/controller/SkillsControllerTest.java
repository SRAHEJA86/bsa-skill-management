package com.bsa.tracking.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bsa.tracking.entity.Skill;
import com.bsa.tracking.enums.SkillLevel;
import com.bsa.tracking.service.SkillsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = SkillsController.class)
@WebAppConfiguration
public class SkillsControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  SkillsService skillsService;

  @Autowired
  private ObjectMapper objectMapper;

  private final String url = "/skills";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getAllSkills() throws Exception {

    //given
    List<Skill> expectedSkills = Arrays.asList(
        Skill.builder().skillId(1).name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build(),
        Skill.builder().skillId(2).name("NODE").skillLevel(SkillLevel.WORKING).build());

    Mockito.when(skillsService.getAllSkills()).thenReturn(expectedSkills);

    //when
    mockMvc.perform(get(url))

        //then
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

  }

  @Test
  public void getSkillBySkillId() throws Exception {

    //given
    Skill expectedSkills = Skill.builder().skillId(1).name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build();

    Mockito.when(skillsService.getSkillBySkillId(1)).thenReturn(
        java.util.Optional.ofNullable(expectedSkills));
    String expectedResponse = objectMapper.writeValueAsString(expectedSkills);

    //when
    mockMvc.perform(get(url+"/1"))
        //then
        .andExpect(status().isOk())
        .andExpect(content().string(is(equalTo(expectedResponse))))
        .andDo(print())
        .andReturn();

  }

  @Test
  public void addSkill() throws Exception {
    //given
    Skill expectedSkills = Skill.builder().skillId(1).name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build();

    Mockito.when(skillsService.addSkill(expectedSkills)).thenReturn(expectedSkills);
    String expectedResponse = objectMapper.writeValueAsString(expectedSkills);

    //when
    mockMvc.perform(post(url+"/1").with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(expectedResponse))
        //then
        .andExpect(status().isOk())
        .andExpect(content().string(is(equalTo(expectedResponse))))
        .andDo(print())
        .andReturn();
  }

  @Test
  public void updateSkill() throws Exception {
    //given
    Skill expectedSkills = Skill.builder().skillId(1).name("JAVA/J2EE").skillLevel(SkillLevel.EXPERT).build();

    Mockito.when(skillsService.updateSkill(expectedSkills)).thenReturn(expectedSkills);
    String expectedResponse = objectMapper.writeValueAsString(expectedSkills);

    //when
    mockMvc.perform(put(url+"/1").with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(expectedResponse))
        //then
        .andExpect(status().isOk())
        .andExpect(content().string(is(equalTo(expectedResponse))))
        .andDo(print())
        .andReturn();
  }

  @Test
  public void deleteSkill() throws Exception {
    //when
    mockMvc.perform(delete(url+"/1").with(csrf()))
        //then
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();
  }

  @Test
  public void shouldReturn404WhenSkillsNotFound() throws Exception{

    Mockito.when(skillsService.getAllSkills()).thenReturn(Collections.EMPTY_LIST);

    //when
    mockMvc.perform(get(url))
        //then
        .andExpect(status().isNotFound())
        .andDo(print())
        .andReturn();

  }

  @Test
  public void shouldReturn404WhenASkillNotFound() throws Exception {

    Mockito.when(skillsService.getSkillBySkillId(1)).thenReturn(Optional.empty());

    //when
    mockMvc.perform(get(url+"/"+1))
        //then
        .andExpect(status().isNotFound())
        .andDo(print())
        .andReturn();
  }
}