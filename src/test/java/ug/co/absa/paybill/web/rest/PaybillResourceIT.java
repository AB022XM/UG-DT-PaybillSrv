package ug.co.absa.paybill.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import ug.co.absa.paybill.domain.Paybill;
import ug.co.absa.paybill.domain.enumeration.PaymentChannel;
import ug.co.absa.paybill.repository.PaybillRepository;
import ug.co.absa.paybill.service.dto.PaybillDTO;
import ug.co.absa.paybill.service.mapper.PaybillMapper;

/**
 * Integration tests for the {@link PaybillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaybillResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final LocalDate DEFAULT_PROCESS_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROCESS_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_FEE_AMOUNT = 1;
    private static final Integer UPDATED_FEE_AMOUNT = 2;

    private static final String DEFAULT_FEE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FEE_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FEE_DUE_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FEE_DUE_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FEE_DUE_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FEE_DUE_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_OUTSTANDING_AMOUNT = 1;
    private static final Integer UPDATED_OUTSTANDING_AMOUNT = 2;

    private static final String DEFAULT_PAYMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_CLASS = "BBBBBBBBBB";

    private static final PaymentChannel DEFAULT_PAYMENT_CHANNEL = PaymentChannel.OVERTHECOUNTER;
    private static final PaymentChannel UPDATED_PAYMENT_CHANNEL = PaymentChannel.ABSAINTERNETBANKING;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paybills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaybillRepository paybillRepository;

    @Autowired
    private PaybillMapper paybillMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaybillMockMvc;

    private Paybill paybill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paybill createEntity(EntityManager em) {
        Paybill paybill = new Paybill()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .processTimestamp(DEFAULT_PROCESS_TIMESTAMP)
            .feeAmount(DEFAULT_FEE_AMOUNT)
            .feeDescription(DEFAULT_FEE_DESCRIPTION)
            .feeDueFromDate(DEFAULT_FEE_DUE_FROM_DATE)
            .feeDueToDate(DEFAULT_FEE_DUE_TO_DATE)
            .feeId(DEFAULT_FEE_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .outstandingAmount(DEFAULT_OUTSTANDING_AMOUNT)
            .paymentCode(DEFAULT_PAYMENT_CODE)
            .schoolCode(DEFAULT_SCHOOL_CODE)
            .schoolName(DEFAULT_SCHOOL_NAME)
            .studentClass(DEFAULT_STUDENT_CLASS)
            .paymentChannel(DEFAULT_PAYMENT_CHANNEL)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return paybill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paybill createUpdatedEntity(EntityManager em) {
        Paybill paybill = new Paybill()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .processTimestamp(UPDATED_PROCESS_TIMESTAMP)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .feeDueFromDate(UPDATED_FEE_DUE_FROM_DATE)
            .feeDueToDate(UPDATED_FEE_DUE_TO_DATE)
            .feeId(UPDATED_FEE_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .schoolCode(UPDATED_SCHOOL_CODE)
            .schoolName(UPDATED_SCHOOL_NAME)
            .studentClass(UPDATED_STUDENT_CLASS)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return paybill;
    }

    @BeforeEach
    public void initTest() {
        paybill = createEntity(em);
    }

    @Test
    @Transactional
    void createPaybill() throws Exception {
        int databaseSizeBeforeCreate = paybillRepository.findAll().size();
        // Create the Paybill
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);
        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isCreated());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeCreate + 1);
        Paybill testPaybill = paybillList.get(paybillList.size() - 1);
        assertThat(testPaybill.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaybill.getProcessTimestamp()).isEqualTo(DEFAULT_PROCESS_TIMESTAMP);
        assertThat(testPaybill.getFeeAmount()).isEqualTo(DEFAULT_FEE_AMOUNT);
        assertThat(testPaybill.getFeeDescription()).isEqualTo(DEFAULT_FEE_DESCRIPTION);
        assertThat(testPaybill.getFeeDueFromDate()).isEqualTo(DEFAULT_FEE_DUE_FROM_DATE);
        assertThat(testPaybill.getFeeDueToDate()).isEqualTo(DEFAULT_FEE_DUE_TO_DATE);
        assertThat(testPaybill.getFeeId()).isEqualTo(DEFAULT_FEE_ID);
        assertThat(testPaybill.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPaybill.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPaybill.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testPaybill.getOutstandingAmount()).isEqualTo(DEFAULT_OUTSTANDING_AMOUNT);
        assertThat(testPaybill.getPaymentCode()).isEqualTo(DEFAULT_PAYMENT_CODE);
        assertThat(testPaybill.getSchoolCode()).isEqualTo(DEFAULT_SCHOOL_CODE);
        assertThat(testPaybill.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testPaybill.getStudentClass()).isEqualTo(DEFAULT_STUDENT_CLASS);
        assertThat(testPaybill.getPaymentChannel()).isEqualTo(DEFAULT_PAYMENT_CHANNEL);
        assertThat(testPaybill.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPaybill.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPaybill.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createPaybillWithExistingId() throws Exception {
        // Create the Paybill with an existing ID
        paybill.setId(1L);
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        int databaseSizeBeforeCreate = paybillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setRecordUniqueIdentifier(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setFeeId(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setFirstName(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setLastName(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMiddleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setMiddleName(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setPaymentCode(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSchoolCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setSchoolCode(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSchoolNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setSchoolName(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStudentClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setStudentClass(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = paybillRepository.findAll().size();
        // set the field null
        paybill.setPaymentChannel(null);

        // Create the Paybill, which fails.
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        restPaybillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isBadRequest());

        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaybills() throws Exception {
        // Initialize the database
        paybillRepository.saveAndFlush(paybill);

        // Get all the paybillList
        restPaybillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paybill.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].processTimestamp").value(hasItem(DEFAULT_PROCESS_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].feeAmount").value(hasItem(DEFAULT_FEE_AMOUNT)))
            .andExpect(jsonPath("$.[*].feeDescription").value(hasItem(DEFAULT_FEE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].feeDueFromDate").value(hasItem(DEFAULT_FEE_DUE_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].feeDueToDate").value(hasItem(DEFAULT_FEE_DUE_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].feeId").value(hasItem(DEFAULT_FEE_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].outstandingAmount").value(hasItem(DEFAULT_OUTSTANDING_AMOUNT)))
            .andExpect(jsonPath("$.[*].paymentCode").value(hasItem(DEFAULT_PAYMENT_CODE)))
            .andExpect(jsonPath("$.[*].schoolCode").value(hasItem(DEFAULT_SCHOOL_CODE)))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME)))
            .andExpect(jsonPath("$.[*].studentClass").value(hasItem(DEFAULT_STUDENT_CLASS)))
            .andExpect(jsonPath("$.[*].paymentChannel").value(hasItem(DEFAULT_PAYMENT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getPaybill() throws Exception {
        // Initialize the database
        paybillRepository.saveAndFlush(paybill);

        // Get the paybill
        restPaybillMockMvc
            .perform(get(ENTITY_API_URL_ID, paybill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paybill.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.processTimestamp").value(DEFAULT_PROCESS_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.feeAmount").value(DEFAULT_FEE_AMOUNT))
            .andExpect(jsonPath("$.feeDescription").value(DEFAULT_FEE_DESCRIPTION))
            .andExpect(jsonPath("$.feeDueFromDate").value(DEFAULT_FEE_DUE_FROM_DATE.toString()))
            .andExpect(jsonPath("$.feeDueToDate").value(DEFAULT_FEE_DUE_TO_DATE.toString()))
            .andExpect(jsonPath("$.feeId").value(DEFAULT_FEE_ID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.outstandingAmount").value(DEFAULT_OUTSTANDING_AMOUNT))
            .andExpect(jsonPath("$.paymentCode").value(DEFAULT_PAYMENT_CODE))
            .andExpect(jsonPath("$.schoolCode").value(DEFAULT_SCHOOL_CODE))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME))
            .andExpect(jsonPath("$.studentClass").value(DEFAULT_STUDENT_CLASS))
            .andExpect(jsonPath("$.paymentChannel").value(DEFAULT_PAYMENT_CHANNEL.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getNonExistingPaybill() throws Exception {
        // Get the paybill
        restPaybillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaybill() throws Exception {
        // Initialize the database
        paybillRepository.saveAndFlush(paybill);

        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();

        // Update the paybill
        Paybill updatedPaybill = paybillRepository.findById(paybill.getId()).get();
        // Disconnect from session so that the updates on updatedPaybill are not directly saved in db
        em.detach(updatedPaybill);
        updatedPaybill
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .processTimestamp(UPDATED_PROCESS_TIMESTAMP)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .feeDueFromDate(UPDATED_FEE_DUE_FROM_DATE)
            .feeDueToDate(UPDATED_FEE_DUE_TO_DATE)
            .feeId(UPDATED_FEE_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .schoolCode(UPDATED_SCHOOL_CODE)
            .schoolName(UPDATED_SCHOOL_NAME)
            .studentClass(UPDATED_STUDENT_CLASS)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        PaybillDTO paybillDTO = paybillMapper.toDto(updatedPaybill);

        restPaybillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paybillDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paybillDTO))
            )
            .andExpect(status().isOk());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
        Paybill testPaybill = paybillList.get(paybillList.size() - 1);
        assertThat(testPaybill.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaybill.getProcessTimestamp()).isEqualTo(UPDATED_PROCESS_TIMESTAMP);
        assertThat(testPaybill.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPaybill.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPaybill.getFeeDueFromDate()).isEqualTo(UPDATED_FEE_DUE_FROM_DATE);
        assertThat(testPaybill.getFeeDueToDate()).isEqualTo(UPDATED_FEE_DUE_TO_DATE);
        assertThat(testPaybill.getFeeId()).isEqualTo(UPDATED_FEE_ID);
        assertThat(testPaybill.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPaybill.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPaybill.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPaybill.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPaybill.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testPaybill.getSchoolCode()).isEqualTo(UPDATED_SCHOOL_CODE);
        assertThat(testPaybill.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testPaybill.getStudentClass()).isEqualTo(UPDATED_STUDENT_CLASS);
        assertThat(testPaybill.getPaymentChannel()).isEqualTo(UPDATED_PAYMENT_CHANNEL);
        assertThat(testPaybill.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaybill.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaybill.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingPaybill() throws Exception {
        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();
        paybill.setId(count.incrementAndGet());

        // Create the Paybill
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaybillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paybillDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paybillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaybill() throws Exception {
        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();
        paybill.setId(count.incrementAndGet());

        // Create the Paybill
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaybillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paybillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaybill() throws Exception {
        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();
        paybill.setId(count.incrementAndGet());

        // Create the Paybill
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaybillMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paybillDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaybillWithPatch() throws Exception {
        // Initialize the database
        paybillRepository.saveAndFlush(paybill);

        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();

        // Update the paybill using partial update
        Paybill partialUpdatedPaybill = new Paybill();
        partialUpdatedPaybill.setId(paybill.getId());

        partialUpdatedPaybill
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .processTimestamp(UPDATED_PROCESS_TIMESTAMP)
            .feeDueFromDate(UPDATED_FEE_DUE_FROM_DATE)
            .feeDueToDate(UPDATED_FEE_DUE_TO_DATE)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .schoolCode(UPDATED_SCHOOL_CODE)
            .studentClass(UPDATED_STUDENT_CLASS)
            .freeField2(UPDATED_FREE_FIELD_2);

        restPaybillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaybill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaybill))
            )
            .andExpect(status().isOk());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
        Paybill testPaybill = paybillList.get(paybillList.size() - 1);
        assertThat(testPaybill.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaybill.getProcessTimestamp()).isEqualTo(UPDATED_PROCESS_TIMESTAMP);
        assertThat(testPaybill.getFeeAmount()).isEqualTo(DEFAULT_FEE_AMOUNT);
        assertThat(testPaybill.getFeeDescription()).isEqualTo(DEFAULT_FEE_DESCRIPTION);
        assertThat(testPaybill.getFeeDueFromDate()).isEqualTo(UPDATED_FEE_DUE_FROM_DATE);
        assertThat(testPaybill.getFeeDueToDate()).isEqualTo(UPDATED_FEE_DUE_TO_DATE);
        assertThat(testPaybill.getFeeId()).isEqualTo(DEFAULT_FEE_ID);
        assertThat(testPaybill.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPaybill.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPaybill.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPaybill.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPaybill.getPaymentCode()).isEqualTo(DEFAULT_PAYMENT_CODE);
        assertThat(testPaybill.getSchoolCode()).isEqualTo(UPDATED_SCHOOL_CODE);
        assertThat(testPaybill.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testPaybill.getStudentClass()).isEqualTo(UPDATED_STUDENT_CLASS);
        assertThat(testPaybill.getPaymentChannel()).isEqualTo(DEFAULT_PAYMENT_CHANNEL);
        assertThat(testPaybill.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPaybill.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaybill.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdatePaybillWithPatch() throws Exception {
        // Initialize the database
        paybillRepository.saveAndFlush(paybill);

        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();

        // Update the paybill using partial update
        Paybill partialUpdatedPaybill = new Paybill();
        partialUpdatedPaybill.setId(paybill.getId());

        partialUpdatedPaybill
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .processTimestamp(UPDATED_PROCESS_TIMESTAMP)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .feeDueFromDate(UPDATED_FEE_DUE_FROM_DATE)
            .feeDueToDate(UPDATED_FEE_DUE_TO_DATE)
            .feeId(UPDATED_FEE_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .schoolCode(UPDATED_SCHOOL_CODE)
            .schoolName(UPDATED_SCHOOL_NAME)
            .studentClass(UPDATED_STUDENT_CLASS)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restPaybillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaybill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaybill))
            )
            .andExpect(status().isOk());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
        Paybill testPaybill = paybillList.get(paybillList.size() - 1);
        assertThat(testPaybill.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaybill.getProcessTimestamp()).isEqualTo(UPDATED_PROCESS_TIMESTAMP);
        assertThat(testPaybill.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPaybill.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPaybill.getFeeDueFromDate()).isEqualTo(UPDATED_FEE_DUE_FROM_DATE);
        assertThat(testPaybill.getFeeDueToDate()).isEqualTo(UPDATED_FEE_DUE_TO_DATE);
        assertThat(testPaybill.getFeeId()).isEqualTo(UPDATED_FEE_ID);
        assertThat(testPaybill.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPaybill.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPaybill.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPaybill.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPaybill.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testPaybill.getSchoolCode()).isEqualTo(UPDATED_SCHOOL_CODE);
        assertThat(testPaybill.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testPaybill.getStudentClass()).isEqualTo(UPDATED_STUDENT_CLASS);
        assertThat(testPaybill.getPaymentChannel()).isEqualTo(UPDATED_PAYMENT_CHANNEL);
        assertThat(testPaybill.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaybill.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaybill.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingPaybill() throws Exception {
        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();
        paybill.setId(count.incrementAndGet());

        // Create the Paybill
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaybillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paybillDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paybillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaybill() throws Exception {
        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();
        paybill.setId(count.incrementAndGet());

        // Create the Paybill
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaybillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paybillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaybill() throws Exception {
        int databaseSizeBeforeUpdate = paybillRepository.findAll().size();
        paybill.setId(count.incrementAndGet());

        // Create the Paybill
        PaybillDTO paybillDTO = paybillMapper.toDto(paybill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaybillMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paybillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paybill in the database
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaybill() throws Exception {
        // Initialize the database
        paybillRepository.saveAndFlush(paybill);

        int databaseSizeBeforeDelete = paybillRepository.findAll().size();

        // Delete the paybill
        restPaybillMockMvc
            .perform(delete(ENTITY_API_URL_ID, paybill.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paybill> paybillList = paybillRepository.findAll();
        assertThat(paybillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
