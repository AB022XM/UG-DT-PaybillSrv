package ug.co.absa.paybill.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.paybill.domain.BillerCatergory;

/**
 * Spring Data JPA repository for the BillerCatergory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillerCatergoryRepository extends JpaRepository<BillerCatergory, Long> {}
