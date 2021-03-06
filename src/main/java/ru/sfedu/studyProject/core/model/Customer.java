package ru.sfedu.studyProject.core.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Attribute;
import ru.sfedu.studyProject.core.Constants;

import java.util.Date;
import java.util.Objects;

/**
 * Class Customer
 */
public class Customer {

  //
  // Fields
  //
  @Attribute
  @CsvBindByName
  private long userId;
  @Attribute
  @CsvBindByName
  private long id;
  @Attribute
  @CsvBindByName
  @CsvDate(value = Constants.DATE_FORMAT)
  private Date dateOfCreation;
  @Attribute
  @CsvBindByName
  private String name;
  @Attribute
  @CsvBindByName
  private String url;
  @Attribute
  @CsvBindByName
  private String phoneNumber;
  
  //
  // Constructors
  //
  public Customer () { };
  
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
   * Set the value of url
   * @param newVar the new value of url
   */
  public void setUrl (String newVar) {
    url = newVar;
  }

  /**
   * Get the value of url
   * @return the value of url
   */
  public String getUrl () {
    return url;
  }

  /**
   * Set the value of phoneNumber
   * @param newVar the new value of phoneNumber
   */
  public void setPhoneNumber (String newVar) {
    phoneNumber = newVar;
  }

  /**
   * Get the value of phoneNumber
   * @return the value of phoneNumber
   */
  public String getPhoneNumber () {
    return phoneNumber;
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
    return "Customer{" +
            "userId=" + userId +
            ", id=" + id +
            ", dateOfCreation=" + dateOfCreation +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return userId == customer.userId && id == customer.id && Objects.equals(dateOfCreation, customer.dateOfCreation) && Objects.equals(name, customer.name) && Objects.equals(url, customer.url) && Objects.equals(phoneNumber, customer.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, id, dateOfCreation, name, url, phoneNumber);
  }
}
