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
import ug.co.absa.paybill.repository.BillerCatergoryRepository;
import ug.co.absa.paybill.service.BillerCatergoryService;
import ug.co.absa.paybill.service.dto.BillerCatergoryDTO;
import ug.co.absa.paybill.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.paybill.domain.BillerCatergory}.
 */
@RestController
@RequestMapping("/api")
public class BillerCatergoryResource {

    private final Logger log = LoggerFactory.getLogger(BillerCatergoryResource.class);

    private static final String ENTITY_NAME = "billerCatergory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillerCatergoryService billerCatergoryService;

    private final BillerCatergoryRepository billerCatergoryRepository;

    public BillerCatergoryResource(BillerCatergoryService billerCatergoryService, BillerCatergoryRepository billerCatergoryRepository) {
        this.billerCatergoryService = billerCatergoryService;
        this.billerCatergoryRepository = billerCatergoryRepository;
    }

    /**
     * {@code POST  /biller-catergories} : Create a new billerCatergory.
     *
     * @param billerCatergoryDTO the billerCatergoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billerCatergoryDTO, or with status {@code 400 (Bad Request)} if the billerCatergory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/biller-catergories")
    public ResponseEntity<BillerCatergoryDTO> createBillerCatergory(@Valid @RequestBody BillerCatergoryDTO billerCatergoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save BillerCatergory : {}", billerCatergoryDTO);
        if (billerCatergoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new billerCatergory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillerCatergoryDTO result = billerCatergoryService.save(billerCatergoryDTO);
        return ResponseEntity
            .created(new URI("/api/biller-catergories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /biller-catergories/:id} : Updates an existing billerCatergory.
     *
     * @param id the id of the billerCatergoryDTO to save.
     * @param billerCatergoryDTO the billerCatergoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billerCatergoryDTO,
     * or with status {@code 400 (Bad Request)} if the billerCatergoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billerCatergoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/biller-catergories/{id}")
    public ResponseEntity<BillerCatergoryDTO> updateBillerCatergory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BillerCatergoryDTO billerCatergoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BillerCatergory : {}, {}", id, billerCatergoryDTO);
        if (billerCatergoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billerCatergoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billerCatergoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BillerCatergoryDTO result = billerCatergoryService.update(billerCatergoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billerCatergoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /biller-catergories/:id} : Partial updates given fields of an existing billerCatergory, field will ignore if it is null
     *
     * @param id the id of the billerCatergoryDTO to save.
     * @param billerCatergoryDTO the billerCatergoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billerCatergoryDTO,
     * or with status {@code 400 (Bad Request)} if the billerCatergoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the billerCatergoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the billerCatergoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/biller-catergories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BillerCatergoryDTO> partialUpdateBillerCatergory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BillerCatergoryDTO billerCatergoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BillerCatergory partially : {}, {}", id, billerCatergoryDTO);
        if (billerCatergoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billerCatergoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billerCatergoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BillerCatergoryDTO> result = billerCatergoryService.partialUpdate(billerCatergoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billerCatergoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /biller-catergories} : get all the billerCatergories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billerCatergories in body.
     */
    @GetMapping("/biller-catergories")
    public List<BillerCatergoryDTO> getAllBillerCatergories() {
        log.debug("REST request to get all BillerCatergories");
        return billerCatergoryService.findAll();
    }

    /**
     * {@code GET  /biller-catergories/:id} : get the "id" billerCatergory.
     *
     * @param id the id of the billerCatergoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billerCatergoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/biller-catergories/{id}")
    public ResponseEntity<BillerCatergoryDTO> getBillerCatergory(@PathVariable Long id) {
        log.debug("REST request to get BillerCatergory : {}", id);
        Optional<BillerCatergoryDTO> billerCatergoryDTO = billerCatergoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billerCatergoryDTO);
    }

    /**
     * {@code DELETE  /biller-catergories/:id} : delete the "id" billerCatergory.
     *
     * @param id the id of the billerCatergoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/biller-catergories/{id}")
    public ResponseEntity<Void> deleteBillerCatergory(@PathVariable Long id) {
        log.debug("REST request to delete BillerCatergory : {}", id);
        billerCatergoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
