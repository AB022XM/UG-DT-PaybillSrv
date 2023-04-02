package ug.co.absa.paybill.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import ug.co.absa.paybill.domain.enumeration.PaymentChannel;

/**
 * A Paybill.
 */
@Entity
@Table(name = "paybill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paybill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "record_unique_identifier", length = 36, nullable = false, unique = true)
    private UUID recordUniqueIdentifier;

    @Column(name = "process_timestamp")
    private LocalDate processTimestamp;

    @Column(name = "fee_amount")
    private Integer feeAmount;

    @Size(min = 3, max = 200)
    @Column(name = "fee_description", length = 200)
    private String feeDescription;

    @Column(name = "fee_due_from_date")
    private LocalDate feeDueFromDate;

    @Column(name = "fee_due_to_date")
    private LocalDate feeDueToDate;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "fee_id", length = 50, nullable = false)
    private String feeId;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "middle_name", length = 50, nullable = false)
    private String middleName;

    @Column(name = "outstanding_amount")
    private Integer outstandingAmount;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "payment_code", length = 50, nullable = false)
    private String paymentCode;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "school_code", length = 50, nullable = false)
    private String schoolCode;

    @NotNull
    @Size(min = 3, max = 200)
    @Column(name = "school_name", length = 200, nullable = false)
    private String schoolName;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "student_class", length = 50, nullable = false)
    private String studentClass;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_channel", nullable = false)
    private PaymentChannel paymentChannel;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @ManyToOne
    @JsonIgnoreProperties(value = { "payments", "paybills" }, allowSetters = true)
    private Biller biller;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paybill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRecordUniqueIdentifier() {
        return this.recordUniqueIdentifier;
    }

    public Paybill recordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.setRecordUniqueIdentifier(recordUniqueIdentifier);
        return this;
    }

    public void setRecordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.recordUniqueIdentifier = recordUniqueIdentifier;
    }

    public LocalDate getProcessTimestamp() {
        return this.processTimestamp;
    }

    public Paybill processTimestamp(LocalDate processTimestamp) {
        this.setProcessTimestamp(processTimestamp);
        return this;
    }

    public void setProcessTimestamp(LocalDate processTimestamp) {
        this.processTimestamp = processTimestamp;
    }

    public Integer getFeeAmount() {
        return this.feeAmount;
    }

    public Paybill feeAmount(Integer feeAmount) {
        this.setFeeAmount(feeAmount);
        return this;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeDescription() {
        return this.feeDescription;
    }

    public Paybill feeDescription(String feeDescription) {
        this.setFeeDescription(feeDescription);
        return this;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }

    public LocalDate getFeeDueFromDate() {
        return this.feeDueFromDate;
    }

    public Paybill feeDueFromDate(LocalDate feeDueFromDate) {
        this.setFeeDueFromDate(feeDueFromDate);
        return this;
    }

    public void setFeeDueFromDate(LocalDate feeDueFromDate) {
        this.feeDueFromDate = feeDueFromDate;
    }

    public LocalDate getFeeDueToDate() {
        return this.feeDueToDate;
    }

    public Paybill feeDueToDate(LocalDate feeDueToDate) {
        this.setFeeDueToDate(feeDueToDate);
        return this;
    }

    public void setFeeDueToDate(LocalDate feeDueToDate) {
        this.feeDueToDate = feeDueToDate;
    }

    public String getFeeId() {
        return this.feeId;
    }

    public Paybill feeId(String feeId) {
        this.setFeeId(feeId);
        return this;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Paybill firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Paybill lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Paybill middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getOutstandingAmount() {
        return this.outstandingAmount;
    }

    public Paybill outstandingAmount(Integer outstandingAmount) {
        this.setOutstandingAmount(outstandingAmount);
        return this;
    }

    public void setOutstandingAmount(Integer outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getPaymentCode() {
        return this.paymentCode;
    }

    public Paybill paymentCode(String paymentCode) {
        this.setPaymentCode(paymentCode);
        return this;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getSchoolCode() {
        return this.schoolCode;
    }

    public Paybill schoolCode(String schoolCode) {
        this.setSchoolCode(schoolCode);
        return this;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public Paybill schoolName(String schoolName) {
        this.setSchoolName(schoolName);
        return this;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStudentClass() {
        return this.studentClass;
    }

    public Paybill studentClass(String studentClass) {
        this.setStudentClass(studentClass);
        return this;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public PaymentChannel getPaymentChannel() {
        return this.paymentChannel;
    }

    public Paybill paymentChannel(PaymentChannel paymentChannel) {
        this.setPaymentChannel(paymentChannel);
        return this;
    }

    public void setPaymentChannel(PaymentChannel paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Paybill freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Paybill freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Paybill freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public Biller getBiller() {
        return this.biller;
    }

    public void setBiller(Biller biller) {
        this.biller = biller;
    }

    public Paybill biller(Biller biller) {
        this.setBiller(biller);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paybill)) {
            return false;
        }
        return id != null && id.equals(((Paybill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paybill{" +
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
            "}";
    }
}
