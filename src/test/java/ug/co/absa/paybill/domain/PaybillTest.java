package ug.co.absa.paybill.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class PaybillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paybill.class);
        Paybill paybill1 = new Paybill();
        paybill1.setId(1L);
        Paybill paybill2 = new Paybill();
        paybill2.setId(paybill1.getId());
        assertThat(paybill1).isEqualTo(paybill2);
        paybill2.setId(2L);
        assertThat(paybill1).isNotEqualTo(paybill2);
        paybill1.setId(null);
        assertThat(paybill1).isNotEqualTo(paybill2);
    }
}
