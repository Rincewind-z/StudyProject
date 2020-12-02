package ru.sfedu.studyProject.model;

import ru.sfedu.studyProject.enums.MaterialType;
import ru.sfedu.studyProject.enums.Unit;

import java.util.Date;

/**
 * Class Material
 */
public class Material {

  //
  // Fields
  //

  private long id;
  private Date dateOfCreatoion;
  private String name;
  private MaterialType materialType;
  private Float cost;
  private String description;
  private Unit unit;
  private float inStock;
  
  //
  // Constructors
  //
  public Material () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  public void setId (long newVar) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public long getId () {
    return id;
  }

  /**
   * Set the value of dateOfCreatoion
   * @param newVar the new value of dateOfCreatoion
   */
  public void setDateOfCreatoion (Date newVar) {
    dateOfCreatoion = newVar;
  }

  /**
   * Get the value of dateOfCreatoion
   * @return the value of dateOfCreatoion
   */
  public Date getDateOfCreatoion () {
    return dateOfCreatoion;
  }

  /**
   * Set the value of name
   * @param newVar the new value of name
   */
  public void setName (String newVar) {
    name = newVar;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  public String getName () {
    return name;
  }

  /**
   * Set the value of materialType
   * @param newVar the new value of materialType
   */
  public void setMaterialType (MaterialType newVar) {
    materialType = newVar;
  }

  /**
   * Get the value of materialType
   * @return the value of materialType
   */
  public MaterialType getMaterialType () {
    return materialType;
  }

  /**
   * Set the value of cost
   * @param newVar the new value of cost
   */
  public void setCost (Float newVar) {
    cost = newVar;
  }

  /**
   * Get the value of cost
   * @return the value of cost
   */
  public Float getCost () {
    return cost;
  }

  /**
   * Set the value of description
   * @param newVar the new value of description
   */
  public void setDescription (String newVar) {
    description = newVar;
  }

  /**
   * Get the value of description
   * @return the value of description
   */
  public String getDescription () {
    return description;
  }

  /**
   * Set the value of unit
   * @param newVar the new value of unit
   */
  public void setUnit (Unit newVar) {
    unit = newVar;
  }

  /**
   * Get the value of unit
   * @return the value of unit
   */
  public Unit getUnit () {
    return unit;
  }

  /**
   * Set the value of inStock
   * @param newVar the new value of inStock
   */
  public void setInStock (float newVar) {
    inStock = newVar;
  }

  /**
   * Get the value of inStock
   * @return the value of inStock
   */
  public float getInStock () {
    return inStock;
  }

  //
  // Other methods
  //

}
