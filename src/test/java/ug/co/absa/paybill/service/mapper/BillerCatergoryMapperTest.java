package ug.co.absa.paybill.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BillerCatergoryMapperTest {

    private BillerCatergoryMapper billerCatergoryMapper;

    @BeforeEach
    public void setUp() {
        billerCatergoryMapper = new BillerCatergoryMapperImpl();
    }
}
