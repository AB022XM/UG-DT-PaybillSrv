package ug.co.absa.paybill.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.paybill.domain.Biller;
import ug.co.absa.paybill.repository.BillerRepository;
import ug.co.absa.paybill.service.dto.BillerDTO;
import ug.co.absa.paybill.service.mapper.BillerMapper;

/**
 * Service Implementation for managing {@link Biller}.
 */
@Service
@Transactional
public class BillerService {

    private final Logger log = LoggerFactory.getLogger(BillerService.class);

    private final BillerRepository billerRepository;

    private final BillerMapper billerMapper;

    public BillerService(BillerRepository billerRepository, BillerMapper billerMapper) {
        this.billerRepository = billerRepository;
        this.billerMapper = billerMapper;
    }

    /**
     * Save a biller.
     *
     * @param billerDTO the entity to save.
     * @return the persisted entity.
     */
    public BillerDTO save(BillerDTO billerDTO) {
        log.debug("Request to save Biller : {}", billerDTO);
        Biller biller = billerMapper.toEntity(billerDTO);
        biller = billerRepository.save(biller);
        return billerMapper.toDto(biller);
    }

    /**
     * Update a biller.
     *
     * @param billerDTO the entity to save.
     * @return the persisted entity.
     */
    public BillerDTO update(BillerDTO billerDTO) {
        log.debug("Request to update Biller : {}", billerDTO);
        Biller biller = billerMapper.toEntity(billerDTO);
        biller = billerRepository.save(biller);
        return billerMapper.toDto(biller);
    }

    /**
     * Partially update a biller.
     *
     * @param billerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BillerDTO> partialUpdate(BillerDTO billerDTO) {
        log.debug("Request to partially update Biller : {}", billerDTO);

        return billerRepository
            .findById(billerDTO.getId())
            .map(existingBiller -> {
                billerMapper.partialUpdate(existingBiller, billerDTO);

                return existingBiller;
            })
            .map(billerRepository::save)
            .map(billerMapper::toDto);
    }

    /**
     * Get all the billers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BillerDTO> findAll() {
        log.debug("Request to get all Billers");
        return billerRepository.findAll().stream().map(billerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one biller by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BillerDTO> findOne(Long id) {
        log.debug("Request to get Biller : {}", id);
        return billerRepository.findById(id).map(billerMapper::toDto);
    }

    /**
     * Delete the biller by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Biller : {}", id);
        billerRepository.deleteById(id);
    }
}
