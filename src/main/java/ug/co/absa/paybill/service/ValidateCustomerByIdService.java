package ug.co.absa.paybill.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.paybill.domain.ValidateCustomerById;
import ug.co.absa.paybill.repository.ValidateCustomerByIdRepository;
import ug.co.absa.paybill.service.dto.ValidateCustomerByIdDTO;
import ug.co.absa.paybill.service.mapper.ValidateCustomerByIdMapper;

/**
 * Service Implementation for managing {@link ValidateCustomerById}.
 */
@Service
@Transactional
public class ValidateCustomerByIdService {

    private final Logger log = LoggerFactory.getLogger(ValidateCustomerByIdService.class);

    private final ValidateCustomerByIdRepository validateCustomerByIdRepository;

    private final ValidateCustomerByIdMapper validateCustomerByIdMapper;

    public ValidateCustomerByIdService(
        ValidateCustomerByIdRepository validateCustomerByIdRepository,
        ValidateCustomerByIdMapper validateCustomerByIdMapper
    ) {
        this.validateCustomerByIdRepository = validateCustomerByIdRepository;
        this.validateCustomerByIdMapper = validateCustomerByIdMapper;
    }

    /**
     * Save a validateCustomerById.
     *
     * @param validateCustomerByIdDTO the entity to save.
     * @return the persisted entity.
     */
    public ValidateCustomerByIdDTO save(ValidateCustomerByIdDTO validateCustomerByIdDTO) {
        log.debug("Request to save ValidateCustomerById : {}", validateCustomerByIdDTO);
        ValidateCustomerById validateCustomerById = validateCustomerByIdMapper.toEntity(validateCustomerByIdDTO);
        validateCustomerById = validateCustomerByIdRepository.save(validateCustomerById);
        return validateCustomerByIdMapper.toDto(validateCustomerById);
    }

    /**
     * Update a validateCustomerById.
     *
     * @param validateCustomerByIdDTO the entity to save.
     * @return the persisted entity.
     */
    public ValidateCustomerByIdDTO update(ValidateCustomerByIdDTO validateCustomerByIdDTO) {
        log.debug("Request to update ValidateCustomerById : {}", validateCustomerByIdDTO);
        ValidateCustomerById validateCustomerById = validateCustomerByIdMapper.toEntity(validateCustomerByIdDTO);
        validateCustomerById = validateCustomerByIdRepository.save(validateCustomerById);
        return validateCustomerByIdMapper.toDto(validateCustomerById);
    }

    /**
     * Partially update a validateCustomerById.
     *
     * @param validateCustomerByIdDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ValidateCustomerByIdDTO> partialUpdate(ValidateCustomerByIdDTO validateCustomerByIdDTO) {
        log.debug("Request to partially update ValidateCustomerById : {}", validateCustomerByIdDTO);

        return validateCustomerByIdRepository
            .findById(validateCustomerByIdDTO.getId())
            .map(existingValidateCustomerById -> {
                validateCustomerByIdMapper.partialUpdate(existingValidateCustomerById, validateCustomerByIdDTO);

                return existingValidateCustomerById;
            })
            .map(validateCustomerByIdRepository::save)
            .map(validateCustomerByIdMapper::toDto);
    }

    /**
     * Get all the validateCustomerByIds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ValidateCustomerByIdDTO> findAll() {
        log.debug("Request to get all ValidateCustomerByIds");
        return validateCustomerByIdRepository
            .findAll()
            .stream()
            .map(validateCustomerByIdMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one validateCustomerById by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ValidateCustomerByIdDTO> findOne(Long id) {
        log.debug("Request to get ValidateCustomerById : {}", id);
        return validateCustomerByIdRepository.findById(id).map(validateCustomerByIdMapper::toDto);
    }

    /**
     * Delete the validateCustomerById by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ValidateCustomerById : {}", id);
        validateCustomerByIdRepository.deleteById(id);
    }
}
