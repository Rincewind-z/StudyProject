package ru.sfedu.studyProject.lab3.JoinedTable.model;

import ru.sfedu.studyProject.core.enums.FursuitStyle;
import ru.sfedu.studyProject.core.enums.FursuitType;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class Fursuit
 */
@Entity(name = "joined_table_fursuit")
@Table(schema = "joined_table")
public class Fursuit extends Project {

  //
  // Fields
  //

  private FursuitType fursuitType;
 // private List<FursuitPart> partList;
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

  /**
   * Set the value of partList
   * @param newVar the new value of partList
   */
 /* public void setPartList (List<FursuitPart> newVar) {
    partList = newVar;
  }*/

  /**
   * Get the value of partList
   * @return the value of partList
   */
 /* public List<FursuitPart> getPartList () {
    return partList;
  }*/

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

 /* @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Fursuit fursuit = (Fursuit) o;
    return fursuitType == fursuit.fursuitType && Objects.equals(partList, fursuit.partList) && fursuitStyle == fursuit.fursuitStyle;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), fursuitType, partList, fursuitStyle);
  }*/
}
