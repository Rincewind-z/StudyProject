package ru.sfedu.studyProject.lab5.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Class Customer
 */
@Entity(name = "lab5_customer")
@Table(schema = "schema_lab5")
public class Customer {

  //
  // Fields
  //

  private long userId;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @OneToOne(fetch = FetchType.EAGER)
  private Project project;
  private Date dateOfCreation;
  private String name;
  @OneToOne(mappedBy = "customer",
  cascade = CascadeType.PERSIST)
  private URLs url = new URLs();
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

  public void setProject(Project project) {
    id = project.getId();
    this.project = project;
  }

  public Project getProject() {
    return project;
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

  public URLs getUrl() {
    return url;
  }

  public void setUrl(URLs url) {
    this.url = url;
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
    return userId == customer.userId && id == customer.id && Objects.equals(dateOfCreation, customer.dateOfCreation) && Objects.equals(name, customer.name) && Objects.equals(phoneNumber, customer.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, id, dateOfCreation, name, url, phoneNumber);
  }
}
