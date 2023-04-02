package ug.co.absa.paybill.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidateCustomerByIdMapperTest {

    private ValidateCustomerByIdMapper validateCustomerByIdMapper;

    @BeforeEach
    public void setUp() {
        validateCustomerByIdMapper = new ValidateCustomerByIdMapperImpl();
    }
}
