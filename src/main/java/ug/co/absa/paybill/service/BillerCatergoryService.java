package ug.co.absa.paybill.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.paybill.domain.BillerCatergory;
import ug.co.absa.paybill.repository.BillerCatergoryRepository;
import ug.co.absa.paybill.service.dto.BillerCatergoryDTO;
import ug.co.absa.paybill.service.mapper.BillerCatergoryMapper;

/**
 * Service Implementation for managing {@link BillerCatergory}.
 */
@Service
@Transactional
public class BillerCatergoryService {

    private final Logger log = LoggerFactory.getLogger(BillerCatergoryService.class);

    private final BillerCatergoryRepository billerCatergoryRepository;

    private final BillerCatergoryMapper billerCatergoryMapper;

    public BillerCatergoryService(BillerCatergoryRepository billerCatergoryRepository, BillerCatergoryMapper billerCatergoryMapper) {
        this.billerCatergoryRepository = billerCatergoryRepository;
        this.billerCatergoryMapper = billerCatergoryMapper;
    }

    /**
     * Save a billerCatergory.
     *
     * @param billerCatergoryDTO the entity to save.
     * @return the persisted entity.
     */
    public BillerCatergoryDTO save(BillerCatergoryDTO billerCatergoryDTO) {
        log.debug("Request to save BillerCatergory : {}", billerCatergoryDTO);
        BillerCatergory billerCatergory = billerCatergoryMapper.toEntity(billerCatergoryDTO);
        billerCatergory = billerCatergoryRepository.save(billerCatergory);
        return billerCatergoryMapper.toDto(billerCatergory);
    }

    /**
     * Update a billerCatergory.
     *
     * @param billerCatergoryDTO the entity to save.
     * @return the persisted entity.
     */
    public BillerCatergoryDTO update(BillerCatergoryDTO billerCatergoryDTO) {
        log.debug("Request to update BillerCatergory : {}", billerCatergoryDTO);
        BillerCatergory billerCatergory = billerCatergoryMapper.toEntity(billerCatergoryDTO);
        billerCatergory = billerCatergoryRepository.save(billerCatergory);
        return billerCatergoryMapper.toDto(billerCatergory);
    }

    /**
     * Partially update a billerCatergory.
     *
     * @param billerCatergoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BillerCatergoryDTO> partialUpdate(BillerCatergoryDTO billerCatergoryDTO) {
        log.debug("Request to partially update BillerCatergory : {}", billerCatergoryDTO);

        return billerCatergoryRepository
            .findById(billerCatergoryDTO.getId())
            .map(existingBillerCatergory -> {
                billerCatergoryMapper.partialUpdate(existingBillerCatergory, billerCatergoryDTO);

                return existingBillerCatergory;
            })
            .map(billerCatergoryRepository::save)
            .map(billerCatergoryMapper::toDto);
    }

    /**
     * Get all the billerCatergories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BillerCatergoryDTO> findAll() {
        log.debug("Request to get all BillerCatergories");
        return billerCatergoryRepository
            .findAll()
            .stream()
            .map(billerCatergoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one billerCatergory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BillerCatergoryDTO> findOne(Long id) {
        log.debug("Request to get BillerCatergory : {}", id);
        return billerCatergoryRepository.findById(id).map(billerCatergoryMapper::toDto);
    }

    /**
     * Delete the billerCatergory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BillerCatergory : {}", id);
        billerCatergoryRepository.deleteById(id);
    }
}
