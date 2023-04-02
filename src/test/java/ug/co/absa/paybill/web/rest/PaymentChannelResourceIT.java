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
import ug.co.absa.paybill.domain.PaymentChannel;
import ug.co.absa.paybill.domain.enumeration.PaymentChannel;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;
import ug.co.absa.paybill.domain.enumeration.RecordStatus;
import ug.co.absa.paybill.repository.PaymentChannelRepository;
import ug.co.absa.paybill.service.dto.PaymentChannelDTO;
import ug.co.absa.paybill.service.mapper.PaymentChannelMapper;

/**
 * Integration tests for the {@link PaymentChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentChannelResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_CHANNEL_ID = 1;
    private static final Integer UPDATED_CHANNEL_ID = 2;

    private static final Integer DEFAULT_CHANNEL_CODE = 1;
    private static final Integer UPDATED_CHANNEL_CODE = 2;

    private static final PaymentChannel DEFAULT_CHANNEL_NAME = PaymentChannel.OVERTHECOUNTER;
    private static final PaymentChannel UPDATED_CHANNEL_NAME = PaymentChannel.ABSAINTERNETBANKING;

    private static final RecordStatus DEFAULT_STATUS = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_STATUS = RecordStatus.INACTIVE;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final RecordStatus DEFAULT_IS_DELETED = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_IS_DELETED = RecordStatus.INACTIVE;

    private static final String ENTITY_API_URL = "/api/payment-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentChannelRepository paymentChannelRepository;

    @Autowired
    private PaymentChannelMapper paymentChannelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentChannelMockMvc;

    private PaymentChannel paymentChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentChannel createEntity(EntityManager em) {
        PaymentChannel paymentChannel = new PaymentChannel()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .channelId(DEFAULT_CHANNEL_ID)
            .channelCode(DEFAULT_CHANNEL_CODE)
            .channelName(DEFAULT_CHANNEL_NAME)
            .status(DEFAULT_STATUS)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .isDeleted(DEFAULT_IS_DELETED);
        return paymentChannel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentChannel createUpdatedEntity(EntityManager em) {
        PaymentChannel paymentChannel = new PaymentChannel()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .channelId(UPDATED_CHANNEL_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelName(UPDATED_CHANNEL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        return paymentChannel;
    }

    @BeforeEach
    public void initTest() {
        paymentChannel = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentChannel() throws Exception {
        int databaseSizeBeforeCreate = paymentChannelRepository.findAll().size();
        // Create the PaymentChannel
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);
        restPaymentChannelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentChannel testPaymentChannel = paymentChannelList.get(paymentChannelList.size() - 1);
        assertThat(testPaymentChannel.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannel.getChannelId()).isEqualTo(DEFAULT_CHANNEL_ID);
        assertThat(testPaymentChannel.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testPaymentChannel.getChannelName()).isEqualTo(DEFAULT_CHANNEL_NAME);
        assertThat(testPaymentChannel.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentChannel.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPaymentChannel.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPaymentChannel.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testPaymentChannel.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createPaymentChannelWithExistingId() throws Exception {
        // Create the PaymentChannel with an existing ID
        paymentChannel.setId(1L);
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        int databaseSizeBeforeCreate = paymentChannelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentChannelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentChannelRepository.findAll().size();
        // set the field null
        paymentChannel.setRecordUniqueIdentifier(null);

        // Create the PaymentChannel, which fails.
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        restPaymentChannelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChannelIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentChannelRepository.findAll().size();
        // set the field null
        paymentChannel.setChannelId(null);

        // Create the PaymentChannel, which fails.
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        restPaymentChannelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChannelNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentChannelRepository.findAll().size();
        // set the field null
        paymentChannel.setChannelName(null);

        // Create the PaymentChannel, which fails.
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        restPaymentChannelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentChannels() throws Exception {
        // Initialize the database
        paymentChannelRepository.saveAndFlush(paymentChannel);

        // Get all the paymentChannelList
        restPaymentChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].channelId").value(hasItem(DEFAULT_CHANNEL_ID)))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE)))
            .andExpect(jsonPath("$.[*].channelName").value(hasItem(DEFAULT_CHANNEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.toString())));
    }

    @Test
    @Transactional
    void getPaymentChannel() throws Exception {
        // Initialize the database
        paymentChannelRepository.saveAndFlush(paymentChannel);

        // Get the paymentChannel
        restPaymentChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentChannel.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.channelId").value(DEFAULT_CHANNEL_ID))
            .andExpect(jsonPath("$.channelCode").value(DEFAULT_CHANNEL_CODE))
            .andExpect(jsonPath("$.channelName").value(DEFAULT_CHANNEL_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentChannel() throws Exception {
        // Get the paymentChannel
        restPaymentChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentChannel() throws Exception {
        // Initialize the database
        paymentChannelRepository.saveAndFlush(paymentChannel);

        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();

        // Update the paymentChannel
        PaymentChannel updatedPaymentChannel = paymentChannelRepository.findById(paymentChannel.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentChannel are not directly saved in db
        em.detach(updatedPaymentChannel);
        updatedPaymentChannel
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .channelId(UPDATED_CHANNEL_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelName(UPDATED_CHANNEL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(updatedPaymentChannel);

        restPaymentChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentChannelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
        PaymentChannel testPaymentChannel = paymentChannelList.get(paymentChannelList.size() - 1);
        assertThat(testPaymentChannel.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannel.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testPaymentChannel.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testPaymentChannel.getChannelName()).isEqualTo(UPDATED_CHANNEL_NAME);
        assertThat(testPaymentChannel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentChannel.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentChannel.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaymentChannel.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPaymentChannel.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingPaymentChannel() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();
        paymentChannel.setId(count.incrementAndGet());

        // Create the PaymentChannel
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentChannelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentChannel() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();
        paymentChannel.setId(count.incrementAndGet());

        // Create the PaymentChannel
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentChannel() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();
        paymentChannel.setId(count.incrementAndGet());

        // Create the PaymentChannel
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentChannelWithPatch() throws Exception {
        // Initialize the database
        paymentChannelRepository.saveAndFlush(paymentChannel);

        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();

        // Update the paymentChannel using partial update
        PaymentChannel partialUpdatedPaymentChannel = new PaymentChannel();
        partialUpdatedPaymentChannel.setId(paymentChannel.getId());

        partialUpdatedPaymentChannel.channelCode(UPDATED_CHANNEL_CODE).freeField1(UPDATED_FREE_FIELD_1);

        restPaymentChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentChannel))
            )
            .andExpect(status().isOk());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
        PaymentChannel testPaymentChannel = paymentChannelList.get(paymentChannelList.size() - 1);
        assertThat(testPaymentChannel.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannel.getChannelId()).isEqualTo(DEFAULT_CHANNEL_ID);
        assertThat(testPaymentChannel.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testPaymentChannel.getChannelName()).isEqualTo(DEFAULT_CHANNEL_NAME);
        assertThat(testPaymentChannel.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentChannel.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentChannel.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPaymentChannel.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testPaymentChannel.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdatePaymentChannelWithPatch() throws Exception {
        // Initialize the database
        paymentChannelRepository.saveAndFlush(paymentChannel);

        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();

        // Update the paymentChannel using partial update
        PaymentChannel partialUpdatedPaymentChannel = new PaymentChannel();
        partialUpdatedPaymentChannel.setId(paymentChannel.getId());

        partialUpdatedPaymentChannel
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .channelId(UPDATED_CHANNEL_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelName(UPDATED_CHANNEL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);

        restPaymentChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentChannel))
            )
            .andExpect(status().isOk());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
        PaymentChannel testPaymentChannel = paymentChannelList.get(paymentChannelList.size() - 1);
        assertThat(testPaymentChannel.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannel.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testPaymentChannel.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testPaymentChannel.getChannelName()).isEqualTo(UPDATED_CHANNEL_NAME);
        assertThat(testPaymentChannel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentChannel.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentChannel.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaymentChannel.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPaymentChannel.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentChannel() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();
        paymentChannel.setId(count.incrementAndGet());

        // Create the PaymentChannel
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentChannelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentChannel() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();
        paymentChannel.setId(count.incrementAndGet());

        // Create the PaymentChannel
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentChannel() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelRepository.findAll().size();
        paymentChannel.setId(count.incrementAndGet());

        // Create the PaymentChannel
        PaymentChannelDTO paymentChannelDTO = paymentChannelMapper.toDto(paymentChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentChannel in the database
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentChannel() throws Exception {
        // Initialize the database
        paymentChannelRepository.saveAndFlush(paymentChannel);

        int databaseSizeBeforeDelete = paymentChannelRepository.findAll().size();

        // Delete the paymentChannel
        restPaymentChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentChannel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentChannel> paymentChannelList = paymentChannelRepository.findAll();
        assertThat(paymentChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
