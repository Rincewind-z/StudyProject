package ru.sfedu.studyProject.lab3.MappedSuperclass.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Attribute;
import ru.sfedu.studyProject.core.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * Class FursuitPart
 */
@Entity(name = "mapped_superclass_fursuit_part")
@Table(schema = "mapped_superclass")
public class FursuitPart {

  //
  // Fields
  //
  private long userId;
  @Id
  @GeneratedValue
  private long id;
  private Date dateOfCreation;
  private String name;
  private float progress;
  private double cost;
  
  //
  // Constructors
  //
  public FursuitPart () { };
  
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
   * Set the value of progress
   * @param newVar the new value of progress
   */
  public void setProgress (float newVar) {
    progress = newVar;
  }

  /**
   * Get the value of progress
   * @return the value of progress
   */
  public float getProgress () {
    return progress;
  }

  public void setUserId (long newVar) {
    userId = newVar;
  }

  public long getUserId() {
    return userId;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  //
  // Other methods
  //


  @Override
  public String toString() {
    return "FursuitPart{" +
            "userId=" + userId +
            ", id=" + id +
            ", dateOfCreation=" + dateOfCreation +
            ", name='" + name + '\'' +
            ", progress=" + progress +
            ", cost=" + cost +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FursuitPart that = (FursuitPart) o;
    return userId == that.userId
            && id == that.id && Float.compare(that.progress, progress) == 0
            && Double.compare(that.cost, cost) == 0
            && Objects.equals(dateOfCreation, that.dateOfCreation)
            && Objects.equals(name, that.name);
  }
}
