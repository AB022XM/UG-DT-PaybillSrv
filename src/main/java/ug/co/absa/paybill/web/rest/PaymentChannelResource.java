package ug.co.absa.paybill.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import ug.co.absa.paybill.repository.PaymentChannelRepository;
import ug.co.absa.paybill.service.PaymentChannelService;
import ug.co.absa.paybill.service.dto.PaymentChannelDTO;
import ug.co.absa.paybill.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.paybill.domain.PaymentChannel}.
 */
@RestController
@RequestMapping("/api")
public class PaymentChannelResource {

    private final Logger log = LoggerFactory.getLogger(PaymentChannelResource.class);

    private static final String ENTITY_NAME = "paymentChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentChannelService paymentChannelService;

    private final PaymentChannelRepository paymentChannelRepository;

    public PaymentChannelResource(PaymentChannelService paymentChannelService, PaymentChannelRepository paymentChannelRepository) {
        this.paymentChannelService = paymentChannelService;
        this.paymentChannelRepository = paymentChannelRepository;
    }

    /**
     * {@code POST  /payment-channels} : Create a new paymentChannel.
     *
     * @param paymentChannelDTO the paymentChannelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentChannelDTO, or with status {@code 400 (Bad Request)} if the paymentChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-channels")
    public ResponseEntity<PaymentChannelDTO> createPaymentChannel(@Valid @RequestBody PaymentChannelDTO paymentChannelDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentChannel : {}", paymentChannelDTO);
        if (paymentChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentChannelDTO result = paymentChannelService.save(paymentChannelDTO);
        return ResponseEntity
            .created(new URI("/api/payment-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-channels/:id} : Updates an existing paymentChannel.
     *
     * @param id the id of the paymentChannelDTO to save.
     * @param paymentChannelDTO the paymentChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentChannelDTO,
     * or with status {@code 400 (Bad Request)} if the paymentChannelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-channels/{id}")
    public ResponseEntity<PaymentChannelDTO> updatePaymentChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentChannelDTO paymentChannelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentChannel : {}, {}", id, paymentChannelDTO);
        if (paymentChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentChannelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentChannelDTO result = paymentChannelService.update(paymentChannelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-channels/:id} : Partial updates given fields of an existing paymentChannel, field will ignore if it is null
     *
     * @param id the id of the paymentChannelDTO to save.
     * @param paymentChannelDTO the paymentChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentChannelDTO,
     * or with status {@code 400 (Bad Request)} if the paymentChannelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentChannelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-channels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentChannelDTO> partialUpdatePaymentChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentChannelDTO paymentChannelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentChannel partially : {}, {}", id, paymentChannelDTO);
        if (paymentChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentChannelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentChannelDTO> result = paymentChannelService.partialUpdate(paymentChannelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentChannelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-channels} : get all the paymentChannels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentChannels in body.
     */
    @GetMapping("/payment-channels")
    public List<PaymentChannelDTO> getAllPaymentChannels() {
        log.debug("REST request to get all PaymentChannels");
        return paymentChannelService.findAll();
    }

    /**
     * {@code GET  /payment-channels/:id} : get the "id" paymentChannel.
     *
     * @param id the id of the paymentChannelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentChannelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-channels/{id}")
    public ResponseEntity<PaymentChannelDTO> getPaymentChannel(@PathVariable Long id) {
        log.debug("REST request to get PaymentChannel : {}", id);
        Optional<PaymentChannelDTO> paymentChannelDTO = paymentChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentChannelDTO);
    }

    /**
     * {@code DELETE  /payment-channels/:id} : delete the "id" paymentChannel.
     *
     * @param id the id of the paymentChannelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-channels/{id}")
    public ResponseEntity<Void> deletePaymentChannel(@PathVariable Long id) {
        log.debug("REST request to delete PaymentChannel : {}", id);
        paymentChannelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
