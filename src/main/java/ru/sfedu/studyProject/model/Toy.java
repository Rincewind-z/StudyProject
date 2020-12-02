package ru.sfedu.studyProject.model;

import ru.sfedu.studyProject.enums.ToyStyle;
import ru.sfedu.studyProject.enums.ToyType;

import java.util.Map;

/**
 * Class Toy
 */
public class Toy extends Project {

  //
  // Fields
  //

  private ToyStyle toyStyle;
  private ToyType toyType;
  private Map<Material, Long> outgoings;
  
  //
  // Constructors
  //
  public Toy () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of toyStyle
   * @param newVar the new value of toyStyle
   */
  public void setToyStyle (ToyStyle newVar) {
    toyStyle = newVar;
  }

  /**
   * Get the value of toyStyle
   * @return the value of toyStyle
   */
  public ToyStyle getToyStyle () {
    return toyStyle;
  }

  /**
   * Set the value of toyType
   * @param newVar the new value of toyType
   */
  public void setToyType (ToyType newVar) {
    toyType = newVar;
  }

  /**
   * Get the value of toyType
   * @return the value of toyType
   */
  public ToyType getToyType () {
    return toyType;
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

  //
  // Other methods
  //

}
