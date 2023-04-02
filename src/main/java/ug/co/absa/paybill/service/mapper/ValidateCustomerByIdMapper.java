package ug.co.absa.paybill.service.mapper;

import org.mapstruct.*;
import ug.co.absa.paybill.domain.ValidateCustomerById;
import ug.co.absa.paybill.service.dto.ValidateCustomerByIdDTO;

/**
 * Mapper for the entity {@link ValidateCustomerById} and its DTO {@link ValidateCustomerByIdDTO}.
 */
@Mapper(componentModel = "spring")
public interface ValidateCustomerByIdMapper extends EntityMapper<ValidateCustomerByIdDTO, ValidateCustomerById> {}
