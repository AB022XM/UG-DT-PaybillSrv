package ug.co.absa.paybill.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.paybill.domain.ValidateCustomerById;

/**
 * Spring Data JPA repository for the ValidateCustomerById entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValidateCustomerByIdRepository extends JpaRepository<ValidateCustomerById, Long> {}
