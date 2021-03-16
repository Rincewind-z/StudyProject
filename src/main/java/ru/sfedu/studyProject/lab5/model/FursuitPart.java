package ru.sfedu.studyProject.lab5.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Class FursuitPart
 */
@Entity(name = "lab5_fursuit_part")
@Table(schema = "schema_lab5")
public class FursuitPart {

  //
  // Fields
  //
  private long userId;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @ManyToOne(fetch = FetchType.EAGER)
  private Fursuit fursuit;
  private Date dateOfCreation;
  @OneToOne(fetch = FetchType.EAGER,
  cascade = CascadeType.PERSIST)
  @JoinColumn(unique = true)
  private FurPartDetails furPartDetails;
  
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

  public FurPartDetails getFurPartDetails() {
    return furPartDetails;
  }

  public void setFurPartDetails(FurPartDetails furPartDetails) {
    this.furPartDetails = furPartDetails;
  }

  public void setUserId (long newVar) {
    userId = newVar;
  }

  public long getUserId() {
    return userId;
  }

  public void setFursuit(Fursuit fursuit) {
    this.fursuit = fursuit;
  }

  public Fursuit getFursuit() {
    return fursuit;
  }
//
  // Other methods
  //
  @Override
  public String toString() {
    return "FursuitPart{" +
            "userId=" + userId +
            ", id=" + id +
            ", fursuit=" + fursuit +
            ", dateOfCreation=" + dateOfCreation +
            ", furPartDetails=" + furPartDetails +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FursuitPart that = (FursuitPart) o;
    return userId == that.userId && id == that.id && /*Objects.equals(fursuit, that.fursuit) && */Objects.equals(dateOfCreation, that.dateOfCreation) /*&& Objects.equals(furPartDetails, that.furPartDetails)*/;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, id, /*fursuit,*/ dateOfCreation, furPartDetails);
  }
}
