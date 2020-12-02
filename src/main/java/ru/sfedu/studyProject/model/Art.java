package ru.sfedu.studyProject.model;

import ru.sfedu.studyProject.enums.ArtStyle;
import ru.sfedu.studyProject.enums.ArtType;

import java.util.Map;

/**
 * Class Art
 */
public class Art extends Project {

  //
  // Fields
  //

  private ArtType artType;
  private Map<Material, Long> outgoings;
  private ArtStyle artStyle;
  
  //
  // Constructors
  //
  public Art () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of artType
   * @param newVar the new value of artType
   */
  public void setArtType (ArtType newVar) {
    artType = newVar;
  }

  /**
   * Get the value of artType
   * @return the value of artType
   */
  public ArtType getArtType () {
    return artType;
  }

  /**
   * Set the value of outgoings
   * @param newVar the new value of outgoings
   */
  public void setOutgoings (Map<Material, Long> newVar) {
    outgoings = newVar;
  }

  /**
   * Get the value of outgoings
   * @return the value of outgoings
   */
  public Map<Material, Long> getOutgoings () {
    return outgoings;
  }

  /**
   * Set the value of artStyle
   * @param newVar the new value of artStyle
   */
  public void setArtStyle (ArtStyle newVar) {
    artStyle = newVar;
  }

  /**
   * Get the value of artStyle
   * @return the value of artStyle
   */
  public ArtStyle getArtStyle () {
    return artStyle;
  }

  //
  // Other methods
  //

}
