package firstcatering.api.util;

import firstcatering.api.model.EmployeeCard;
import firstcatering.api.service.interfaces.EmployeeCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import firstcatering.api.Application;

@Component
public class DataLoader implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  @Autowired
  private EmployeeCardService cardService;

  @Override
  public void run(String... args) {
    LOG.info("Loading: " + cardService.registerEmployeeCard(
        new EmployeeCard("321x", "John Smith", "user1@enterprise.com", "07543765567", "1234",
            "6h7f9jh6GR879Y7Q")));
    LOG.info("Loading: " + cardService.registerEmployeeCard(
        new EmployeeCard("987y", "John Doe", "user2@enterprise.com", "07342873564", "1234",
            "8HK54dr9GTyh7680")));
    LOG.info("Loading: " + cardService.registerEmployeeCard(
        new EmployeeCard("545z", "Joanne StJohn", "user3@enterprise.com", "07967435234", "1234",
            "12G6hv78tAj76g4f")));
    LOG.info("Loading: " + cardService.registerEmployeeCard(
        new EmployeeCard("111a", "Mariana Stoiovici", "mari@enterprise.com", "07654372876", "3456",
            "8A7f9kj6GR079X4C")));
  }
}