package firstcatering.api.service.impl;

import firstcatering.api.service.interfaces.EmployeeCardService;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import firstcatering.api.exception.ResourceNotFoundException;
import firstcatering.api.model.EmployeeCard;
import firstcatering.api.repository.EmployeeCardRepository;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private EmployeeCardRepository employeeCardRepository;

  @Override
  public EmployeeCard registerEmployeeCard(EmployeeCard employeeCard) {
    if (!isEmployeeCardRegistered(employeeCard.getCardID())) {
      employeeCard.setPin(employeeCard.getPin());
      return employeeCardRepository.save(employeeCard);
    } else {
      throw new ResourceNotFoundException();
    }
  }

  //TODO: allow toUP only for active cards
  @Override
  public EmployeeCard topUpEmployeeCard(EmployeeCard employeeCard, Double amount) {
    if (isEmployeeCardRegistered(employeeCard.getCardID())) {
      employeeCard.topUpBalance(amount);
      return employeeCardRepository.save(employeeCard);
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public boolean isEmployeeCardRegistered(String cardID) {
    EmployeeCard employeeCard = employeeCardRepository.findByCardID(cardID);
    if (employeeCard != null) {
      return true;
    }
    return false;
  }

  @Override
  public Double getEmployeeCardBalance(String cardID) {
    EmployeeCard employeeCard = getEmployeeCardByCardID(cardID);
    if (employeeCard != null) {
      return employeeCard.getBalance();
    } else {
      throw new ResourceNotFoundException();
    }
  }

  @Override
  // TODO fix line 63
  public EmployeeCard getEmployeeCardByCardID(String cardID) {
    if (isEmployeeCardRegistered(cardID)) {
      return employeeCardRepository.findByCardID(cardID);
    } else {
      throw new ResourceNotFoundException(
          "Employee card not found, enter valid card number ID or register your card");
    }
  }

  @Override
  public List<EmployeeCard> getAllEmployeeCards() {
    List<EmployeeCard> employeeCardList = employeeCardRepository.findAll();
    if (!employeeCardList.isEmpty()) {
      return employeeCardList;
    } else {
      throw new ResourceNotFoundException("No employee card records were found");
    }
  }

  @Override
  public String freezeEmployeeCard(String cardID) {
    EmployeeCard employeeCard = getEmployeeCardByCardID(cardID);
    if (employeeCard != null) {
      employeeCard.setActive(false);
      employeeCardRepository.save(employeeCard);
      return "Card is no longer active.";
    } else {
      throw new ResourceNotFoundException("Card with the provided id not found");
    }
  }
}

