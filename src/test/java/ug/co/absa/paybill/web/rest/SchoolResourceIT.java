package ug.co.absa.paybill.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
import ug.co.absa.paybill.domain.School;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;
import ug.co.absa.paybill.repository.SchoolRepository;
import ug.co.absa.paybill.service.dto.SchoolDTO;
import ug.co.absa.paybill.service.mapper.SchoolMapper;

/**
 * Integration tests for the {@link SchoolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchoolResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_SCHOOL_ID = 1;
    private static final Integer UPDATED_SCHOOL_ID = 2;

    private static final String DEFAULT_SCHOOL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_ALTERNATIVE_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_ALTERNATIVE_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOLEMAIL_ADDESS = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOLEMAIL_ADDESS = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBBBBBBB";

    private static final RecordStatus DEFAULT_STATUS = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_STATUS = RecordStatus.INACTIVE;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final Instant DEFAULT_REGISTRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_APPROVED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_IS_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IS_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/schools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolMockMvc;

    private School school;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .schoolId(DEFAULT_SCHOOL_ID)
            .schoolCode(DEFAULT_SCHOOL_CODE)
            .schoolPhoneNumber(DEFAULT_SCHOOL_PHONE_NUMBER)
            .schoolAlternativePhoneNumber(DEFAULT_SCHOOL_ALTERNATIVE_PHONE_NUMBER)
            .schoolemailAddess(DEFAULT_SCHOOLEMAIL_ADDESS)
            .schoolName(DEFAULT_SCHOOL_NAME)
            .status(DEFAULT_STATUS)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .approvedDate(DEFAULT_APPROVED_DATE)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .isDeleted(DEFAULT_IS_DELETED);
        return school;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createUpdatedEntity(EntityManager em) {
        School school = new School()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .schoolId(UPDATED_SCHOOL_ID)
            .schoolCode(UPDATED_SCHOOL_CODE)
            .schoolPhoneNumber(UPDATED_SCHOOL_PHONE_NUMBER)
            .schoolAlternativePhoneNumber(UPDATED_SCHOOL_ALTERNATIVE_PHONE_NUMBER)
            .schoolemailAddess(UPDATED_SCHOOLEMAIL_ADDESS)
            .schoolName(UPDATED_SCHOOL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .approvedDate(UPDATED_APPROVED_DATE)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        return school;
    }

    @BeforeEach
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();
        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);
        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testSchool.getSchoolId()).isEqualTo(DEFAULT_SCHOOL_ID);
        assertThat(testSchool.getSchoolCode()).isEqualTo(DEFAULT_SCHOOL_CODE);
        assertThat(testSchool.getSchoolPhoneNumber()).isEqualTo(DEFAULT_SCHOOL_PHONE_NUMBER);
        assertThat(testSchool.getSchoolAlternativePhoneNumber()).isEqualTo(DEFAULT_SCHOOL_ALTERNATIVE_PHONE_NUMBER);
        assertThat(testSchool.getSchoolemailAddess()).isEqualTo(DEFAULT_SCHOOLEMAIL_ADDESS);
        assertThat(testSchool.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testSchool.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSchool.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSchool.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testSchool.getApprovedDate()).isEqualTo(DEFAULT_APPROVED_DATE);
        assertThat(testSchool.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSchool.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testSchool.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createSchoolWithExistingId() throws Exception {
        // Create the School with an existing ID
        school.setId(1L);
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setRecordUniqueIdentifier(null);

        // Create the School, which fails.
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSchoolIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setSchoolId(null);

        // Create the School, which fails.
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSchoolCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setSchoolCode(null);

        // Create the School, which fails.
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSchoolNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setSchoolName(null);

        // Create the School, which fails.
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setStatus(null);

        // Create the School, which fails.
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].schoolId").value(hasItem(DEFAULT_SCHOOL_ID)))
            .andExpect(jsonPath("$.[*].schoolCode").value(hasItem(DEFAULT_SCHOOL_CODE)))
            .andExpect(jsonPath("$.[*].schoolPhoneNumber").value(hasItem(DEFAULT_SCHOOL_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].schoolAlternativePhoneNumber").value(hasItem(DEFAULT_SCHOOL_ALTERNATIVE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].schoolemailAddess").value(hasItem(DEFAULT_SCHOOLEMAIL_ADDESS)))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(DEFAULT_APPROVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.toString())));
    }

    @Test
    @Transactional
    void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL_ID, school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.schoolId").value(DEFAULT_SCHOOL_ID))
            .andExpect(jsonPath("$.schoolCode").value(DEFAULT_SCHOOL_CODE))
            .andExpect(jsonPath("$.schoolPhoneNumber").value(DEFAULT_SCHOOL_PHONE_NUMBER))
            .andExpect(jsonPath("$.schoolAlternativePhoneNumber").value(DEFAULT_SCHOOL_ALTERNATIVE_PHONE_NUMBER))
            .andExpect(jsonPath("$.schoolemailAddess").value(DEFAULT_SCHOOLEMAIL_ADDESS))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.approvedDate").value(DEFAULT_APPROVED_DATE.toString()))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).get();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .schoolId(UPDATED_SCHOOL_ID)
            .schoolCode(UPDATED_SCHOOL_CODE)
            .schoolPhoneNumber(UPDATED_SCHOOL_PHONE_NUMBER)
            .schoolAlternativePhoneNumber(UPDATED_SCHOOL_ALTERNATIVE_PHONE_NUMBER)
            .schoolemailAddess(UPDATED_SCHOOLEMAIL_ADDESS)
            .schoolName(UPDATED_SCHOOL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .approvedDate(UPDATED_APPROVED_DATE)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        SchoolDTO schoolDTO = schoolMapper.toDto(updatedSchool);

        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testSchool.getSchoolId()).isEqualTo(UPDATED_SCHOOL_ID);
        assertThat(testSchool.getSchoolCode()).isEqualTo(UPDATED_SCHOOL_CODE);
        assertThat(testSchool.getSchoolPhoneNumber()).isEqualTo(UPDATED_SCHOOL_PHONE_NUMBER);
        assertThat(testSchool.getSchoolAlternativePhoneNumber()).isEqualTo(UPDATED_SCHOOL_ALTERNATIVE_PHONE_NUMBER);
        assertThat(testSchool.getSchoolemailAddess()).isEqualTo(UPDATED_SCHOOLEMAIL_ADDESS);
        assertThat(testSchool.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testSchool.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSchool.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSchool.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testSchool.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);
        assertThat(testSchool.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSchool.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSchool.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .schoolId(UPDATED_SCHOOL_ID)
            .schoolPhoneNumber(UPDATED_SCHOOL_PHONE_NUMBER)
            .schoolemailAddess(UPDATED_SCHOOLEMAIL_ADDESS)
            .approvedDate(UPDATED_APPROVED_DATE)
            .freeField2(UPDATED_FREE_FIELD_2)
            .isDeleted(UPDATED_IS_DELETED);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testSchool.getSchoolId()).isEqualTo(UPDATED_SCHOOL_ID);
        assertThat(testSchool.getSchoolCode()).isEqualTo(DEFAULT_SCHOOL_CODE);
        assertThat(testSchool.getSchoolPhoneNumber()).isEqualTo(UPDATED_SCHOOL_PHONE_NUMBER);
        assertThat(testSchool.getSchoolAlternativePhoneNumber()).isEqualTo(DEFAULT_SCHOOL_ALTERNATIVE_PHONE_NUMBER);
        assertThat(testSchool.getSchoolemailAddess()).isEqualTo(UPDATED_SCHOOLEMAIL_ADDESS);
        assertThat(testSchool.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testSchool.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSchool.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSchool.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testSchool.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);
        assertThat(testSchool.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSchool.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testSchool.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .schoolId(UPDATED_SCHOOL_ID)
            .schoolCode(UPDATED_SCHOOL_CODE)
            .schoolPhoneNumber(UPDATED_SCHOOL_PHONE_NUMBER)
            .schoolAlternativePhoneNumber(UPDATED_SCHOOL_ALTERNATIVE_PHONE_NUMBER)
            .schoolemailAddess(UPDATED_SCHOOLEMAIL_ADDESS)
            .schoolName(UPDATED_SCHOOL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .approvedDate(UPDATED_APPROVED_DATE)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testSchool.getSchoolId()).isEqualTo(UPDATED_SCHOOL_ID);
        assertThat(testSchool.getSchoolCode()).isEqualTo(UPDATED_SCHOOL_CODE);
        assertThat(testSchool.getSchoolPhoneNumber()).isEqualTo(UPDATED_SCHOOL_PHONE_NUMBER);
        assertThat(testSchool.getSchoolAlternativePhoneNumber()).isEqualTo(UPDATED_SCHOOL_ALTERNATIVE_PHONE_NUMBER);
        assertThat(testSchool.getSchoolemailAddess()).isEqualTo(UPDATED_SCHOOLEMAIL_ADDESS);
        assertThat(testSchool.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testSchool.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSchool.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSchool.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testSchool.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);
        assertThat(testSchool.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSchool.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSchool.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schoolDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();
        school.setId(count.incrementAndGet());

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(schoolDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Delete the school
        restSchoolMockMvc
            .perform(delete(ENTITY_API_URL_ID, school.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
