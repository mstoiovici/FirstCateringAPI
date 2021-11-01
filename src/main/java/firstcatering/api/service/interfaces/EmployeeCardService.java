package firstcatering.api.service.interfaces;

import java.util.List;

import firstcatering.api.model.EmployeeCard;

public interface EmployeeCardService {

  EmployeeCard registerEmployeeCard(EmployeeCard cardDetails);

  EmployeeCard topUpEmployeeCard(EmployeeCard employeeCard, Double amount);

  Double getEmployeeCardBalance(String cardID);

  EmployeeCard getEmployeeCardByCardID(String cardID);

  List<EmployeeCard> getAllEmployeeCards();

  String freezeEmployeeCard(String cardID);

}
