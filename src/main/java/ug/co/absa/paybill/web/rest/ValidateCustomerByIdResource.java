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
import ug.co.absa.paybill.repository.ValidateCustomerByIdRepository;
import ug.co.absa.paybill.service.ValidateCustomerByIdService;
import ug.co.absa.paybill.service.dto.ValidateCustomerByIdDTO;
import ug.co.absa.paybill.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.paybill.domain.ValidateCustomerById}.
 */
@RestController
@RequestMapping("/api")
public class ValidateCustomerByIdResource {

    private final Logger log = LoggerFactory.getLogger(ValidateCustomerByIdResource.class);

    private static final String ENTITY_NAME = "validateCustomerById";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValidateCustomerByIdService validateCustomerByIdService;

    private final ValidateCustomerByIdRepository validateCustomerByIdRepository;

    public ValidateCustomerByIdResource(
        ValidateCustomerByIdService validateCustomerByIdService,
        ValidateCustomerByIdRepository validateCustomerByIdRepository
    ) {
        this.validateCustomerByIdService = validateCustomerByIdService;
        this.validateCustomerByIdRepository = validateCustomerByIdRepository;
    }

    /**
     * {@code POST  /validate-customer-by-ids} : Create a new validateCustomerById.
     *
     * @param validateCustomerByIdDTO the validateCustomerByIdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new validateCustomerByIdDTO, or with status {@code 400 (Bad Request)} if the validateCustomerById has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/validate-customer-by-ids")
    public ResponseEntity<ValidateCustomerByIdDTO> createValidateCustomerById(
        @Valid @RequestBody ValidateCustomerByIdDTO validateCustomerByIdDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ValidateCustomerById : {}", validateCustomerByIdDTO);
        if (validateCustomerByIdDTO.getId() != null) {
            throw new BadRequestAlertException("A new validateCustomerById cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValidateCustomerByIdDTO result = validateCustomerByIdService.save(validateCustomerByIdDTO);
        return ResponseEntity
            .created(new URI("/api/validate-customer-by-ids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /validate-customer-by-ids/:id} : Updates an existing validateCustomerById.
     *
     * @param id the id of the validateCustomerByIdDTO to save.
     * @param validateCustomerByIdDTO the validateCustomerByIdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validateCustomerByIdDTO,
     * or with status {@code 400 (Bad Request)} if the validateCustomerByIdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the validateCustomerByIdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/validate-customer-by-ids/{id}")
    public ResponseEntity<ValidateCustomerByIdDTO> updateValidateCustomerById(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ValidateCustomerByIdDTO validateCustomerByIdDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ValidateCustomerById : {}, {}", id, validateCustomerByIdDTO);
        if (validateCustomerByIdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, validateCustomerByIdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!validateCustomerByIdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ValidateCustomerByIdDTO result = validateCustomerByIdService.update(validateCustomerByIdDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, validateCustomerByIdDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /validate-customer-by-ids/:id} : Partial updates given fields of an existing validateCustomerById, field will ignore if it is null
     *
     * @param id the id of the validateCustomerByIdDTO to save.
     * @param validateCustomerByIdDTO the validateCustomerByIdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validateCustomerByIdDTO,
     * or with status {@code 400 (Bad Request)} if the validateCustomerByIdDTO is not valid,
     * or with status {@code 404 (Not Found)} if the validateCustomerByIdDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the validateCustomerByIdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/validate-customer-by-ids/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ValidateCustomerByIdDTO> partialUpdateValidateCustomerById(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ValidateCustomerByIdDTO validateCustomerByIdDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ValidateCustomerById partially : {}, {}", id, validateCustomerByIdDTO);
        if (validateCustomerByIdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, validateCustomerByIdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!validateCustomerByIdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ValidateCustomerByIdDTO> result = validateCustomerByIdService.partialUpdate(validateCustomerByIdDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, validateCustomerByIdDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /validate-customer-by-ids} : get all the validateCustomerByIds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of validateCustomerByIds in body.
     */
    @GetMapping("/validate-customer-by-ids")
    public List<ValidateCustomerByIdDTO> getAllValidateCustomerByIds() {
        log.debug("REST request to get all ValidateCustomerByIds");
        return validateCustomerByIdService.findAll();
    }

    /**
     * {@code GET  /validate-customer-by-ids/:id} : get the "id" validateCustomerById.
     *
     * @param id the id of the validateCustomerByIdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the validateCustomerByIdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/validate-customer-by-ids/{id}")
    public ResponseEntity<ValidateCustomerByIdDTO> getValidateCustomerById(@PathVariable Long id) {
        log.debug("REST request to get ValidateCustomerById : {}", id);
        Optional<ValidateCustomerByIdDTO> validateCustomerByIdDTO = validateCustomerByIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(validateCustomerByIdDTO);
    }

    /**
     * {@code DELETE  /validate-customer-by-ids/:id} : delete the "id" validateCustomerById.
     *
     * @param id the id of the validateCustomerByIdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/validate-customer-by-ids/{id}")
    public ResponseEntity<Void> deleteValidateCustomerById(@PathVariable Long id) {
        log.debug("REST request to delete ValidateCustomerById : {}", id);
        validateCustomerByIdService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
