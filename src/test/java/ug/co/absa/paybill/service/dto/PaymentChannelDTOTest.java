package ug.co.absa.paybill.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class PaymentChannelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentChannelDTO.class);
        PaymentChannelDTO paymentChannelDTO1 = new PaymentChannelDTO();
        paymentChannelDTO1.setId(1L);
        PaymentChannelDTO paymentChannelDTO2 = new PaymentChannelDTO();
        assertThat(paymentChannelDTO1).isNotEqualTo(paymentChannelDTO2);
        paymentChannelDTO2.setId(paymentChannelDTO1.getId());
        assertThat(paymentChannelDTO1).isEqualTo(paymentChannelDTO2);
        paymentChannelDTO2.setId(2L);
        assertThat(paymentChannelDTO1).isNotEqualTo(paymentChannelDTO2);
        paymentChannelDTO1.setId(null);
        assertThat(paymentChannelDTO1).isNotEqualTo(paymentChannelDTO2);
    }
}
