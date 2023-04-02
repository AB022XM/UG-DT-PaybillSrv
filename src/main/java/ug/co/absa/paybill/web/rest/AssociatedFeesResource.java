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
import ug.co.absa.paybill.repository.AssociatedFeesRepository;
import ug.co.absa.paybill.service.AssociatedFeesService;
import ug.co.absa.paybill.service.dto.AssociatedFeesDTO;
import ug.co.absa.paybill.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.paybill.domain.AssociatedFees}.
 */
@RestController
@RequestMapping("/api")
public class AssociatedFeesResource {

    private final Logger log = LoggerFactory.getLogger(AssociatedFeesResource.class);

    private static final String ENTITY_NAME = "associatedFees";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssociatedFeesService associatedFeesService;

    private final AssociatedFeesRepository associatedFeesRepository;

    public AssociatedFeesResource(AssociatedFeesService associatedFeesService, AssociatedFeesRepository associatedFeesRepository) {
        this.associatedFeesService = associatedFeesService;
        this.associatedFeesRepository = associatedFeesRepository;
    }

    /**
     * {@code POST  /associated-fees} : Create a new associatedFees.
     *
     * @param associatedFeesDTO the associatedFeesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new associatedFeesDTO, or with status {@code 400 (Bad Request)} if the associatedFees has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/associated-fees")
    public ResponseEntity<AssociatedFeesDTO> createAssociatedFees(@Valid @RequestBody AssociatedFeesDTO associatedFeesDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssociatedFees : {}", associatedFeesDTO);
        if (associatedFeesDTO.getId() != null) {
            throw new BadRequestAlertException("A new associatedFees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssociatedFeesDTO result = associatedFeesService.save(associatedFeesDTO);
        return ResponseEntity
            .created(new URI("/api/associated-fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /associated-fees/:id} : Updates an existing associatedFees.
     *
     * @param id the id of the associatedFeesDTO to save.
     * @param associatedFeesDTO the associatedFeesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated associatedFeesDTO,
     * or with status {@code 400 (Bad Request)} if the associatedFeesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the associatedFeesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/associated-fees/{id}")
    public ResponseEntity<AssociatedFeesDTO> updateAssociatedFees(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssociatedFeesDTO associatedFeesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssociatedFees : {}, {}", id, associatedFeesDTO);
        if (associatedFeesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, associatedFeesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!associatedFeesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssociatedFeesDTO result = associatedFeesService.update(associatedFeesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, associatedFeesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /associated-fees/:id} : Partial updates given fields of an existing associatedFees, field will ignore if it is null
     *
     * @param id the id of the associatedFeesDTO to save.
     * @param associatedFeesDTO the associatedFeesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated associatedFeesDTO,
     * or with status {@code 400 (Bad Request)} if the associatedFeesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the associatedFeesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the associatedFeesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/associated-fees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssociatedFeesDTO> partialUpdateAssociatedFees(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssociatedFeesDTO associatedFeesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssociatedFees partially : {}, {}", id, associatedFeesDTO);
        if (associatedFeesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, associatedFeesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!associatedFeesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssociatedFeesDTO> result = associatedFeesService.partialUpdate(associatedFeesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, associatedFeesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /associated-fees} : get all the associatedFees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of associatedFees in body.
     */
    @GetMapping("/associated-fees")
    public List<AssociatedFeesDTO> getAllAssociatedFees() {
        log.debug("REST request to get all AssociatedFees");
        return associatedFeesService.findAll();
    }

    /**
     * {@code GET  /associated-fees/:id} : get the "id" associatedFees.
     *
     * @param id the id of the associatedFeesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the associatedFeesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/associated-fees/{id}")
    public ResponseEntity<AssociatedFeesDTO> getAssociatedFees(@PathVariable Long id) {
        log.debug("REST request to get AssociatedFees : {}", id);
        Optional<AssociatedFeesDTO> associatedFeesDTO = associatedFeesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(associatedFeesDTO);
    }

    /**
     * {@code DELETE  /associated-fees/:id} : delete the "id" associatedFees.
     *
     * @param id the id of the associatedFeesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/associated-fees/{id}")
    public ResponseEntity<Void> deleteAssociatedFees(@PathVariable Long id) {
        log.debug("REST request to delete AssociatedFees : {}", id);
        associatedFeesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
