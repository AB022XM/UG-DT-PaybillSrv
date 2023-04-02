package ug.co.absa.paybill.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class PaymentChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentChannel.class);
        PaymentChannel paymentChannel1 = new PaymentChannel();
        paymentChannel1.setId(1L);
        PaymentChannel paymentChannel2 = new PaymentChannel();
        paymentChannel2.setId(paymentChannel1.getId());
        assertThat(paymentChannel1).isEqualTo(paymentChannel2);
        paymentChannel2.setId(2L);
        assertThat(paymentChannel1).isNotEqualTo(paymentChannel2);
        paymentChannel1.setId(null);
        assertThat(paymentChannel1).isNotEqualTo(paymentChannel2);
    }
}
