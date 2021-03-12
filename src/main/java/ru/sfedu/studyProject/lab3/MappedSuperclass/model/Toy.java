package ru.sfedu.studyProject.lab3.MappedSuperclass.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import ru.sfedu.studyProject.core.Converters.MaterialMapConverter;
import ru.sfedu.studyProject.core.enums.ToyStyle;
import ru.sfedu.studyProject.core.enums.ToyType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;

/**
 * Class Toy
 */
@Entity(name = "mapped_superclass_toy")
@Table (schema = "mapped_superclass")
public class Toy extends Project {

  //
  // Fields
  //
  private ToyStyle toyStyle;
  private ToyType toyType;
//  private Map<Material, Double> outgoings;

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
 /* public void setOutgoings (Map<Material, Double> newVar) {
    outgoings = newVar;
  }*/

  /**
   * Get the value of outgoings
   * @return the value of outgoings
   */
 /* public Map<Material, Double> getOutgoings () {
    return outgoings;
  }*/

  @Override
  public String toString() {
    return "Toy{" +
            "toyStyle=" + toyStyle +
            ", toyType=" + toyType +
            super.toString() +
         //   ", outgoings=" + outgoings +
            '}';
  }

/*  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Toy toy = (Toy) o;
    return toyStyle == toy.toyStyle && toyType == toy.toyType && Objects.equals(outgoings, toy.outgoings);
  }*/

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), toyStyle, toyType/*, outgoings*/);
  }
}
