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
import ug.co.absa.paybill.domain.Biller;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;
import ug.co.absa.paybill.repository.BillerRepository;
import ug.co.absa.paybill.service.dto.BillerDTO;
import ug.co.absa.paybill.service.mapper.BillerMapper;

/**
 * Integration tests for the {@link BillerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BillerResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_BILLER_ID = 1;
    private static final Integer UPDATED_BILLER_ID = 2;

    private static final Integer DEFAULT_BILLER_CODE = 1;
    private static final Integer UPDATED_BILLER_CODE = 2;

    private static final Integer DEFAULT_CATEGORY_ID = 1;
    private static final Integer UPDATED_CATEGORY_ID = 2;

    private static final Integer DEFAULT_CONTACT_ID = 1;
    private static final Integer UPDATED_CONTACT_ID = 2;

    private static final Integer DEFAULT_ADDRESS_ID = 1;
    private static final Integer UPDATED_ADDRESS_ID = 2;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OUT_STANDING_AMOUNT = "AAAAAAAA";
    private static final String UPDATED_OUT_STANDING_AMOUNT = "BBBBBBBB";

    private static final RecordStatus DEFAULT_STATUS = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_STATUS = RecordStatus.INACTIVE;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_IS_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IS_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/billers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BillerRepository billerRepository;

    @Autowired
    private BillerMapper billerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillerMockMvc;

    private Biller biller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biller createEntity(EntityManager em) {
        Biller biller = new Biller()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .billerId(DEFAULT_BILLER_ID)
            .billerCode(DEFAULT_BILLER_CODE)
            .categoryId(DEFAULT_CATEGORY_ID)
            .contactId(DEFAULT_CONTACT_ID)
            .addressId(DEFAULT_ADDRESS_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .outStandingAmount(DEFAULT_OUT_STANDING_AMOUNT)
            .status(DEFAULT_STATUS)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .isDeleted(DEFAULT_IS_DELETED);
        return biller;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biller createUpdatedEntity(EntityManager em) {
        Biller biller = new Biller()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .billerId(UPDATED_BILLER_ID)
            .billerCode(UPDATED_BILLER_CODE)
            .categoryId(UPDATED_CATEGORY_ID)
            .contactId(UPDATED_CONTACT_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .outStandingAmount(UPDATED_OUT_STANDING_AMOUNT)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        return biller;
    }

    @BeforeEach
    public void initTest() {
        biller = createEntity(em);
    }

    @Test
    @Transactional
    void createBiller() throws Exception {
        int databaseSizeBeforeCreate = billerRepository.findAll().size();
        // Create the Biller
        BillerDTO billerDTO = billerMapper.toDto(biller);
        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isCreated());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeCreate + 1);
        Biller testBiller = billerList.get(billerList.size() - 1);
        assertThat(testBiller.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBiller.getBillerId()).isEqualTo(DEFAULT_BILLER_ID);
        assertThat(testBiller.getBillerCode()).isEqualTo(DEFAULT_BILLER_CODE);
        assertThat(testBiller.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testBiller.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testBiller.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testBiller.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBiller.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testBiller.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBiller.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testBiller.getOutStandingAmount()).isEqualTo(DEFAULT_OUT_STANDING_AMOUNT);
        assertThat(testBiller.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBiller.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testBiller.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testBiller.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testBiller.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createBillerWithExistingId() throws Exception {
        // Create the Biller with an existing ID
        biller.setId(1L);
        BillerDTO billerDTO = billerMapper.toDto(biller);

        int databaseSizeBeforeCreate = billerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setRecordUniqueIdentifier(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setBillerCode(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setCategoryId(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setContactId(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setAddressId(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setFirstName(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMiddleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setMiddleName(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setLastName(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setDateOfBirth(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOutStandingAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setOutStandingAmount(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerRepository.findAll().size();
        // set the field null
        biller.setStatus(null);

        // Create the Biller, which fails.
        BillerDTO billerDTO = billerMapper.toDto(biller);

        restBillerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isBadRequest());

        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBillers() throws Exception {
        // Initialize the database
        billerRepository.saveAndFlush(biller);

        // Get all the billerList
        restBillerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biller.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].billerId").value(hasItem(DEFAULT_BILLER_ID)))
            .andExpect(jsonPath("$.[*].billerCode").value(hasItem(DEFAULT_BILLER_CODE)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID)))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].outStandingAmount").value(hasItem(DEFAULT_OUT_STANDING_AMOUNT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.toString())));
    }

    @Test
    @Transactional
    void getBiller() throws Exception {
        // Initialize the database
        billerRepository.saveAndFlush(biller);

        // Get the biller
        restBillerMockMvc
            .perform(get(ENTITY_API_URL_ID, biller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(biller.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.billerId").value(DEFAULT_BILLER_ID))
            .andExpect(jsonPath("$.billerCode").value(DEFAULT_BILLER_CODE))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.outStandingAmount").value(DEFAULT_OUT_STANDING_AMOUNT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBiller() throws Exception {
        // Get the biller
        restBillerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBiller() throws Exception {
        // Initialize the database
        billerRepository.saveAndFlush(biller);

        int databaseSizeBeforeUpdate = billerRepository.findAll().size();

        // Update the biller
        Biller updatedBiller = billerRepository.findById(biller.getId()).get();
        // Disconnect from session so that the updates on updatedBiller are not directly saved in db
        em.detach(updatedBiller);
        updatedBiller
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .billerId(UPDATED_BILLER_ID)
            .billerCode(UPDATED_BILLER_CODE)
            .categoryId(UPDATED_CATEGORY_ID)
            .contactId(UPDATED_CONTACT_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .outStandingAmount(UPDATED_OUT_STANDING_AMOUNT)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        BillerDTO billerDTO = billerMapper.toDto(updatedBiller);

        restBillerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
        Biller testBiller = billerList.get(billerList.size() - 1);
        assertThat(testBiller.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBiller.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testBiller.getBillerCode()).isEqualTo(UPDATED_BILLER_CODE);
        assertThat(testBiller.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testBiller.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testBiller.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testBiller.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBiller.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testBiller.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBiller.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testBiller.getOutStandingAmount()).isEqualTo(UPDATED_OUT_STANDING_AMOUNT);
        assertThat(testBiller.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBiller.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBiller.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBiller.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testBiller.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingBiller() throws Exception {
        int databaseSizeBeforeUpdate = billerRepository.findAll().size();
        biller.setId(count.incrementAndGet());

        // Create the Biller
        BillerDTO billerDTO = billerMapper.toDto(biller);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBiller() throws Exception {
        int databaseSizeBeforeUpdate = billerRepository.findAll().size();
        biller.setId(count.incrementAndGet());

        // Create the Biller
        BillerDTO billerDTO = billerMapper.toDto(biller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBiller() throws Exception {
        int databaseSizeBeforeUpdate = billerRepository.findAll().size();
        biller.setId(count.incrementAndGet());

        // Create the Biller
        BillerDTO billerDTO = billerMapper.toDto(biller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBillerWithPatch() throws Exception {
        // Initialize the database
        billerRepository.saveAndFlush(biller);

        int databaseSizeBeforeUpdate = billerRepository.findAll().size();

        // Update the biller using partial update
        Biller partialUpdatedBiller = new Biller();
        partialUpdatedBiller.setId(biller.getId());

        partialUpdatedBiller
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .billerId(UPDATED_BILLER_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .outStandingAmount(UPDATED_OUT_STANDING_AMOUNT)
            .freeField2(UPDATED_FREE_FIELD_2)
            .isDeleted(UPDATED_IS_DELETED);

        restBillerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBiller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBiller))
            )
            .andExpect(status().isOk());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
        Biller testBiller = billerList.get(billerList.size() - 1);
        assertThat(testBiller.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBiller.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testBiller.getBillerCode()).isEqualTo(DEFAULT_BILLER_CODE);
        assertThat(testBiller.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testBiller.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testBiller.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testBiller.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBiller.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testBiller.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBiller.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testBiller.getOutStandingAmount()).isEqualTo(UPDATED_OUT_STANDING_AMOUNT);
        assertThat(testBiller.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBiller.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testBiller.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBiller.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testBiller.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateBillerWithPatch() throws Exception {
        // Initialize the database
        billerRepository.saveAndFlush(biller);

        int databaseSizeBeforeUpdate = billerRepository.findAll().size();

        // Update the biller using partial update
        Biller partialUpdatedBiller = new Biller();
        partialUpdatedBiller.setId(biller.getId());

        partialUpdatedBiller
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .billerId(UPDATED_BILLER_ID)
            .billerCode(UPDATED_BILLER_CODE)
            .categoryId(UPDATED_CATEGORY_ID)
            .contactId(UPDATED_CONTACT_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .outStandingAmount(UPDATED_OUT_STANDING_AMOUNT)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);

        restBillerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBiller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBiller))
            )
            .andExpect(status().isOk());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
        Biller testBiller = billerList.get(billerList.size() - 1);
        assertThat(testBiller.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBiller.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testBiller.getBillerCode()).isEqualTo(UPDATED_BILLER_CODE);
        assertThat(testBiller.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testBiller.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testBiller.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testBiller.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBiller.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testBiller.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBiller.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testBiller.getOutStandingAmount()).isEqualTo(UPDATED_OUT_STANDING_AMOUNT);
        assertThat(testBiller.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBiller.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBiller.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBiller.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testBiller.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingBiller() throws Exception {
        int databaseSizeBeforeUpdate = billerRepository.findAll().size();
        biller.setId(count.incrementAndGet());

        // Create the Biller
        BillerDTO billerDTO = billerMapper.toDto(biller);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, billerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBiller() throws Exception {
        int databaseSizeBeforeUpdate = billerRepository.findAll().size();
        biller.setId(count.incrementAndGet());

        // Create the Biller
        BillerDTO billerDTO = billerMapper.toDto(biller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBiller() throws Exception {
        int databaseSizeBeforeUpdate = billerRepository.findAll().size();
        biller.setId(count.incrementAndGet());

        // Create the Biller
        BillerDTO billerDTO = billerMapper.toDto(biller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(billerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Biller in the database
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBiller() throws Exception {
        // Initialize the database
        billerRepository.saveAndFlush(biller);

        int databaseSizeBeforeDelete = billerRepository.findAll().size();

        // Delete the biller
        restBillerMockMvc
            .perform(delete(ENTITY_API_URL_ID, biller.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Biller> billerList = billerRepository.findAll();
        assertThat(billerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
