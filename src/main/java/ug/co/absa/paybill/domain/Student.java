package ug.co.absa.paybill.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "record_unique_identifier", length = 36, nullable = false, unique = true)
    private UUID recordUniqueIdentifier;

    @NotNull
    @Column(name = "student_id", nullable = false, unique = true)
    private Integer studentId;

    @NotNull
    @Column(name = "student_code", nullable = false)
    private Integer studentCode;

    @NotNull
    @Column(name = "student_class_id", nullable = false)
    private Integer studentClassId;

    @NotNull
    @Column(name = "contact_id", nullable = false)
    private Integer contactId;

    @NotNull
    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "middle_name", length = 30, nullable = false)
    private String middleName;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "payment_code", length = 20, nullable = false)
    private String paymentCode;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Size(min = 3, max = 8)
    @Column(name = "out_standing_amount", length = 8, nullable = false)
    private String outStandingAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RecordStatus status;

    @Column(name = "biller_contact")
    private String billerContact;

    @Column(name = "biller_address")
    private String billerAddress;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "is_deleted")
    private LocalDate isDeleted;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student" }, allowSetters = true)
    private Set<ContactInfo> contactInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRecordUniqueIdentifier() {
        return this.recordUniqueIdentifier;
    }

    public Student recordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.setRecordUniqueIdentifier(recordUniqueIdentifier);
        return this;
    }

    public void setRecordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.recordUniqueIdentifier = recordUniqueIdentifier;
    }

    public Integer getStudentId() {
        return this.studentId;
    }

    public Student studentId(Integer studentId) {
        this.setStudentId(studentId);
        return this;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentCode() {
        return this.studentCode;
    }

    public Student studentCode(Integer studentCode) {
        this.setStudentCode(studentCode);
        return this;
    }

    public void setStudentCode(Integer studentCode) {
        this.studentCode = studentCode;
    }

    public Integer getStudentClassId() {
        return this.studentClassId;
    }

    public Student studentClassId(Integer studentClassId) {
        this.setStudentClassId(studentClassId);
        return this;
    }

    public void setStudentClassId(Integer studentClassId) {
        this.studentClassId = studentClassId;
    }

    public Integer getContactId() {
        return this.contactId;
    }

    public Student contactId(Integer contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getAddressId() {
        return this.addressId;
    }

    public Student addressId(Integer addressId) {
        this.setAddressId(addressId);
        return this;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Student firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Student middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Student lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPaymentCode() {
        return this.paymentCode;
    }

    public Student paymentCode(String paymentCode) {
        this.setPaymentCode(paymentCode);
        return this;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Student dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOutStandingAmount() {
        return this.outStandingAmount;
    }

    public Student outStandingAmount(String outStandingAmount) {
        this.setOutStandingAmount(outStandingAmount);
        return this;
    }

    public void setOutStandingAmount(String outStandingAmount) {
        this.outStandingAmount = outStandingAmount;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public Student status(RecordStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getBillerContact() {
        return this.billerContact;
    }

    public Student billerContact(String billerContact) {
        this.setBillerContact(billerContact);
        return this;
    }

    public void setBillerContact(String billerContact) {
        this.billerContact = billerContact;
    }

    public String getBillerAddress() {
        return this.billerAddress;
    }

    public Student billerAddress(String billerAddress) {
        this.setBillerAddress(billerAddress);
        return this;
    }

    public void setBillerAddress(String billerAddress) {
        this.billerAddress = billerAddress;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Student freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Student freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Student freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LocalDate getIsDeleted() {
        return this.isDeleted;
    }

    public Student isDeleted(LocalDate isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(LocalDate isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<ContactInfo> getContactInfos() {
        return this.contactInfos;
    }

    public void setContactInfos(Set<ContactInfo> contactInfos) {
        if (this.contactInfos != null) {
            this.contactInfos.forEach(i -> i.setStudent(null));
        }
        if (contactInfos != null) {
            contactInfos.forEach(i -> i.setStudent(this));
        }
        this.contactInfos = contactInfos;
    }

    public Student contactInfos(Set<ContactInfo> contactInfos) {
        this.setContactInfos(contactInfos);
        return this;
    }

    public Student addContactInfo(ContactInfo contactInfo) {
        this.contactInfos.add(contactInfo);
        contactInfo.setStudent(this);
        return this;
    }

    public Student removeContactInfo(ContactInfo contactInfo) {
        this.contactInfos.remove(contactInfo);
        contactInfo.setStudent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", recordUniqueIdentifier='" + getRecordUniqueIdentifier() + "'" +
            ", studentId=" + getStudentId() +
            ", studentCode=" + getStudentCode() +
            ", studentClassId=" + getStudentClassId() +
            ", contactId=" + getContactId() +
            ", addressId=" + getAddressId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", paymentCode='" + getPaymentCode() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", outStandingAmount='" + getOutStandingAmount() + "'" +
            ", status='" + getStatus() + "'" +
            ", billerContact='" + getBillerContact() + "'" +
            ", billerAddress='" + getBillerAddress() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
