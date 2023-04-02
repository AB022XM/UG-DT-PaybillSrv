package ug.co.absa.paybill.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import ug.co.absa.paybill.domain.enumeration.PaymentChannel;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;

/**
 * A PaymentChannel.
 */
@Entity
@Table(name = "payment_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentChannel implements Serializable {

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
    @Column(name = "channel_id", nullable = false)
    private Integer channelId;

    @Column(name = "channel_code")
    private Integer channelCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "channel_name", nullable = false)
    private PaymentChannel channelName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RecordStatus status;

    /**
     * This is an additional field\ncreated in case there is need to\nadd a field without the need to alter database configs
     */
    @Column(name = "free_field_1")
    private String freeField1;

    /**
     * This is an additional field\ncreated in case there is need to\nadd a field without the need to alter database configs
     */
    @Column(name = "free_field_2")
    private String freeField2;

    /**
     * This is an additional field\ncreated in case there is need to\nadd a field without the need to alter database configs
     */
    @Column(name = "free_field_3")
    private String freeField3;

    /**
     * this is used to identify whether the\nrecord has been deleted or not (Y-deleted, N-Not deleted)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "is_deleted")
    private RecordStatus isDeleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentChannel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRecordUniqueIdentifier() {
        return this.recordUniqueIdentifier;
    }

    public PaymentChannel recordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.setRecordUniqueIdentifier(recordUniqueIdentifier);
        return this;
    }

    public void setRecordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.recordUniqueIdentifier = recordUniqueIdentifier;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public PaymentChannel channelId(Integer channelId) {
        this.setChannelId(channelId);
        return this;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getChannelCode() {
        return this.channelCode;
    }

    public PaymentChannel channelCode(Integer channelCode) {
        this.setChannelCode(channelCode);
        return this;
    }

    public void setChannelCode(Integer channelCode) {
        this.channelCode = channelCode;
    }

    public PaymentChannel getChannelName() {
        return this.channelName;
    }

    public PaymentChannel channelName(PaymentChannel channelName) {
        this.setChannelName(channelName);
        return this;
    }

    public void setChannelName(PaymentChannel channelName) {
        this.channelName = channelName;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public PaymentChannel status(RecordStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public PaymentChannel freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public PaymentChannel freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public PaymentChannel freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public RecordStatus getIsDeleted() {
        return this.isDeleted;
    }

    public PaymentChannel isDeleted(RecordStatus isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(RecordStatus isDeleted) {
        this.isDeleted = isDeleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentChannel)) {
            return false;
        }
        return id != null && id.equals(((PaymentChannel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentChannel{" +
            "id=" + getId() +
            ", recordUniqueIdentifier='" + getRecordUniqueIdentifier() + "'" +
            ", channelId=" + getChannelId() +
            ", channelCode=" + getChannelCode() +
            ", channelName='" + getChannelName() + "'" +
            ", status='" + getStatus() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
