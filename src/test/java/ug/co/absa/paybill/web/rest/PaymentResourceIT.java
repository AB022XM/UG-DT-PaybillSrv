package ug.co.absa.paybill.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.paybill.IntegrationTest;
import ug.co.absa.paybill.domain.Payment;
import ug.co.absa.paybill.domain.enumeration.PaymentChannel;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;
import ug.co.absa.paybill.repository.PaymentRepository;
import ug.co.absa.paybill.service.dto.PaymentDTO;
import ug.co.absa.paybill.service.mapper.PaymentMapper;

/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_PAYMENT_ID = 1;
    private static final Integer UPDATED_PAYMENT_ID = 2;

    private static final Integer DEFAULT_PAYMENT_CODE = 1;
    private static final Integer UPDATED_PAYMENT_CODE = 2;

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_FEE_AMOUNT = 1;
    private static final Integer UPDATED_FEE_AMOUNT = 2;

    private static final RecordStatus DEFAULT_IS_AMOUNT_FIXED = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_IS_AMOUNT_FIXED = RecordStatus.INACTIVE;

    private static final String DEFAULT_FEE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FEE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FIXED_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_FIXED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_OUTSTANDING_AMOUNT = 1;
    private static final Integer UPDATED_OUTSTANDING_AMOUNT = 2;

    private static final PaymentChannel DEFAULT_PAYMENT_CHANNEL = PaymentChannel.OVERTHECOUNTER;
    private static final PaymentChannel UPDATED_PAYMENT_CHANNEL = PaymentChannel.ABSAINTERNETBANKING;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(DEFAULT_PAYMENT_ID)
            .paymentCode(DEFAULT_PAYMENT_CODE)
            .customerId(DEFAULT_CUSTOMER_ID)
            .feeAmount(DEFAULT_FEE_AMOUNT)
            .isAmountFixed(DEFAULT_IS_AMOUNT_FIXED)
            .feeDescription(DEFAULT_FEE_DESCRIPTION)
            .fixedAmount(DEFAULT_FIXED_AMOUNT)
            .paymentName(DEFAULT_PAYMENT_NAME)
            .outstandingAmount(DEFAULT_OUTSTANDING_AMOUNT)
            .paymentChannel(DEFAULT_PAYMENT_CHANNEL)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return payment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .customerId(UPDATED_CUSTOMER_ID)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .isAmountFixed(UPDATED_IS_AMOUNT_FIXED)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .paymentName(UPDATED_PAYMENT_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPayment.getPaymentId()).isEqualTo(DEFAULT_PAYMENT_ID);
        assertThat(testPayment.getPaymentCode()).isEqualTo(DEFAULT_PAYMENT_CODE);
        assertThat(testPayment.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testPayment.getFeeAmount()).isEqualTo(DEFAULT_FEE_AMOUNT);
        assertThat(testPayment.getIsAmountFixed()).isEqualTo(DEFAULT_IS_AMOUNT_FIXED);
        assertThat(testPayment.getFeeDescription()).isEqualTo(DEFAULT_FEE_DESCRIPTION);
        assertThat(testPayment.getFixedAmount()).isEqualTo(DEFAULT_FIXED_AMOUNT);
        assertThat(testPayment.getPaymentName()).isEqualTo(DEFAULT_PAYMENT_NAME);
        assertThat(testPayment.getOutstandingAmount()).isEqualTo(DEFAULT_OUTSTANDING_AMOUNT);
        assertThat(testPayment.getPaymentChannel()).isEqualTo(DEFAULT_PAYMENT_CHANNEL);
        assertThat(testPayment.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPayment.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPayment.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createPaymentWithExistingId() throws Exception {
        // Create the Payment with an existing ID
        payment.setId(1L);
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setRecordUniqueIdentifier(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setPaymentId(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setPaymentCode(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFixedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setFixedAmount(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setPaymentName(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setPaymentChannel(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID)))
            .andExpect(jsonPath("$.[*].paymentCode").value(hasItem(DEFAULT_PAYMENT_CODE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].feeAmount").value(hasItem(DEFAULT_FEE_AMOUNT)))
            .andExpect(jsonPath("$.[*].isAmountFixed").value(hasItem(DEFAULT_IS_AMOUNT_FIXED.toString())))
            .andExpect(jsonPath("$.[*].feeDescription").value(hasItem(DEFAULT_FEE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fixedAmount").value(hasItem(DEFAULT_FIXED_AMOUNT)))
            .andExpect(jsonPath("$.[*].paymentName").value(hasItem(DEFAULT_PAYMENT_NAME)))
            .andExpect(jsonPath("$.[*].outstandingAmount").value(hasItem(DEFAULT_OUTSTANDING_AMOUNT)))
            .andExpect(jsonPath("$.[*].paymentChannel").value(hasItem(DEFAULT_PAYMENT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.paymentId").value(DEFAULT_PAYMENT_ID))
            .andExpect(jsonPath("$.paymentCode").value(DEFAULT_PAYMENT_CODE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.feeAmount").value(DEFAULT_FEE_AMOUNT))
            .andExpect(jsonPath("$.isAmountFixed").value(DEFAULT_IS_AMOUNT_FIXED.toString()))
            .andExpect(jsonPath("$.feeDescription").value(DEFAULT_FEE_DESCRIPTION))
            .andExpect(jsonPath("$.fixedAmount").value(DEFAULT_FIXED_AMOUNT))
            .andExpect(jsonPath("$.paymentName").value(DEFAULT_PAYMENT_NAME))
            .andExpect(jsonPath("$.outstandingAmount").value(DEFAULT_OUTSTANDING_AMOUNT))
            .andExpect(jsonPath("$.paymentChannel").value(DEFAULT_PAYMENT_CHANNEL.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .customerId(UPDATED_CUSTOMER_ID)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .isAmountFixed(UPDATED_IS_AMOUNT_FIXED)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .paymentName(UPDATED_PAYMENT_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        PaymentDTO paymentDTO = paymentMapper.toDto(updatedPayment);

        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPayment.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testPayment.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testPayment.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testPayment.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPayment.getIsAmountFixed()).isEqualTo(UPDATED_IS_AMOUNT_FIXED);
        assertThat(testPayment.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPayment.getFixedAmount()).isEqualTo(UPDATED_FIXED_AMOUNT);
        assertThat(testPayment.getPaymentName()).isEqualTo(UPDATED_PAYMENT_NAME);
        assertThat(testPayment.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPayment.getPaymentChannel()).isEqualTo(UPDATED_PAYMENT_CHANNEL);
        assertThat(testPayment.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPayment.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPayment.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .paymentId(UPDATED_PAYMENT_ID)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .freeField2(UPDATED_FREE_FIELD_2);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPayment.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testPayment.getPaymentCode()).isEqualTo(DEFAULT_PAYMENT_CODE);
        assertThat(testPayment.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testPayment.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPayment.getIsAmountFixed()).isEqualTo(DEFAULT_IS_AMOUNT_FIXED);
        assertThat(testPayment.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPayment.getFixedAmount()).isEqualTo(DEFAULT_FIXED_AMOUNT);
        assertThat(testPayment.getPaymentName()).isEqualTo(DEFAULT_PAYMENT_NAME);
        assertThat(testPayment.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPayment.getPaymentChannel()).isEqualTo(DEFAULT_PAYMENT_CHANNEL);
        assertThat(testPayment.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPayment.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPayment.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .customerId(UPDATED_CUSTOMER_ID)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .isAmountFixed(UPDATED_IS_AMOUNT_FIXED)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .paymentName(UPDATED_PAYMENT_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPayment.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testPayment.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testPayment.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testPayment.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPayment.getIsAmountFixed()).isEqualTo(UPDATED_IS_AMOUNT_FIXED);
        assertThat(testPayment.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPayment.getFixedAmount()).isEqualTo(UPDATED_FIXED_AMOUNT);
        assertThat(testPayment.getPaymentName()).isEqualTo(UPDATED_PAYMENT_NAME);
        assertThat(testPayment.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPayment.getPaymentChannel()).isEqualTo(UPDATED_PAYMENT_CHANNEL);
        assertThat(testPayment.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPayment.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPayment.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, payment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
