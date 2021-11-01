package firstcatering.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeCardTest {

  private Validator validator;
  private EmployeeCard employeeCard;

  @Before
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    employeeCard = new EmployeeCard();
  }

  @Test
  public void validEmployeeCardDetailsWillNotReturnConstraintViolations() {
    employeeCard.setName("John Smith");
    employeeCard.setEmployeeID("111a");
    // email field will never return validation violations as there are no constraints for it yet
    employeeCard.setEmail("john.smith@enterprise.com");
    employeeCard.setMobile("07763876485");
    employeeCard.setCardID("LH6f98N0G54si1jA");

    Set<ConstraintViolation<EmployeeCard>> violations = validator.validate(employeeCard);

    assertTrue(violations.isEmpty());
  }

  @Test
  public void nullEmployeeCardNameDetailWillReturnConstraintViolation() {
    employeeCard.setName("");
    employeeCard.setEmployeeID("111a");
    // email field will never return validation violations as there are no constraints for it yet
    employeeCard.setEmail("john.smith@enterprise.com");
    employeeCard.setMobile("07763876485");
    employeeCard.setCardID("LH6f98N0G54si1jA");

    Set<ConstraintViolation<EmployeeCard>> violations = validator.validate(employeeCard);

    assertEquals(1, violations.size());
  }

  @Test
  public void invalidEmployeeCardDetailsWillReturnConstraintViolations() {
    employeeCard.setName("");
    employeeCard.setEmployeeID("111aA");
    // email field will never return validation violations as there are no constraints for it yet
    employeeCard.setEmail("john.smith@enterprise.com");
    employeeCard.setMobile("123456789");
    employeeCard.setCardID("LH6f98N0G5");

    Set<ConstraintViolation<EmployeeCard>> violations = validator.validate(employeeCard);

    assertEquals(4, violations.size());
  }
}
