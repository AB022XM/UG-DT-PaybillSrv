package ug.co.absa.paybill.service.mapper;

import org.mapstruct.*;
import ug.co.absa.paybill.domain.PaymentChannel;
import ug.co.absa.paybill.service.dto.PaymentChannelDTO;

/**
 * Mapper for the entity {@link PaymentChannel} and its DTO {@link PaymentChannelDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentChannelMapper extends EntityMapper<PaymentChannelDTO, PaymentChannel> {}
