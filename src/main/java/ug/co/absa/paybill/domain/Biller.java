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
 * This is a student class,\n@author Banada Ismael ABSA DT team
 */
@Entity
@Table(name = "biller")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Biller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "record_unique_identifier", length = 36, nullable = false, unique = true)
    private UUID recordUniqueIdentifier;

    @Column(name = "biller_id", unique = true)
    private Integer billerId;

    @NotNull
    @Column(name = "biller_code", nullable = false)
    private Integer billerCode;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

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

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "is_deleted")
    private LocalDate isDeleted;

    @OneToMany(mappedBy = "biller")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "associatedFees", "biller" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "biller")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "biller" }, allowSetters = true)
    private Set<Paybill> paybills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Biller id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRecordUniqueIdentifier() {
        return this.recordUniqueIdentifier;
    }

    public Biller recordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.setRecordUniqueIdentifier(recordUniqueIdentifier);
        return this;
    }

    public void setRecordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.recordUniqueIdentifier = recordUniqueIdentifier;
    }

    public Integer getBillerId() {
        return this.billerId;
    }

    public Biller billerId(Integer billerId) {
        this.setBillerId(billerId);
        return this;
    }

    public void setBillerId(Integer billerId) {
        this.billerId = billerId;
    }

    public Integer getBillerCode() {
        return this.billerCode;
    }

    public Biller billerCode(Integer billerCode) {
        this.setBillerCode(billerCode);
        return this;
    }

    public void setBillerCode(Integer billerCode) {
        this.billerCode = billerCode;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public Biller categoryId(Integer categoryId) {
        this.setCategoryId(categoryId);
        return this;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getContactId() {
        return this.contactId;
    }

    public Biller contactId(Integer contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getAddressId() {
        return this.addressId;
    }

    public Biller addressId(Integer addressId) {
        this.setAddressId(addressId);
        return this;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Biller firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Biller middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Biller lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Biller dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOutStandingAmount() {
        return this.outStandingAmount;
    }

    public Biller outStandingAmount(String outStandingAmount) {
        this.setOutStandingAmount(outStandingAmount);
        return this;
    }

    public void setOutStandingAmount(String outStandingAmount) {
        this.outStandingAmount = outStandingAmount;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public Biller status(RecordStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Biller freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Biller freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Biller freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LocalDate getIsDeleted() {
        return this.isDeleted;
    }

    public Biller isDeleted(LocalDate isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(LocalDate isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setBiller(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setBiller(this));
        }
        this.payments = payments;
    }

    public Biller payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public Biller addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setBiller(this);
        return this;
    }

    public Biller removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setBiller(null);
        return this;
    }

    public Set<Paybill> getPaybills() {
        return this.paybills;
    }

    public void setPaybills(Set<Paybill> paybills) {
        if (this.paybills != null) {
            this.paybills.forEach(i -> i.setBiller(null));
        }
        if (paybills != null) {
            paybills.forEach(i -> i.setBiller(this));
        }
        this.paybills = paybills;
    }

    public Biller paybills(Set<Paybill> paybills) {
        this.setPaybills(paybills);
        return this;
    }

    public Biller addPaybill(Paybill paybill) {
        this.paybills.add(paybill);
        paybill.setBiller(this);
        return this;
    }

    public Biller removePaybill(Paybill paybill) {
        this.paybills.remove(paybill);
        paybill.setBiller(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Biller)) {
            return false;
        }
        return id != null && id.equals(((Biller) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Biller{" +
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
