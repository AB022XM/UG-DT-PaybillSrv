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
import ug.co.absa.paybill.repository.PaybillRepository;
import ug.co.absa.paybill.service.PaybillService;
import ug.co.absa.paybill.service.dto.PaybillDTO;
import ug.co.absa.paybill.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.paybill.domain.Paybill}.
 */
@RestController
@RequestMapping("/api")
public class PaybillResource {

    private final Logger log = LoggerFactory.getLogger(PaybillResource.class);

    private static final String ENTITY_NAME = "paybill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaybillService paybillService;

    private final PaybillRepository paybillRepository;

    public PaybillResource(PaybillService paybillService, PaybillRepository paybillRepository) {
        this.paybillService = paybillService;
        this.paybillRepository = paybillRepository;
    }

    /**
     * {@code POST  /paybills} : Create a new paybill.
     *
     * @param paybillDTO the paybillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paybillDTO, or with status {@code 400 (Bad Request)} if the paybill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paybills")
    public ResponseEntity<PaybillDTO> createPaybill(@Valid @RequestBody PaybillDTO paybillDTO) throws URISyntaxException {
        log.debug("REST request to save Paybill : {}", paybillDTO);
        if (paybillDTO.getId() != null) {
            throw new BadRequestAlertException("A new paybill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaybillDTO result = paybillService.save(paybillDTO);
        return ResponseEntity
            .created(new URI("/api/paybills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paybills/:id} : Updates an existing paybill.
     *
     * @param id the id of the paybillDTO to save.
     * @param paybillDTO the paybillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paybillDTO,
     * or with status {@code 400 (Bad Request)} if the paybillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paybillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paybills/{id}")
    public ResponseEntity<PaybillDTO> updatePaybill(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaybillDTO paybillDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Paybill : {}, {}", id, paybillDTO);
        if (paybillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paybillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paybillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaybillDTO result = paybillService.update(paybillDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paybillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paybills/:id} : Partial updates given fields of an existing paybill, field will ignore if it is null
     *
     * @param id the id of the paybillDTO to save.
     * @param paybillDTO the paybillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paybillDTO,
     * or with status {@code 400 (Bad Request)} if the paybillDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paybillDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paybillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paybills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaybillDTO> partialUpdatePaybill(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaybillDTO paybillDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Paybill partially : {}, {}", id, paybillDTO);
        if (paybillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paybillDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paybillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaybillDTO> result = paybillService.partialUpdate(paybillDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paybillDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /paybills} : get all the paybills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paybills in body.
     */
    @GetMapping("/paybills")
    public List<PaybillDTO> getAllPaybills() {
        log.debug("REST request to get all Paybills");
        return paybillService.findAll();
    }

    /**
     * {@code GET  /paybills/:id} : get the "id" paybill.
     *
     * @param id the id of the paybillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paybillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paybills/{id}")
    public ResponseEntity<PaybillDTO> getPaybill(@PathVariable Long id) {
        log.debug("REST request to get Paybill : {}", id);
        Optional<PaybillDTO> paybillDTO = paybillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paybillDTO);
    }

    /**
     * {@code DELETE  /paybills/:id} : delete the "id" paybill.
     *
     * @param id the id of the paybillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paybills/{id}")
    public ResponseEntity<Void> deletePaybill(@PathVariable Long id) {
        log.debug("REST request to delete Paybill : {}", id);
        paybillService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
