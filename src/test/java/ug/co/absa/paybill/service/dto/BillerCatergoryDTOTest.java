package ug.co.absa.paybill.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class BillerCatergoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillerCatergoryDTO.class);
        BillerCatergoryDTO billerCatergoryDTO1 = new BillerCatergoryDTO();
        billerCatergoryDTO1.setId(1L);
        BillerCatergoryDTO billerCatergoryDTO2 = new BillerCatergoryDTO();
        assertThat(billerCatergoryDTO1).isNotEqualTo(billerCatergoryDTO2);
        billerCatergoryDTO2.setId(billerCatergoryDTO1.getId());
        assertThat(billerCatergoryDTO1).isEqualTo(billerCatergoryDTO2);
        billerCatergoryDTO2.setId(2L);
        assertThat(billerCatergoryDTO1).isNotEqualTo(billerCatergoryDTO2);
        billerCatergoryDTO1.setId(null);
        assertThat(billerCatergoryDTO1).isNotEqualTo(billerCatergoryDTO2);
    }
}
