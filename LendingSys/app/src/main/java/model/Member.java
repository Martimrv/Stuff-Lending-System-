package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Member class.
 */
public class Member {
  private String memberId;
  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private String email;
  private String phoneNumber;
  private Date createDate;
  private double points;

  /**
   * Constructor.
   */
  public Member(String memberId, String userName,
      String password, String email, String phoneNumber) {
    this.memberId = memberId;
    this.userName = userName;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.createDate = new Date();
    this.points = 0;
  }

  // New constructor to set initial points
  public Member(String memberId, String userName,
      String password, String email, String phoneNumber, double initialPoints) {
    this(memberId, userName, password, email, phoneNumber); // Call the existing constructor
    this.points = initialPoints;
  }

  // Getters and Setters for all attributes

  public String getMemberId() {
    return memberId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public double getPoints() {
    return points;
  }

  public void setPoints(double points) {
    this.points = points;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getCreateDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(createDate);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
