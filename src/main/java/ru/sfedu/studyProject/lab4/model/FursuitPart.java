package ru.sfedu.studyProject.lab4.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Class FursuitPart
 */
@Entity
@Table(schema = "schema_lab4")
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
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(schema = "SCHEMA_LAB4")
  @MapKeyColumn
  private Map<Material, Double> outgoings;
  
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

  public void setOutgoings(Map<Material, Double> outgoings) {
    this.outgoings = outgoings;
  }

  public Map<Material, Double> getOutgoings() {
    return outgoings;
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
            ", outgoings=" + outgoings +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FursuitPart that = (FursuitPart) o;
    return userId == that.userId && id == that.id && Float.compare(that.progress, progress) == 0 && Objects.equals(dateOfCreation, that.dateOfCreation) && Objects.equals(name, that.name) && Objects.equals(outgoings, that.outgoings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, id, dateOfCreation, name, progress, outgoings);
  }
}
