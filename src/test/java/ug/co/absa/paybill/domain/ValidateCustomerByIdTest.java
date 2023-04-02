package ug.co.absa.paybill.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class ValidateCustomerByIdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidateCustomerById.class);
        ValidateCustomerById validateCustomerById1 = new ValidateCustomerById();
        validateCustomerById1.setId(1L);
        ValidateCustomerById validateCustomerById2 = new ValidateCustomerById();
        validateCustomerById2.setId(validateCustomerById1.getId());
        assertThat(validateCustomerById1).isEqualTo(validateCustomerById2);
        validateCustomerById2.setId(2L);
        assertThat(validateCustomerById1).isNotEqualTo(validateCustomerById2);
        validateCustomerById1.setId(null);
        assertThat(validateCustomerById1).isNotEqualTo(validateCustomerById2);
    }
}
