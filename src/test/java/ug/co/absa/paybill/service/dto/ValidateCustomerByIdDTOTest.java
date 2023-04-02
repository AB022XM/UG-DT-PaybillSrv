package ug.co.absa.paybill.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class ValidateCustomerByIdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidateCustomerByIdDTO.class);
        ValidateCustomerByIdDTO validateCustomerByIdDTO1 = new ValidateCustomerByIdDTO();
        validateCustomerByIdDTO1.setId(1L);
        ValidateCustomerByIdDTO validateCustomerByIdDTO2 = new ValidateCustomerByIdDTO();
        assertThat(validateCustomerByIdDTO1).isNotEqualTo(validateCustomerByIdDTO2);
        validateCustomerByIdDTO2.setId(validateCustomerByIdDTO1.getId());
        assertThat(validateCustomerByIdDTO1).isEqualTo(validateCustomerByIdDTO2);
        validateCustomerByIdDTO2.setId(2L);
        assertThat(validateCustomerByIdDTO1).isNotEqualTo(validateCustomerByIdDTO2);
        validateCustomerByIdDTO1.setId(null);
        assertThat(validateCustomerByIdDTO1).isNotEqualTo(validateCustomerByIdDTO2);
    }
}
