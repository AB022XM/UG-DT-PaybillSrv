package ug.co.absa.paybill.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;
import ug.co.absa.paybill.domain.enumeration.PaymentChannel;

/**
 * A DTO for the {@link ug.co.absa.paybill.domain.Paybill} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaybillDTO implements Serializable {

    private Long id;

    @NotNull
    private UUID recordUniqueIdentifier;

    private LocalDate processTimestamp;

    private Integer feeAmount;

    @Size(min = 3, max = 200)
    private String feeDescription;

    private LocalDate feeDueFromDate;

    private LocalDate feeDueToDate;

    @NotNull
    @Size(min = 1, max = 50)
    private String feeId;

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 50)
    private String middleName;

    private Integer outstandingAmount;

    @NotNull
    @Size(min = 3, max = 50)
    private String paymentCode;

    @NotNull
    @Size(min = 3, max = 50)
    private String schoolCode;

    @NotNull
    @Size(min = 3, max = 200)
    private String schoolName;

    @NotNull
    @Size(min = 3, max = 50)
    private String studentClass;

    @NotNull
    private PaymentChannel paymentChannel;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private BillerDTO biller;

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

    public LocalDate getProcessTimestamp() {
        return processTimestamp;
    }

    public void setProcessTimestamp(LocalDate processTimestamp) {
        this.processTimestamp = processTimestamp;
    }

    public Integer getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }

    public LocalDate getFeeDueFromDate() {
        return feeDueFromDate;
    }

    public void setFeeDueFromDate(LocalDate feeDueFromDate) {
        this.feeDueFromDate = feeDueFromDate;
    }

    public LocalDate getFeeDueToDate() {
        return feeDueToDate;
    }

    public void setFeeDueToDate(LocalDate feeDueToDate) {
        this.feeDueToDate = feeDueToDate;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Integer outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public PaymentChannel getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannel paymentChannel) {
        this.paymentChannel = paymentChannel;
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

    public BillerDTO getBiller() {
        return biller;
    }

    public void setBiller(BillerDTO biller) {
        this.biller = biller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaybillDTO)) {
            return false;
        }

        PaybillDTO paybillDTO = (PaybillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paybillDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaybillDTO{" +
            "id=" + getId() +
            ", recordUniqueIdentifier='" + getRecordUniqueIdentifier() + "'" +
            ", processTimestamp='" + getProcessTimestamp() + "'" +
            ", feeAmount=" + getFeeAmount() +
            ", feeDescription='" + getFeeDescription() + "'" +
            ", feeDueFromDate='" + getFeeDueFromDate() + "'" +
            ", feeDueToDate='" + getFeeDueToDate() + "'" +
            ", feeId='" + getFeeId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", outstandingAmount=" + getOutstandingAmount() +
            ", paymentCode='" + getPaymentCode() + "'" +
            ", schoolCode='" + getSchoolCode() + "'" +
            ", schoolName='" + getSchoolName() + "'" +
            ", studentClass='" + getStudentClass() + "'" +
            ", paymentChannel='" + getPaymentChannel() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", biller=" + getBiller() +
            "}";
    }
}
