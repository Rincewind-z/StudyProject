package ru.sfedu.studyProject.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import ru.sfedu.studyProject.Constants;
import ru.sfedu.studyProject.Converters.MaterialMapConverter;

import java.util.Date;
import java.util.Map;

/**
 * Class FursuitPart
 */
public class FursuitPart {

  //
  // Fields
  //

  @CsvBindByName
  private long userId;
  @CsvBindByName
  private long id;
  @CsvBindByName
  @CsvDate(value = Constants.DATE_FORMAT)
  private Date dateOfCreation;
  @CsvBindByName
  private String name;
  @CsvCustomBindByName(converter = MaterialMapConverter.class)
  private Map<Material, Double> outgoings;
  @CsvBindByName
  private float progress;
  
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
   * Set the value of outgoings
   * @param newVar the new value of outgoings
   */
  public void setOutgoings (Map<Material, Double> newVar) {
    outgoings = newVar;
  }

  /**
   * Get the value of outgoings
   * @return the value of outgoings
   */
  public Map<Material, Double> getOutgoings () {
    return outgoings;
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
            ", outgoings=" + outgoings +
            ", progress=" + progress +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FursuitPart that = (FursuitPart) o;

    if (userId != that.userId) return false;
    if (id != that.id) return false;
    if (Float.compare(that.progress, progress) != 0) return false;
    if (!dateOfCreation.equals(that.dateOfCreation)) return false;
    return name.equals(that.name);
  }

}
