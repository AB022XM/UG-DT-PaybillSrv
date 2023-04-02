package ug.co.absa.paybill.service.mapper;

import org.mapstruct.*;
import ug.co.absa.paybill.domain.Customer;
import ug.co.absa.paybill.service.dto.CustomerDTO;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
