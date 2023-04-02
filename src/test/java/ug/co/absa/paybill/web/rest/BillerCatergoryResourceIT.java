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
import ug.co.absa.paybill.domain.BillerCatergory;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;
import ug.co.absa.paybill.repository.BillerCatergoryRepository;
import ug.co.absa.paybill.service.dto.BillerCatergoryDTO;
import ug.co.absa.paybill.service.mapper.BillerCatergoryMapper;

/**
 * Integration tests for the {@link BillerCatergoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BillerCatergoryResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_CATEGORY_ID = 1;
    private static final Integer UPDATED_CATEGORY_ID = 2;

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_DESCRIPTION = "BBBBBBBBBB";

    private static final RecordStatus DEFAULT_STATUS = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_STATUS = RecordStatus.INACTIVE;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String ENTITY_API_URL = "/api/biller-catergories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BillerCatergoryRepository billerCatergoryRepository;

    @Autowired
    private BillerCatergoryMapper billerCatergoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillerCatergoryMockMvc;

    private BillerCatergory billerCatergory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillerCatergory createEntity(EntityManager em) {
        BillerCatergory billerCatergory = new BillerCatergory()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .categoryId(DEFAULT_CATEGORY_ID)
            .categoryCode(DEFAULT_CATEGORY_CODE)
            .categoryName(DEFAULT_CATEGORY_NAME)
            .categoryDescription(DEFAULT_CATEGORY_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .isDeleted(DEFAULT_IS_DELETED);
        return billerCatergory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillerCatergory createUpdatedEntity(EntityManager em) {
        BillerCatergory billerCatergory = new BillerCatergory()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .categoryId(UPDATED_CATEGORY_ID)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        return billerCatergory;
    }

    @BeforeEach
    public void initTest() {
        billerCatergory = createEntity(em);
    }

    @Test
    @Transactional
    void createBillerCatergory() throws Exception {
        int databaseSizeBeforeCreate = billerCatergoryRepository.findAll().size();
        // Create the BillerCatergory
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);
        restBillerCatergoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeCreate + 1);
        BillerCatergory testBillerCatergory = billerCatergoryList.get(billerCatergoryList.size() - 1);
        assertThat(testBillerCatergory.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBillerCatergory.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testBillerCatergory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
        assertThat(testBillerCatergory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testBillerCatergory.getCategoryDescription()).isEqualTo(DEFAULT_CATEGORY_DESCRIPTION);
        assertThat(testBillerCatergory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBillerCatergory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testBillerCatergory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testBillerCatergory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testBillerCatergory.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createBillerCatergoryWithExistingId() throws Exception {
        // Create the BillerCatergory with an existing ID
        billerCatergory.setId(1L);
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        int databaseSizeBeforeCreate = billerCatergoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillerCatergoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerCatergoryRepository.findAll().size();
        // set the field null
        billerCatergory.setRecordUniqueIdentifier(null);

        // Create the BillerCatergory, which fails.
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        restBillerCatergoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerCatergoryRepository.findAll().size();
        // set the field null
        billerCatergory.setCategoryCode(null);

        // Create the BillerCatergory, which fails.
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        restBillerCatergoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerCatergoryRepository.findAll().size();
        // set the field null
        billerCatergory.setCategoryName(null);

        // Create the BillerCatergory, which fails.
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        restBillerCatergoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerCatergoryRepository.findAll().size();
        // set the field null
        billerCatergory.setCategoryDescription(null);

        // Create the BillerCatergory, which fails.
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        restBillerCatergoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = billerCatergoryRepository.findAll().size();
        // set the field null
        billerCatergory.setStatus(null);

        // Create the BillerCatergory, which fails.
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        restBillerCatergoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBillerCatergories() throws Exception {
        // Initialize the database
        billerCatergoryRepository.saveAndFlush(billerCatergory);

        // Get all the billerCatergoryList
        restBillerCatergoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billerCatergory.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getBillerCatergory() throws Exception {
        // Initialize the database
        billerCatergoryRepository.saveAndFlush(billerCatergory);

        // Get the billerCatergory
        restBillerCatergoryMockMvc
            .perform(get(ENTITY_API_URL_ID, billerCatergory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billerCatergory.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.categoryDescription").value(DEFAULT_CATEGORY_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBillerCatergory() throws Exception {
        // Get the billerCatergory
        restBillerCatergoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBillerCatergory() throws Exception {
        // Initialize the database
        billerCatergoryRepository.saveAndFlush(billerCatergory);

        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();

        // Update the billerCatergory
        BillerCatergory updatedBillerCatergory = billerCatergoryRepository.findById(billerCatergory.getId()).get();
        // Disconnect from session so that the updates on updatedBillerCatergory are not directly saved in db
        em.detach(updatedBillerCatergory);
        updatedBillerCatergory
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .categoryId(UPDATED_CATEGORY_ID)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(updatedBillerCatergory);

        restBillerCatergoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billerCatergoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
        BillerCatergory testBillerCatergory = billerCatergoryList.get(billerCatergoryList.size() - 1);
        assertThat(testBillerCatergory.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBillerCatergory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testBillerCatergory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testBillerCatergory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testBillerCatergory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
        assertThat(testBillerCatergory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBillerCatergory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBillerCatergory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBillerCatergory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testBillerCatergory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingBillerCatergory() throws Exception {
        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();
        billerCatergory.setId(count.incrementAndGet());

        // Create the BillerCatergory
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillerCatergoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billerCatergoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBillerCatergory() throws Exception {
        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();
        billerCatergory.setId(count.incrementAndGet());

        // Create the BillerCatergory
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerCatergoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBillerCatergory() throws Exception {
        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();
        billerCatergory.setId(count.incrementAndGet());

        // Create the BillerCatergory
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerCatergoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBillerCatergoryWithPatch() throws Exception {
        // Initialize the database
        billerCatergoryRepository.saveAndFlush(billerCatergory);

        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();

        // Update the billerCatergory using partial update
        BillerCatergory partialUpdatedBillerCatergory = new BillerCatergory();
        partialUpdatedBillerCatergory.setId(billerCatergory.getId());

        partialUpdatedBillerCatergory
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);

        restBillerCatergoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillerCatergory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillerCatergory))
            )
            .andExpect(status().isOk());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
        BillerCatergory testBillerCatergory = billerCatergoryList.get(billerCatergoryList.size() - 1);
        assertThat(testBillerCatergory.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBillerCatergory.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testBillerCatergory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testBillerCatergory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testBillerCatergory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
        assertThat(testBillerCatergory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBillerCatergory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBillerCatergory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testBillerCatergory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testBillerCatergory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateBillerCatergoryWithPatch() throws Exception {
        // Initialize the database
        billerCatergoryRepository.saveAndFlush(billerCatergory);

        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();

        // Update the billerCatergory using partial update
        BillerCatergory partialUpdatedBillerCatergory = new BillerCatergory();
        partialUpdatedBillerCatergory.setId(billerCatergory.getId());

        partialUpdatedBillerCatergory
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .categoryId(UPDATED_CATEGORY_ID)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);

        restBillerCatergoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillerCatergory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillerCatergory))
            )
            .andExpect(status().isOk());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
        BillerCatergory testBillerCatergory = billerCatergoryList.get(billerCatergoryList.size() - 1);
        assertThat(testBillerCatergory.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testBillerCatergory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testBillerCatergory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testBillerCatergory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testBillerCatergory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
        assertThat(testBillerCatergory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBillerCatergory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBillerCatergory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBillerCatergory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testBillerCatergory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingBillerCatergory() throws Exception {
        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();
        billerCatergory.setId(count.incrementAndGet());

        // Create the BillerCatergory
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillerCatergoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, billerCatergoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBillerCatergory() throws Exception {
        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();
        billerCatergory.setId(count.incrementAndGet());

        // Create the BillerCatergory
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerCatergoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBillerCatergory() throws Exception {
        int databaseSizeBeforeUpdate = billerCatergoryRepository.findAll().size();
        billerCatergory.setId(count.incrementAndGet());

        // Create the BillerCatergory
        BillerCatergoryDTO billerCatergoryDTO = billerCatergoryMapper.toDto(billerCatergory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillerCatergoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billerCatergoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BillerCatergory in the database
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBillerCatergory() throws Exception {
        // Initialize the database
        billerCatergoryRepository.saveAndFlush(billerCatergory);

        int databaseSizeBeforeDelete = billerCatergoryRepository.findAll().size();

        // Delete the billerCatergory
        restBillerCatergoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, billerCatergory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillerCatergory> billerCatergoryList = billerCatergoryRepository.findAll();
        assertThat(billerCatergoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
