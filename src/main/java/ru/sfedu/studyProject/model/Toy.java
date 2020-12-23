package ru.sfedu.studyProject.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
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
  @Attribute
  @CsvBindByName
  private ToyStyle toyStyle;
  @Attribute
  @CsvBindByName
  private ToyType toyType;
  @ElementMap
  @CsvCustomBindByName(converter = MaterialMapConverter.class)
  private Map<Material, Double> outgoings;

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

  @Override
  public String toString() {
    return "Toy{" +
            "toyStyle=" + toyStyle +
            ", toyType=" + toyType +
            ", outgoings=" + outgoings +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Toy toy = (Toy) o;
    return toyStyle == toy.toyStyle && toyType == toy.toyType && Objects.equals(outgoings, toy.outgoings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), toyStyle, toyType, outgoings);
  }
}
