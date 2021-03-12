package ru.sfedu.studyProject.lab3.MappedSuperclass.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import ru.sfedu.studyProject.core.enums.ArtStyle;
import ru.sfedu.studyProject.core.enums.ArtType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Class Art
 */
@Entity(name = "mapped_superclass_art")
@Table(schema = "mapped_superclass")
public class Art extends Project {

  //
  // Fields
  //

  private ArtType artType;
  private ArtStyle artStyle;
  private double cost;
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
    return "Art{" +
            "artType=" + artType +
            ", artStyle=" + artStyle +
            ", cost=" + cost +
            super.toString() +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Art art = (Art) o;
    return Double.compare(art.cost, cost) == 0 && artType == art.artType && artStyle == art.artStyle;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), artType, artStyle, cost);
  }
}
