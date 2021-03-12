package ru.sfedu.studyProject.lab4.model;

import ru.sfedu.studyProject.core.enums.FursuitStyle;
import ru.sfedu.studyProject.core.enums.FursuitType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Fursuit
 */
@Entity
@Table(schema = "schema_lab4")
public class Fursuit extends Project {

  //
  // Fields
  //

  private FursuitType fursuitType;
  @ElementCollection(fetch = FetchType.EAGER)
  @OrderColumn
  @CollectionTable(schema = "schema_lab4")
  private List<String> partList;
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

    public void setPartList(List<String> partList) {
        this.partList = partList;
    }

    public List<String> getPartList() {
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
            ", partList=" + partList +
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
    return fursuitType == fursuit.fursuitType && Objects.equals(partList, fursuit.partList) && fursuitStyle == fursuit.fursuitStyle;
  }

  @Override
    public int hashCode() {
        return Objects.hash(fursuitType, partList, fursuitStyle);
    }
}
