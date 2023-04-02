package ug.co.absa.paybill.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.paybill.web.rest.TestUtil;

class BillerCatergoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillerCatergory.class);
        BillerCatergory billerCatergory1 = new BillerCatergory();
        billerCatergory1.setId(1L);
        BillerCatergory billerCatergory2 = new BillerCatergory();
        billerCatergory2.setId(billerCatergory1.getId());
        assertThat(billerCatergory1).isEqualTo(billerCatergory2);
        billerCatergory2.setId(2L);
        assertThat(billerCatergory1).isNotEqualTo(billerCatergory2);
        billerCatergory1.setId(null);
        assertThat(billerCatergory1).isNotEqualTo(billerCatergory2);
    }
}
