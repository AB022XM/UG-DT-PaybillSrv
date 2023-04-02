package ug.co.absa.paybill.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ValidateCustomerById.
 */
@Entity
@Table(name = "validate_customer_by_id")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ValidateCustomerById implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "customner_id", nullable = false, unique = true)
    private Integer customnerId;

    @NotNull
    @Column(name = "bill_code", nullable = false)
    private Integer billCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ValidateCustomerById id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomnerId() {
        return this.customnerId;
    }

    public ValidateCustomerById customnerId(Integer customnerId) {
        this.setCustomnerId(customnerId);
        return this;
    }

    public void setCustomnerId(Integer customnerId) {
        this.customnerId = customnerId;
    }

    public Integer getBillCode() {
        return this.billCode;
    }

    public ValidateCustomerById billCode(Integer billCode) {
        this.setBillCode(billCode);
        return this;
    }

    public void setBillCode(Integer billCode) {
        this.billCode = billCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValidateCustomerById)) {
            return false;
        }
        return id != null && id.equals(((ValidateCustomerById) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ValidateCustomerById{" +
            "id=" + getId() +
            ", customnerId=" + getCustomnerId() +
            ", billCode=" + getBillCode() +
            "}";
    }
}
