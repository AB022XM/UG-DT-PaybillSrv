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
import ug.co.absa.paybill.repository.BillerRepository;
import ug.co.absa.paybill.service.BillerService;
import ug.co.absa.paybill.service.dto.BillerDTO;
import ug.co.absa.paybill.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.paybill.domain.Biller}.
 */
@RestController
@RequestMapping("/api")
public class BillerResource {

    private final Logger log = LoggerFactory.getLogger(BillerResource.class);

    private static final String ENTITY_NAME = "biller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillerService billerService;

    private final BillerRepository billerRepository;

    public BillerResource(BillerService billerService, BillerRepository billerRepository) {
        this.billerService = billerService;
        this.billerRepository = billerRepository;
    }

    /**
     * {@code POST  /billers} : Create a new biller.
     *
     * @param billerDTO the billerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billerDTO, or with status {@code 400 (Bad Request)} if the biller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billers")
    public ResponseEntity<BillerDTO> createBiller(@Valid @RequestBody BillerDTO billerDTO) throws URISyntaxException {
        log.debug("REST request to save Biller : {}", billerDTO);
        if (billerDTO.getId() != null) {
            throw new BadRequestAlertException("A new biller cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillerDTO result = billerService.save(billerDTO);
        return ResponseEntity
            .created(new URI("/api/billers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billers/:id} : Updates an existing biller.
     *
     * @param id the id of the billerDTO to save.
     * @param billerDTO the billerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billerDTO,
     * or with status {@code 400 (Bad Request)} if the billerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billers/{id}")
    public ResponseEntity<BillerDTO> updateBiller(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BillerDTO billerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Biller : {}, {}", id, billerDTO);
        if (billerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BillerDTO result = billerService.update(billerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /billers/:id} : Partial updates given fields of an existing biller, field will ignore if it is null
     *
     * @param id the id of the billerDTO to save.
     * @param billerDTO the billerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billerDTO,
     * or with status {@code 400 (Bad Request)} if the billerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the billerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the billerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/billers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BillerDTO> partialUpdateBiller(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BillerDTO billerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Biller partially : {}, {}", id, billerDTO);
        if (billerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BillerDTO> result = billerService.partialUpdate(billerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /billers} : get all the billers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billers in body.
     */
    @GetMapping("/billers")
    public List<BillerDTO> getAllBillers() {
        log.debug("REST request to get all Billers");
        return billerService.findAll();
    }

    /**
     * {@code GET  /billers/:id} : get the "id" biller.
     *
     * @param id the id of the billerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billers/{id}")
    public ResponseEntity<BillerDTO> getBiller(@PathVariable Long id) {
        log.debug("REST request to get Biller : {}", id);
        Optional<BillerDTO> billerDTO = billerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billerDTO);
    }

    /**
     * {@code DELETE  /billers/:id} : delete the "id" biller.
     *
     * @param id the id of the billerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billers/{id}")
    public ResponseEntity<Void> deleteBiller(@PathVariable Long id) {
        log.debug("REST request to delete Biller : {}", id);
        billerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
