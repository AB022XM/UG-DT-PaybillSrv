package ug.co.absa.paybill.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class PaybillDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaybillDTO.class);
        PaybillDTO paybillDTO1 = new PaybillDTO();
        paybillDTO1.setId(1L);
        PaybillDTO paybillDTO2 = new PaybillDTO();
        assertThat(paybillDTO1).isNotEqualTo(paybillDTO2);
        paybillDTO2.setId(paybillDTO1.getId());
        assertThat(paybillDTO1).isEqualTo(paybillDTO2);
        paybillDTO2.setId(2L);
        assertThat(paybillDTO1).isNotEqualTo(paybillDTO2);
        paybillDTO1.setId(null);
        assertThat(paybillDTO1).isNotEqualTo(paybillDTO2);
    }
}
