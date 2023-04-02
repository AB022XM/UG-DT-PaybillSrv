package ug.co.absa.paybill.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.paybill.domain.AssociatedFees;
import ug.co.absa.paybill.repository.AssociatedFeesRepository;
import ug.co.absa.paybill.service.dto.AssociatedFeesDTO;
import ug.co.absa.paybill.service.mapper.AssociatedFeesMapper;

/**
 * Service Implementation for managing {@link AssociatedFees}.
 */
@Service
@Transactional
public class AssociatedFeesService {

    private final Logger log = LoggerFactory.getLogger(AssociatedFeesService.class);

    private final AssociatedFeesRepository associatedFeesRepository;

    private final AssociatedFeesMapper associatedFeesMapper;

    public AssociatedFeesService(AssociatedFeesRepository associatedFeesRepository, AssociatedFeesMapper associatedFeesMapper) {
        this.associatedFeesRepository = associatedFeesRepository;
        this.associatedFeesMapper = associatedFeesMapper;
    }

    /**
     * Save a associatedFees.
     *
     * @param associatedFeesDTO the entity to save.
     * @return the persisted entity.
     */
    public AssociatedFeesDTO save(AssociatedFeesDTO associatedFeesDTO) {
        log.debug("Request to save AssociatedFees : {}", associatedFeesDTO);
        AssociatedFees associatedFees = associatedFeesMapper.toEntity(associatedFeesDTO);
        associatedFees = associatedFeesRepository.save(associatedFees);
        return associatedFeesMapper.toDto(associatedFees);
    }

    /**
     * Update a associatedFees.
     *
     * @param associatedFeesDTO the entity to save.
     * @return the persisted entity.
     */
    public AssociatedFeesDTO update(AssociatedFeesDTO associatedFeesDTO) {
        log.debug("Request to update AssociatedFees : {}", associatedFeesDTO);
        AssociatedFees associatedFees = associatedFeesMapper.toEntity(associatedFeesDTO);
        associatedFees = associatedFeesRepository.save(associatedFees);
        return associatedFeesMapper.toDto(associatedFees);
    }

    /**
     * Partially update a associatedFees.
     *
     * @param associatedFeesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssociatedFeesDTO> partialUpdate(AssociatedFeesDTO associatedFeesDTO) {
        log.debug("Request to partially update AssociatedFees : {}", associatedFeesDTO);

        return associatedFeesRepository
            .findById(associatedFeesDTO.getId())
            .map(existingAssociatedFees -> {
                associatedFeesMapper.partialUpdate(existingAssociatedFees, associatedFeesDTO);

                return existingAssociatedFees;
            })
            .map(associatedFeesRepository::save)
            .map(associatedFeesMapper::toDto);
    }

    /**
     * Get all the associatedFees.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssociatedFeesDTO> findAll() {
        log.debug("Request to get all AssociatedFees");
        return associatedFeesRepository
            .findAll()
            .stream()
            .map(associatedFeesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one associatedFees by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssociatedFeesDTO> findOne(Long id) {
        log.debug("Request to get AssociatedFees : {}", id);
        return associatedFeesRepository.findById(id).map(associatedFeesMapper::toDto);
    }

    /**
     * Delete the associatedFees by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssociatedFees : {}", id);
        associatedFeesRepository.deleteById(id);
    }
}
