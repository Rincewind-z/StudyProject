package ru.sfedu.studyProject.model;

import ru.sfedu.studyProject.enums.PaymentType;
import ru.sfedu.studyProject.enums.ProjectType;

import java.util.Date;

/**
 * Class Project
 */
abstract public class Project {

  //
  // Fields
  //

  private long id;
  private Customer customer;
  private Date dateOfCreation;
  private Date deadline;
  private String name;
  private float progress;
  private PaymentType paymentType;
  private ProjectType projectType;
  
  //
  // Constructors
  //
  public Project () { };
  
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
   * Set the value of customer
   * @param newVar the new value of customer
   */
  public void setCustomer (Customer newVar) {
    customer = newVar;
  }

  /**
   * Get the value of customer
   * @return the value of customer
   */
  public Customer getCustomer () {
    return customer;
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
   * Set the value of deadline
   * @param newVar the new value of deadline
   */
  public void setDeadline (Date newVar) {
    deadline = newVar;
  }

  /**
   * Get the value of deadline
   * @return the value of deadline
   */
  public Date getDeadline () {
    return deadline;
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

  /**
   * Set the value of paymentType
   * @param newVar the new value of paymentType
   */
  public void setPaymentType (PaymentType newVar) {
    paymentType = newVar;
  }

  /**
   * Get the value of paymentType
   * @return the value of paymentType
   */
  public PaymentType getPaymentType () {
    return paymentType;
  }

  /**
   * Set the value of projectType
   * @param newVar the new value of projectType
   */
  public void setProjectType (ProjectType newVar) {
    projectType = newVar;
  }

  /**
   * Get the value of projectType
   * @return the value of projectType
   */
  public ProjectType getProjectType () {
    return projectType;
  }

  //
  // Other methods
  //

}
