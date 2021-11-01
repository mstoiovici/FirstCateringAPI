package firstcatering.api.controller;

import firstcatering.api.service.interfaces.EmployeeCardService;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import firstcatering.api.model.EmployeeCard;


@RestController
@RequestMapping(value = "/cards")

public class EmployeeCardController {

  @Autowired
  private EmployeeCardService employeeCardService;

  /**
   *
   * @return get all employee cards
   */
  @GetMapping(value = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<EmployeeCard>> getAllEmployeeCards() {
    List<EmployeeCard> employeeCardList = employeeCardService.getAllEmployeeCards();

    for (EmployeeCard employeeCard : employeeCardList) {
      Link getLink = WebMvcLinkBuilder
          .linkTo(
              EmployeeCardController.class)
          .slash(employeeCard.getId())
          .withSelfRel()
          .withTitle("GET card info");
      employeeCard.add(getLink);
    }
    return new ResponseEntity<List<EmployeeCard>>(employeeCardList, HttpStatus.OK);
  }

  /**
   *
   * @param cardID
   * @return get single employee card with specified cardID
   */
  @GetMapping(value = "/{card-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EmployeeCard> viewEmployeeCard(
      @PathVariable(value = "card-id") String cardID) {
    EmployeeCard card = employeeCardService.getEmployeeCardByCardID(cardID);
    return new ResponseEntity<EmployeeCard>(card, HttpStatus.OK);
  }

  /**
   *
   * @param cardID
   * @return get balance of specified employee card
   */
  @GetMapping(value = "/{card-id}/balance")
  public Double viewEmployeeCardBalance(@PathVariable(value = "card-id") String cardID) {
    return employeeCardService.getEmployeeCardBalance(cardID);
  }

  /**
   *
   * @param employeeCard
   * @return register a new employee card
   */
  //TODO: fix exception/error when the card is already registered
  @PostMapping(value = "/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EmployeeCard> registerEmployeeCard(@Valid @RequestBody EmployeeCard employeeCard) {
    return new ResponseEntity<EmployeeCard>(employeeCardService.registerEmployeeCard(employeeCard),
        HttpStatus.CREATED);
  }

  /**
   *
   * @param cardID
   * @param topUpAmount
   * @return specified employee card with updated balance
   */
  //TODO: when card is frozen do not allow top-up anymore
  @PutMapping(value = "/{card-id}/top-up", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EmployeeCard> topUpEmployeeCardBalance(@PathVariable(value = "card-id") String cardID,
      @RequestParam(value = "amount") Double topUpAmount) {
    EmployeeCard employeeCard = employeeCardService.getEmployeeCardByCardID(cardID);
    employeeCardService.topUpEmployeeCard(employeeCard, topUpAmount);
    return new ResponseEntity<EmployeeCard>(employeeCard, HttpStatus.OK);
  }

  /**
   *
   * @param cardID
   * @return message informing employee card has been frozen
   */
  @PutMapping(value = "/{card-id}/freeze")
  public ResponseEntity<String> freezeEmployeeCard(@PathVariable(value = "card-id") String cardID) {
    String string = employeeCardService.freezeEmployeeCard(cardID);
    return new ResponseEntity<String>(string, HttpStatus.OK);
  }
}
