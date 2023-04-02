package ug.co.absa.paybill.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ug.co.absa.paybill.domain.ValidateCustomerById} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ValidateCustomerByIdDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer customnerId;

    @NotNull
    private Integer billCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomnerId() {
        return customnerId;
    }

    public void setCustomnerId(Integer customnerId) {
        this.customnerId = customnerId;
    }

    public Integer getBillCode() {
        return billCode;
    }

    public void setBillCode(Integer billCode) {
        this.billCode = billCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValidateCustomerByIdDTO)) {
            return false;
        }

        ValidateCustomerByIdDTO validateCustomerByIdDTO = (ValidateCustomerByIdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, validateCustomerByIdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ValidateCustomerByIdDTO{" +
            "id=" + getId() +
            ", customnerId=" + getCustomnerId() +
            ", billCode=" + getBillCode() +
            "}";
    }
}
