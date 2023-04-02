package ug.co.absa.paybill.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
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
import ug.co.absa.paybill.domain.ValidateCustomerById;
import ug.co.absa.paybill.repository.ValidateCustomerByIdRepository;
import ug.co.absa.paybill.service.dto.ValidateCustomerByIdDTO;
import ug.co.absa.paybill.service.mapper.ValidateCustomerByIdMapper;

/**
 * Integration tests for the {@link ValidateCustomerByIdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ValidateCustomerByIdResourceIT {

    private static final Integer DEFAULT_CUSTOMNER_ID = 1;
    private static final Integer UPDATED_CUSTOMNER_ID = 2;

    private static final Integer DEFAULT_BILL_CODE = 1;
    private static final Integer UPDATED_BILL_CODE = 2;

    private static final String ENTITY_API_URL = "/api/validate-customer-by-ids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ValidateCustomerByIdRepository validateCustomerByIdRepository;

    @Autowired
    private ValidateCustomerByIdMapper validateCustomerByIdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restValidateCustomerByIdMockMvc;

    private ValidateCustomerById validateCustomerById;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidateCustomerById createEntity(EntityManager em) {
        ValidateCustomerById validateCustomerById = new ValidateCustomerById()
            .customnerId(DEFAULT_CUSTOMNER_ID)
            .billCode(DEFAULT_BILL_CODE);
        return validateCustomerById;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidateCustomerById createUpdatedEntity(EntityManager em) {
        ValidateCustomerById validateCustomerById = new ValidateCustomerById()
            .customnerId(UPDATED_CUSTOMNER_ID)
            .billCode(UPDATED_BILL_CODE);
        return validateCustomerById;
    }

    @BeforeEach
    public void initTest() {
        validateCustomerById = createEntity(em);
    }

    @Test
    @Transactional
    void createValidateCustomerById() throws Exception {
        int databaseSizeBeforeCreate = validateCustomerByIdRepository.findAll().size();
        // Create the ValidateCustomerById
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);
        restValidateCustomerByIdMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeCreate + 1);
        ValidateCustomerById testValidateCustomerById = validateCustomerByIdList.get(validateCustomerByIdList.size() - 1);
        assertThat(testValidateCustomerById.getCustomnerId()).isEqualTo(DEFAULT_CUSTOMNER_ID);
        assertThat(testValidateCustomerById.getBillCode()).isEqualTo(DEFAULT_BILL_CODE);
    }

    @Test
    @Transactional
    void createValidateCustomerByIdWithExistingId() throws Exception {
        // Create the ValidateCustomerById with an existing ID
        validateCustomerById.setId(1L);
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        int databaseSizeBeforeCreate = validateCustomerByIdRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidateCustomerByIdMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustomnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = validateCustomerByIdRepository.findAll().size();
        // set the field null
        validateCustomerById.setCustomnerId(null);

        // Create the ValidateCustomerById, which fails.
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        restValidateCustomerByIdMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isBadRequest());

        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = validateCustomerByIdRepository.findAll().size();
        // set the field null
        validateCustomerById.setBillCode(null);

        // Create the ValidateCustomerById, which fails.
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        restValidateCustomerByIdMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isBadRequest());

        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllValidateCustomerByIds() throws Exception {
        // Initialize the database
        validateCustomerByIdRepository.saveAndFlush(validateCustomerById);

        // Get all the validateCustomerByIdList
        restValidateCustomerByIdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validateCustomerById.getId().intValue())))
            .andExpect(jsonPath("$.[*].customnerId").value(hasItem(DEFAULT_CUSTOMNER_ID)))
            .andExpect(jsonPath("$.[*].billCode").value(hasItem(DEFAULT_BILL_CODE)));
    }

    @Test
    @Transactional
    void getValidateCustomerById() throws Exception {
        // Initialize the database
        validateCustomerByIdRepository.saveAndFlush(validateCustomerById);

        // Get the validateCustomerById
        restValidateCustomerByIdMockMvc
            .perform(get(ENTITY_API_URL_ID, validateCustomerById.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(validateCustomerById.getId().intValue()))
            .andExpect(jsonPath("$.customnerId").value(DEFAULT_CUSTOMNER_ID))
            .andExpect(jsonPath("$.billCode").value(DEFAULT_BILL_CODE));
    }

    @Test
    @Transactional
    void getNonExistingValidateCustomerById() throws Exception {
        // Get the validateCustomerById
        restValidateCustomerByIdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingValidateCustomerById() throws Exception {
        // Initialize the database
        validateCustomerByIdRepository.saveAndFlush(validateCustomerById);

        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();

        // Update the validateCustomerById
        ValidateCustomerById updatedValidateCustomerById = validateCustomerByIdRepository.findById(validateCustomerById.getId()).get();
        // Disconnect from session so that the updates on updatedValidateCustomerById are not directly saved in db
        em.detach(updatedValidateCustomerById);
        updatedValidateCustomerById.customnerId(UPDATED_CUSTOMNER_ID).billCode(UPDATED_BILL_CODE);
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(updatedValidateCustomerById);

        restValidateCustomerByIdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, validateCustomerByIdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isOk());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
        ValidateCustomerById testValidateCustomerById = validateCustomerByIdList.get(validateCustomerByIdList.size() - 1);
        assertThat(testValidateCustomerById.getCustomnerId()).isEqualTo(UPDATED_CUSTOMNER_ID);
        assertThat(testValidateCustomerById.getBillCode()).isEqualTo(UPDATED_BILL_CODE);
    }

    @Test
    @Transactional
    void putNonExistingValidateCustomerById() throws Exception {
        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();
        validateCustomerById.setId(count.incrementAndGet());

        // Create the ValidateCustomerById
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidateCustomerByIdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, validateCustomerByIdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchValidateCustomerById() throws Exception {
        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();
        validateCustomerById.setId(count.incrementAndGet());

        // Create the ValidateCustomerById
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidateCustomerByIdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamValidateCustomerById() throws Exception {
        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();
        validateCustomerById.setId(count.incrementAndGet());

        // Create the ValidateCustomerById
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidateCustomerByIdMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateValidateCustomerByIdWithPatch() throws Exception {
        // Initialize the database
        validateCustomerByIdRepository.saveAndFlush(validateCustomerById);

        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();

        // Update the validateCustomerById using partial update
        ValidateCustomerById partialUpdatedValidateCustomerById = new ValidateCustomerById();
        partialUpdatedValidateCustomerById.setId(validateCustomerById.getId());

        restValidateCustomerByIdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValidateCustomerById.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedValidateCustomerById))
            )
            .andExpect(status().isOk());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
        ValidateCustomerById testValidateCustomerById = validateCustomerByIdList.get(validateCustomerByIdList.size() - 1);
        assertThat(testValidateCustomerById.getCustomnerId()).isEqualTo(DEFAULT_CUSTOMNER_ID);
        assertThat(testValidateCustomerById.getBillCode()).isEqualTo(DEFAULT_BILL_CODE);
    }

    @Test
    @Transactional
    void fullUpdateValidateCustomerByIdWithPatch() throws Exception {
        // Initialize the database
        validateCustomerByIdRepository.saveAndFlush(validateCustomerById);

        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();

        // Update the validateCustomerById using partial update
        ValidateCustomerById partialUpdatedValidateCustomerById = new ValidateCustomerById();
        partialUpdatedValidateCustomerById.setId(validateCustomerById.getId());

        partialUpdatedValidateCustomerById.customnerId(UPDATED_CUSTOMNER_ID).billCode(UPDATED_BILL_CODE);

        restValidateCustomerByIdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValidateCustomerById.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedValidateCustomerById))
            )
            .andExpect(status().isOk());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
        ValidateCustomerById testValidateCustomerById = validateCustomerByIdList.get(validateCustomerByIdList.size() - 1);
        assertThat(testValidateCustomerById.getCustomnerId()).isEqualTo(UPDATED_CUSTOMNER_ID);
        assertThat(testValidateCustomerById.getBillCode()).isEqualTo(UPDATED_BILL_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingValidateCustomerById() throws Exception {
        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();
        validateCustomerById.setId(count.incrementAndGet());

        // Create the ValidateCustomerById
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidateCustomerByIdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, validateCustomerByIdDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchValidateCustomerById() throws Exception {
        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();
        validateCustomerById.setId(count.incrementAndGet());

        // Create the ValidateCustomerById
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidateCustomerByIdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamValidateCustomerById() throws Exception {
        int databaseSizeBeforeUpdate = validateCustomerByIdRepository.findAll().size();
        validateCustomerById.setId(count.incrementAndGet());

        // Create the ValidateCustomerById
        ValidateCustomerByIdDTO validateCustomerByIdDTO = validateCustomerByIdMapper.toDto(validateCustomerById);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidateCustomerByIdMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(validateCustomerByIdDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValidateCustomerById in the database
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteValidateCustomerById() throws Exception {
        // Initialize the database
        validateCustomerByIdRepository.saveAndFlush(validateCustomerById);

        int databaseSizeBeforeDelete = validateCustomerByIdRepository.findAll().size();

        // Delete the validateCustomerById
        restValidateCustomerByIdMockMvc
            .perform(delete(ENTITY_API_URL_ID, validateCustomerById.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ValidateCustomerById> validateCustomerByIdList = validateCustomerByIdRepository.findAll();
        assertThat(validateCustomerByIdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
