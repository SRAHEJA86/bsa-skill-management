package com.bsa.tracking.entity;

import com.bsa.tracking.enums.SkillLevel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SKILL")
public class Skill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "skillId", insertable = false, updatable = false)
  @Range(min=0, max=100, message = "Cannot register more than 100 skills")
  private Integer skillId;

  private String name;

  @Enumerated(EnumType.STRING)
  private SkillLevel skillLevel;

}
