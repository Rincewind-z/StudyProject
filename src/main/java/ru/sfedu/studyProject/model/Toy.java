package ru.sfedu.studyProject.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import ru.sfedu.studyProject.Converters.MaterialMapConverter;
import ru.sfedu.studyProject.enums.ToyStyle;
import ru.sfedu.studyProject.enums.ToyType;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Class Toy
 */
public class Toy extends Project {

  //
  // Fields
  //

  @CsvBindByName
  private long userId;
  @CsvBindByName
  private long id;
  @CsvBindByName
  private ToyStyle toyStyle;
  @CsvBindByName
  private ToyType toyType;
  @CsvBindByName
  @CsvDate("dd.MM.yyyy HH:mm:ss z")
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
  public Toy () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  public void setUserId (long newVar) {
    userId = newVar;
  }

  public long getUserId() {
    return userId;
  }

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

  //
  // Other methods
  //

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Toy toy = (Toy) o;
    return userId == toy.userId && id == toy.id && Float.compare(toy.progress, progress) == 0 && toyStyle == toy.toyStyle && toyType == toy.toyType && Objects.equals(dateOfCreation, toy.dateOfCreation) && Objects.equals(name, toy.name) && Objects.equals(outgoings, toy.outgoings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, id, toyStyle, toyType, dateOfCreation, name, outgoings, progress);
  }
}
