package ug.co.absa.paybill.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class AssociatedFeesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociatedFees.class);
        AssociatedFees associatedFees1 = new AssociatedFees();
        associatedFees1.setId(1L);
        AssociatedFees associatedFees2 = new AssociatedFees();
        associatedFees2.setId(associatedFees1.getId());
        assertThat(associatedFees1).isEqualTo(associatedFees2);
        associatedFees2.setId(2L);
        assertThat(associatedFees1).isNotEqualTo(associatedFees2);
        associatedFees1.setId(null);
        assertThat(associatedFees1).isNotEqualTo(associatedFees2);
    }
}
