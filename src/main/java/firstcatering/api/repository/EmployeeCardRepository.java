package firstcatering.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import firstcatering.api.model.EmployeeCard;

@Repository

public interface EmployeeCardRepository extends JpaRepository<EmployeeCard, Integer> {

  EmployeeCard findByCardID(String cardID);
}
