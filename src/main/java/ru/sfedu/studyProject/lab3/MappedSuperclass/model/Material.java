package ru.sfedu.studyProject.lab3.MappedSuperclass.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Attribute;
import ru.sfedu.studyProject.core.Constants;
import ru.sfedu.studyProject.core.enums.MaterialType;
import ru.sfedu.studyProject.core.enums.Unit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * Class Material
 */
@Entity(name = "mapped_superclass_material")
@Table(schema = "mapped_superclass")
public class Material {

  //
  // Fields
  //
  private long userId;
  @Id
  @GeneratedValue
  private long id;
  private Date dateOfCreation;
  private String materialName;
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
   * Set the value of dateOfCreation
   * @param newVar the new value of dateOfCreation
   */
  public void setDateOfCreation (Date newVar) {
    dateOfCreation = newVar;
  }

  /**
   * Get the value of dateOfCreation
   * @return the value of dateOfCreation
   */
  public Date getDateOfCreation () {
    return dateOfCreation;
  }

  /**
   * Set the value of name
   * @param newVar the new value of name
   */
  public void setName (String newVar) {
    materialName = newVar;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  public String getName () {
    return materialName;
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

  public void setUserId (long newVar) {
    userId = newVar;
  }

  public long getUserId() {
    return userId;
  }
//
  // Other methods
  //


  @Override
  public String toString() {
    return "Material{" +
            "userId=" + userId +
            ", id=" + id +
            ", dateOfCreation=" + dateOfCreation +
            ", materialName='" + materialName + '\'' +
            ", materialType=" + materialType +
            ", cost=" + cost +
            ", description='" + description + '\'' +
            ", unit=" + unit +
            ", inStock=" + inStock +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Material material = (Material) o;
    return userId == material.userId && id == material.id && Float.compare(material.inStock, inStock) == 0 && dateOfCreation.equals(material.dateOfCreation) && materialName.equals(material.materialName) && materialType == material.materialType && cost.equals(material.cost) && description.equals(material.description) && unit == material.unit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, id, dateOfCreation, materialName, materialType, cost, description, unit, inStock);
  }
}
