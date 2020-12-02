package ru.sfedu.studyProject.model;

import ru.sfedu.studyProject.enums.FursuitStyle;
import ru.sfedu.studyProject.enums.FursuitType;

/**
 * Class Fursuit
 */
public class Fursuit extends Project {

  //
  // Fields
  //

  private FursuitType fursuitType;
  private FursuitPart partList;
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
  public void setPartList (FursuitPart newVar) {
    partList = newVar;
  }

  /**
   * Get the value of partList
   * @return the value of partList
   */
  public FursuitPart getPartList () {
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

}
