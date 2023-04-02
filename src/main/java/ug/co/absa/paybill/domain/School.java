package ug.co.absa.paybill.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;

/**
 * A School.
 */
@Entity
@Table(name = "school")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "record_unique_identifier", length = 36, nullable = false)
    private UUID recordUniqueIdentifier;

    @NotNull
    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    @NotNull
    @Column(name = "school_code", nullable = false)
    private String schoolCode;

    @Column(name = "school_phone_number")
    private String schoolPhoneNumber;

    @Column(name = "school_alternative_phone_number")
    private String schoolAlternativePhoneNumber;

    @Column(name = "schoolemail_addess")
    private String schoolemailAddess;

    @NotNull
    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RecordStatus status;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @Column(name = "approved_date")
    private Instant approvedDate;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "is_deleted")
    private LocalDate isDeleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public School id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRecordUniqueIdentifier() {
        return this.recordUniqueIdentifier;
    }

    public School recordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.setRecordUniqueIdentifier(recordUniqueIdentifier);
        return this;
    }

    public void setRecordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.recordUniqueIdentifier = recordUniqueIdentifier;
    }

    public Integer getSchoolId() {
        return this.schoolId;
    }

    public School schoolId(Integer schoolId) {
        this.setSchoolId(schoolId);
        return this;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolCode() {
        return this.schoolCode;
    }

    public School schoolCode(String schoolCode) {
        this.setSchoolCode(schoolCode);
        return this;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolPhoneNumber() {
        return this.schoolPhoneNumber;
    }

    public School schoolPhoneNumber(String schoolPhoneNumber) {
        this.setSchoolPhoneNumber(schoolPhoneNumber);
        return this;
    }

    public void setSchoolPhoneNumber(String schoolPhoneNumber) {
        this.schoolPhoneNumber = schoolPhoneNumber;
    }

    public String getSchoolAlternativePhoneNumber() {
        return this.schoolAlternativePhoneNumber;
    }

    public School schoolAlternativePhoneNumber(String schoolAlternativePhoneNumber) {
        this.setSchoolAlternativePhoneNumber(schoolAlternativePhoneNumber);
        return this;
    }

    public void setSchoolAlternativePhoneNumber(String schoolAlternativePhoneNumber) {
        this.schoolAlternativePhoneNumber = schoolAlternativePhoneNumber;
    }

    public String getSchoolemailAddess() {
        return this.schoolemailAddess;
    }

    public School schoolemailAddess(String schoolemailAddess) {
        this.setSchoolemailAddess(schoolemailAddess);
        return this;
    }

    public void setSchoolemailAddess(String schoolemailAddess) {
        this.schoolemailAddess = schoolemailAddess;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public School schoolName(String schoolName) {
        this.setSchoolName(schoolName);
        return this;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public School status(RecordStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public School freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public Instant getRegistrationDate() {
        return this.registrationDate;
    }

    public School registrationDate(Instant registrationDate) {
        this.setRegistrationDate(registrationDate);
        return this;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Instant getApprovedDate() {
        return this.approvedDate;
    }

    public School approvedDate(Instant approvedDate) {
        this.setApprovedDate(approvedDate);
        return this;
    }

    public void setApprovedDate(Instant approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public School freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public School freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LocalDate getIsDeleted() {
        return this.isDeleted;
    }

    public School isDeleted(LocalDate isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(LocalDate isDeleted) {
        this.isDeleted = isDeleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof School)) {
            return false;
        }
        return id != null && id.equals(((School) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", recordUniqueIdentifier='" + getRecordUniqueIdentifier() + "'" +
            ", schoolId=" + getSchoolId() +
            ", schoolCode='" + getSchoolCode() + "'" +
            ", schoolPhoneNumber='" + getSchoolPhoneNumber() + "'" +
            ", schoolAlternativePhoneNumber='" + getSchoolAlternativePhoneNumber() + "'" +
            ", schoolemailAddess='" + getSchoolemailAddess() + "'" +
            ", schoolName='" + getSchoolName() + "'" +
            ", status='" + getStatus() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
