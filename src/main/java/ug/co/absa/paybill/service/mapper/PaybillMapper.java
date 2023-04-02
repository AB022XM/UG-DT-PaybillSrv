package ug.co.absa.paybill.service.mapper;

import org.mapstruct.*;
import ug.co.absa.paybill.domain.Biller;
import ug.co.absa.paybill.domain.Paybill;
import ug.co.absa.paybill.service.dto.BillerDTO;
import ug.co.absa.paybill.service.dto.PaybillDTO;

/**
 * Mapper for the entity {@link Paybill} and its DTO {@link PaybillDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaybillMapper extends EntityMapper<PaybillDTO, Paybill> {
    @Mapping(target = "biller", source = "biller", qualifiedByName = "billerId")
    PaybillDTO toDto(Paybill s);

    @Named("billerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BillerDTO toDtoBillerId(Biller biller);
}
