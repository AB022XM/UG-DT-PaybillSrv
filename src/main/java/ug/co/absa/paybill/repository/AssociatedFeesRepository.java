package ug.co.absa.paybill.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.paybill.domain.AssociatedFees;

/**
 * Spring Data JPA repository for the AssociatedFees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssociatedFeesRepository extends JpaRepository<AssociatedFees, Long> {}
