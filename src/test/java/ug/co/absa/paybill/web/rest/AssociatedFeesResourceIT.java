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
import ug.co.absa.paybill.domain.AssociatedFees;
import ug.co.absa.paybill.repository.AssociatedFeesRepository;
import ug.co.absa.paybill.service.dto.AssociatedFeesDTO;
import ug.co.absa.paybill.service.mapper.AssociatedFeesMapper;

/**
 * Integration tests for the {@link AssociatedFeesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssociatedFeesResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_FEE_ID = 1;
    private static final Integer UPDATED_FEE_ID = 2;

    private static final String DEFAULT_FEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FEE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FEE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FEE_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_IS_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IS_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/associated-fees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssociatedFeesRepository associatedFeesRepository;

    @Autowired
    private AssociatedFeesMapper associatedFeesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssociatedFeesMockMvc;

    private AssociatedFees associatedFees;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssociatedFees createEntity(EntityManager em) {
        AssociatedFees associatedFees = new AssociatedFees()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .feeId(DEFAULT_FEE_ID)
            .feeCode(DEFAULT_FEE_CODE)
            .feeDescription(DEFAULT_FEE_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .isDeleted(DEFAULT_IS_DELETED);
        return associatedFees;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssociatedFees createUpdatedEntity(EntityManager em) {
        AssociatedFees associatedFees = new AssociatedFees()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .feeId(UPDATED_FEE_ID)
            .feeCode(UPDATED_FEE_CODE)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDeleted(UPDATED_IS_DELETED);
        return associatedFees;
    }

    @BeforeEach
    public void initTest() {
        associatedFees = createEntity(em);
    }

    @Test
    @Transactional
    void createAssociatedFees() throws Exception {
        int databaseSizeBeforeCreate = associatedFeesRepository.findAll().size();
        // Create the AssociatedFees
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);
        restAssociatedFeesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeCreate + 1);
        AssociatedFees testAssociatedFees = associatedFeesList.get(associatedFeesList.size() - 1);
        assertThat(testAssociatedFees.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testAssociatedFees.getFeeId()).isEqualTo(DEFAULT_FEE_ID);
        assertThat(testAssociatedFees.getFeeCode()).isEqualTo(DEFAULT_FEE_CODE);
        assertThat(testAssociatedFees.getFeeDescription()).isEqualTo(DEFAULT_FEE_DESCRIPTION);
        assertThat(testAssociatedFees.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssociatedFees.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAssociatedFees.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAssociatedFees.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAssociatedFees.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAssociatedFees.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testAssociatedFees.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createAssociatedFeesWithExistingId() throws Exception {
        // Create the AssociatedFees with an existing ID
        associatedFees.setId(1L);
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        int databaseSizeBeforeCreate = associatedFeesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssociatedFeesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = associatedFeesRepository.findAll().size();
        // set the field null
        associatedFees.setRecordUniqueIdentifier(null);

        // Create the AssociatedFees, which fails.
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        restAssociatedFeesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = associatedFeesRepository.findAll().size();
        // set the field null
        associatedFees.setFeeId(null);

        // Create the AssociatedFees, which fails.
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        restAssociatedFeesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = associatedFeesRepository.findAll().size();
        // set the field null
        associatedFees.setFeeCode(null);

        // Create the AssociatedFees, which fails.
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        restAssociatedFeesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = associatedFeesRepository.findAll().size();
        // set the field null
        associatedFees.setStatus(null);

        // Create the AssociatedFees, which fails.
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        restAssociatedFeesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssociatedFees() throws Exception {
        // Initialize the database
        associatedFeesRepository.saveAndFlush(associatedFees);

        // Get all the associatedFeesList
        restAssociatedFeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(associatedFees.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].feeId").value(hasItem(DEFAULT_FEE_ID)))
            .andExpect(jsonPath("$.[*].feeCode").value(hasItem(DEFAULT_FEE_CODE)))
            .andExpect(jsonPath("$.[*].feeDescription").value(hasItem(DEFAULT_FEE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.toString())));
    }

    @Test
    @Transactional
    void getAssociatedFees() throws Exception {
        // Initialize the database
        associatedFeesRepository.saveAndFlush(associatedFees);

        // Get the associatedFees
        restAssociatedFeesMockMvc
            .perform(get(ENTITY_API_URL_ID, associatedFees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(associatedFees.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.feeId").value(DEFAULT_FEE_ID))
            .andExpect(jsonPath("$.feeCode").value(DEFAULT_FEE_CODE))
            .andExpect(jsonPath("$.feeDescription").value(DEFAULT_FEE_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAssociatedFees() throws Exception {
        // Get the associatedFees
        restAssociatedFeesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssociatedFees() throws Exception {
        // Initialize the database
        associatedFeesRepository.saveAndFlush(associatedFees);

        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();

        // Update the associatedFees
        AssociatedFees updatedAssociatedFees = associatedFeesRepository.findById(associatedFees.getId()).get();
        // Disconnect from session so that the updates on updatedAssociatedFees are not directly saved in db
        em.detach(updatedAssociatedFees);
        updatedAssociatedFees
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .feeId(UPDATED_FEE_ID)
            .feeCode(UPDATED_FEE_CODE)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDeleted(UPDATED_IS_DELETED);
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(updatedAssociatedFees);

        restAssociatedFeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, associatedFeesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
        AssociatedFees testAssociatedFees = associatedFeesList.get(associatedFeesList.size() - 1);
        assertThat(testAssociatedFees.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testAssociatedFees.getFeeId()).isEqualTo(UPDATED_FEE_ID);
        assertThat(testAssociatedFees.getFeeCode()).isEqualTo(UPDATED_FEE_CODE);
        assertThat(testAssociatedFees.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testAssociatedFees.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssociatedFees.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAssociatedFees.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAssociatedFees.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAssociatedFees.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAssociatedFees.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAssociatedFees.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingAssociatedFees() throws Exception {
        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();
        associatedFees.setId(count.incrementAndGet());

        // Create the AssociatedFees
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssociatedFeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, associatedFeesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssociatedFees() throws Exception {
        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();
        associatedFees.setId(count.incrementAndGet());

        // Create the AssociatedFees
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociatedFeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssociatedFees() throws Exception {
        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();
        associatedFees.setId(count.incrementAndGet());

        // Create the AssociatedFees
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociatedFeesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssociatedFeesWithPatch() throws Exception {
        // Initialize the database
        associatedFeesRepository.saveAndFlush(associatedFees);

        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();

        // Update the associatedFees using partial update
        AssociatedFees partialUpdatedAssociatedFees = new AssociatedFees();
        partialUpdatedAssociatedFees.setId(associatedFees.getId());

        partialUpdatedAssociatedFees
            .feeId(UPDATED_FEE_ID)
            .feeCode(UPDATED_FEE_CODE)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDeleted(UPDATED_IS_DELETED);

        restAssociatedFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssociatedFees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssociatedFees))
            )
            .andExpect(status().isOk());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
        AssociatedFees testAssociatedFees = associatedFeesList.get(associatedFeesList.size() - 1);
        assertThat(testAssociatedFees.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testAssociatedFees.getFeeId()).isEqualTo(UPDATED_FEE_ID);
        assertThat(testAssociatedFees.getFeeCode()).isEqualTo(UPDATED_FEE_CODE);
        assertThat(testAssociatedFees.getFeeDescription()).isEqualTo(DEFAULT_FEE_DESCRIPTION);
        assertThat(testAssociatedFees.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssociatedFees.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAssociatedFees.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAssociatedFees.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAssociatedFees.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAssociatedFees.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAssociatedFees.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateAssociatedFeesWithPatch() throws Exception {
        // Initialize the database
        associatedFeesRepository.saveAndFlush(associatedFees);

        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();

        // Update the associatedFees using partial update
        AssociatedFees partialUpdatedAssociatedFees = new AssociatedFees();
        partialUpdatedAssociatedFees.setId(associatedFees.getId());

        partialUpdatedAssociatedFees
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .feeId(UPDATED_FEE_ID)
            .feeCode(UPDATED_FEE_CODE)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDeleted(UPDATED_IS_DELETED);

        restAssociatedFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssociatedFees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssociatedFees))
            )
            .andExpect(status().isOk());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
        AssociatedFees testAssociatedFees = associatedFeesList.get(associatedFeesList.size() - 1);
        assertThat(testAssociatedFees.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testAssociatedFees.getFeeId()).isEqualTo(UPDATED_FEE_ID);
        assertThat(testAssociatedFees.getFeeCode()).isEqualTo(UPDATED_FEE_CODE);
        assertThat(testAssociatedFees.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testAssociatedFees.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssociatedFees.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAssociatedFees.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAssociatedFees.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAssociatedFees.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAssociatedFees.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAssociatedFees.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingAssociatedFees() throws Exception {
        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();
        associatedFees.setId(count.incrementAndGet());

        // Create the AssociatedFees
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssociatedFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, associatedFeesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssociatedFees() throws Exception {
        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();
        associatedFees.setId(count.incrementAndGet());

        // Create the AssociatedFees
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociatedFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssociatedFees() throws Exception {
        int databaseSizeBeforeUpdate = associatedFeesRepository.findAll().size();
        associatedFees.setId(count.incrementAndGet());

        // Create the AssociatedFees
        AssociatedFeesDTO associatedFeesDTO = associatedFeesMapper.toDto(associatedFees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociatedFeesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(associatedFeesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssociatedFees in the database
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssociatedFees() throws Exception {
        // Initialize the database
        associatedFeesRepository.saveAndFlush(associatedFees);

        int databaseSizeBeforeDelete = associatedFeesRepository.findAll().size();

        // Delete the associatedFees
        restAssociatedFeesMockMvc
            .perform(delete(ENTITY_API_URL_ID, associatedFees.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssociatedFees> associatedFeesList = associatedFeesRepository.findAll();
        assertThat(associatedFeesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
