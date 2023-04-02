package ug.co.absa.paybill.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class ContactInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactInfo.class);
        ContactInfo contactInfo1 = new ContactInfo();
        contactInfo1.setId(1L);
        ContactInfo contactInfo2 = new ContactInfo();
        contactInfo2.setId(contactInfo1.getId());
        assertThat(contactInfo1).isEqualTo(contactInfo2);
        contactInfo2.setId(2L);
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);
        contactInfo1.setId(null);
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);
    }
}
