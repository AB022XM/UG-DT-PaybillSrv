package ug.co.absa.paybill.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;

/**
 * A DTO for the {@link ug.co.absa.paybill.domain.Biller} entity.
 */
@Schema(description = "This is a student class,\n@author Banada Ismael ABSA DT team")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BillerDTO implements Serializable {

    private Long id;

    @NotNull
    private UUID recordUniqueIdentifier;

    private Integer billerId;

    @NotNull
    private Integer billerCode;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer contactId;

    @NotNull
    private Integer addressId;

    @NotNull
    @Size(min = 3, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    private String middleName;

    @NotNull
    @Size(min = 3, max = 30)
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Size(min = 3, max = 8)
    private String outStandingAmount;

    @NotNull
    private RecordStatus status;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private LocalDate isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRecordUniqueIdentifier() {
        return recordUniqueIdentifier;
    }

    public void setRecordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.recordUniqueIdentifier = recordUniqueIdentifier;
    }

    public Integer getBillerId() {
        return billerId;
    }

    public void setBillerId(Integer billerId) {
        this.billerId = billerId;
    }

    public Integer getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(Integer billerCode) {
        this.billerCode = billerCode;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOutStandingAmount() {
        return outStandingAmount;
    }

    public void setOutStandingAmount(String outStandingAmount) {
        this.outStandingAmount = outStandingAmount;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LocalDate getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(LocalDate isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillerDTO)) {
            return false;
        }

        BillerDTO billerDTO = (BillerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, billerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillerDTO{" +
            "id=" + getId() +
            ", recordUniqueIdentifier='" + getRecordUniqueIdentifier() + "'" +
            ", billerId=" + getBillerId() +
            ", billerCode=" + getBillerCode() +
            ", categoryId=" + getCategoryId() +
            ", contactId=" + getContactId() +
            ", addressId=" + getAddressId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", outStandingAmount='" + getOutStandingAmount() + "'" +
            ", status='" + getStatus() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
