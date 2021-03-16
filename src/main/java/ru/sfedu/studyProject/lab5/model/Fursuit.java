package ru.sfedu.studyProject.lab5.model;

import ru.sfedu.studyProject.core.enums.FursuitStyle;
import ru.sfedu.studyProject.core.enums.FursuitType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Class Fursuit
 */
@Entity(name = "lab5_fursuit")
@Table(schema = "schema_lab5")
public class Fursuit extends Project {

  //
  // Fields
  //

  private FursuitType fursuitType;
  @OneToMany(mappedBy = "fursuit",
  fetch = FetchType.EAGER,
  cascade = CascadeType.PERSIST)
  private Set<FursuitPart> partList = new HashSet<>();
  private FursuitStyle fursuitStyle;
  
  //
  // Constructors
  //
  public Fursuit () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of fursuitType
   * @param newVar the new value of fursuitType
   */
  public void setFursuitType (FursuitType newVar) {
    fursuitType = newVar;
  }

  /**
   * Get the value of fursuitType
   * @return the value of fursuitType
   */
  public FursuitType getFursuitType () {
    return fursuitType;
  }

    public void setPartList(Set<FursuitPart> partList) {
        this.partList = partList;
    }

    public Set<FursuitPart> getPartList() {
        return partList;
    }

    /**
   * Set the value of fursuitStyle
   * @param newVar the new value of fursuitStyle
   */
  public void setFursuitStyle (FursuitStyle newVar) {
    fursuitStyle = newVar;
  }

  /**
   * Get the value of fursuitStyle
   * @return the value of fursuitStyle
   */
  public FursuitStyle getFursuitStyle () {
    return fursuitStyle;
  }

  //
  // Other methods
  //

  @Override
  public String toString() {
    return "Fursuit{" +
            "fursuitType=" + fursuitType +
    //        ", partList=" + partList +
            ", fursuitStyle=" + fursuitStyle +
            super.toString() +
            '}';
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Fursuit fursuit = (Fursuit) o;
        return fursuitType == fursuit.fursuitType && /*Objects.equals(partList, fursuit.partList) &&*/ fursuitStyle == fursuit.fursuitStyle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fursuitType, fursuitStyle);
    }
}
