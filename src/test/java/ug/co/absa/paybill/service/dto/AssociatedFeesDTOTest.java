package ug.co.absa.paybill.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class AssociatedFeesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociatedFeesDTO.class);
        AssociatedFeesDTO associatedFeesDTO1 = new AssociatedFeesDTO();
        associatedFeesDTO1.setId(1L);
        AssociatedFeesDTO associatedFeesDTO2 = new AssociatedFeesDTO();
        assertThat(associatedFeesDTO1).isNotEqualTo(associatedFeesDTO2);
        associatedFeesDTO2.setId(associatedFeesDTO1.getId());
        assertThat(associatedFeesDTO1).isEqualTo(associatedFeesDTO2);
        associatedFeesDTO2.setId(2L);
        assertThat(associatedFeesDTO1).isNotEqualTo(associatedFeesDTO2);
        associatedFeesDTO1.setId(null);
        assertThat(associatedFeesDTO1).isNotEqualTo(associatedFeesDTO2);
    }
}
