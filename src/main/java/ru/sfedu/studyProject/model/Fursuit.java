package ru.sfedu.studyProject.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.studyProject.Converters.FursuitPartListConverter;
import ru.sfedu.studyProject.enums.FursuitStyle;
import ru.sfedu.studyProject.enums.FursuitType;

import java.util.List;
import java.util.Objects;

/**
 * Class Fursuit
 */
public class Fursuit extends Project {

  //
  // Fields
  //

  @CsvBindByName
  private FursuitType fursuitType;
  @CsvCustomBindByName(converter = FursuitPartListConverter.class)
  private List<FursuitPart> partList;
  @CsvBindByName
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
  public void setPartList (List<FursuitPart> newVar) {
    partList = newVar;
  }

  /**
   * Get the value of partList
   * @return the value of partList
   */
  public List<FursuitPart> getPartList () {
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

  @Override
  public String toString() {
    return "Fursuit{" +
            "fursuitType=" + fursuitType +
            ", partList=" + partList +
            ", fursuitStyle=" + fursuitStyle +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Fursuit fursuit = (Fursuit) o;
    return fursuitType == fursuit.fursuitType && Objects.equals(partList, fursuit.partList) && fursuitStyle == fursuit.fursuitStyle;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), fursuitType, partList, fursuitStyle);
  }
}
