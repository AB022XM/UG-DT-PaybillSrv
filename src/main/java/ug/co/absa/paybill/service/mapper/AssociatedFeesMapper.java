package ug.co.absa.paybill.service.mapper;

import org.mapstruct.*;
import ug.co.absa.paybill.domain.AssociatedFees;
import ug.co.absa.paybill.domain.Payment;
import ug.co.absa.paybill.service.dto.AssociatedFeesDTO;
import ug.co.absa.paybill.service.dto.PaymentDTO;

/**
 * Mapper for the entity {@link AssociatedFees} and its DTO {@link AssociatedFeesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssociatedFeesMapper extends EntityMapper<AssociatedFeesDTO, AssociatedFees> {
    @Mapping(target = "payment", source = "payment", qualifiedByName = "paymentId")
    AssociatedFeesDTO toDto(AssociatedFees s);

    @Named("paymentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentDTO toDtoPaymentId(Payment payment);
}
