package ug.co.absa.paybill.service.mapper;

import org.mapstruct.*;
import ug.co.absa.paybill.domain.Biller;
import ug.co.absa.paybill.domain.Payment;
import ug.co.absa.paybill.service.dto.BillerDTO;
import ug.co.absa.paybill.service.dto.PaymentDTO;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "biller", source = "biller", qualifiedByName = "billerId")
    PaymentDTO toDto(Payment s);

    @Named("billerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BillerDTO toDtoBillerId(Biller biller);
}
