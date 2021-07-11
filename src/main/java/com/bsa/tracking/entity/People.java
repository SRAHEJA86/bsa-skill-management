package com.bsa.tracking.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PEOPLE")
public class People {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", insertable = false, updatable = false)
  @Range(min=0, max=2000,message = "Cannot add more than 2000 employees")
  private Integer id;

  private String name;

  @OneToMany(fetch = FetchType.LAZY)
  @NotFound(
      action = NotFoundAction.IGNORE)
  @JoinTable(name = "PEOPLE_SKILLS", joinColumns = @JoinColumn(name = "Id"),
      inverseJoinColumns = @JoinColumn(name = "skillId"))
  @Size(max = 10, message = " Cannot register more than 10 skills")
  private Set<Skill> skills;


  @Override
  public String toString() {
    return "People [id=" + id + ", name=" + name + ", skills=" + skills + "]";
  }
}
