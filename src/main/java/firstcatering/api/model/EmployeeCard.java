package firstcatering.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "EmployeeCards")
@ToString
@Data
public class EmployeeCard extends RepresentationModel<EmployeeCard> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Pattern(regexp = "^\\d{3}\\D{1}$")
  @NotEmpty
  @Column(unique = true)
  @JsonProperty("employee-id")
  private String employeeID;

  @NotEmpty
  private String name;

  @Email
  @Column(unique = true)
  private String email;

  @Pattern(regexp = "^07[\\d]{9}$")
  private String mobile;

  private String pin;

  @Pattern(regexp = "^[\\d\\D]{16}$")
  @JsonProperty("card-id")
  @NotEmpty
  @Column(unique = true)
  private String cardID;

  //TODO: change type for a better representation of money
  private Double balance = .0;

  private Boolean active = true;

  public EmployeeCard() {
  }

  /**
   *
   * @param employeeID
   * @param name
   * @param email
   * @param mobile
   * @param pin
   * @param cardID
   */
  public EmployeeCard(
      @Pattern(regexp = "^\\d{3}\\D{1}$") @NotEmpty String employeeID,
      @NotEmpty String name,
      @Email String email,
      @Pattern(regexp = "^07[\\d]{9}$") String mobile,
      String pin,
      @Pattern(regexp = "^[\\d\\D]{16}$") @NotEmpty String cardID) {
    super();
    this.employeeID = employeeID;
    this.name = name;
    this.email = email;
    this.mobile = mobile;
    this.pin = pin;
    this.cardID = cardID;
  }

  /**
   *
   * @param amount
   * @return get balance
   */
  public Double topUpBalance(Double amount) {
    if (amount > 0.00) {
      return balance += amount;
    }
    return balance;
  }

  /**
   *
   * @return get pin
   */
  public String getPin() {
    return pin;
  }

  /**
   *
   * @param pin
   * sets encoded pin
   */
  public void setPin(String pin) {
    this.pin = new BCryptPasswordEncoder().encode(pin);
  }
}
