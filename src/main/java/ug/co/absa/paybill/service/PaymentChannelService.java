package ug.co.absa.paybill.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.paybill.domain.PaymentChannel;
import ug.co.absa.paybill.repository.PaymentChannelRepository;
import ug.co.absa.paybill.service.dto.PaymentChannelDTO;
import ug.co.absa.paybill.service.mapper.PaymentChannelMapper;

/**
 * Service Implementation for managing {@link PaymentChannel}.
 */
@Service
@Transactional
public class PaymentChannelService {

    private final Logger log = LoggerFactory.getLogger(PaymentChannelService.class);

    private final PaymentChannelRepository paymentChannelRepository;

    private final PaymentChannelMapper paymentChannelMapper;

    public PaymentChannelService(PaymentChannelRepository paymentChannelRepository, PaymentChannelMapper paymentChannelMapper) {
        this.paymentChannelRepository = paymentChannelRepository;
        this.paymentChannelMapper = paymentChannelMapper;
    }

    /**
     * Save a paymentChannel.
     *
     * @param paymentChannelDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentChannelDTO save(PaymentChannelDTO paymentChannelDTO) {
        log.debug("Request to save PaymentChannel : {}", paymentChannelDTO);
        PaymentChannel paymentChannel = paymentChannelMapper.toEntity(paymentChannelDTO);
        paymentChannel = paymentChannelRepository.save(paymentChannel);
        return paymentChannelMapper.toDto(paymentChannel);
    }

    /**
     * Update a paymentChannel.
     *
     * @param paymentChannelDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentChannelDTO update(PaymentChannelDTO paymentChannelDTO) {
        log.debug("Request to update PaymentChannel : {}", paymentChannelDTO);
        PaymentChannel paymentChannel = paymentChannelMapper.toEntity(paymentChannelDTO);
        paymentChannel = paymentChannelRepository.save(paymentChannel);
        return paymentChannelMapper.toDto(paymentChannel);
    }

    /**
     * Partially update a paymentChannel.
     *
     * @param paymentChannelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentChannelDTO> partialUpdate(PaymentChannelDTO paymentChannelDTO) {
        log.debug("Request to partially update PaymentChannel : {}", paymentChannelDTO);

        return paymentChannelRepository
            .findById(paymentChannelDTO.getId())
            .map(existingPaymentChannel -> {
                paymentChannelMapper.partialUpdate(existingPaymentChannel, paymentChannelDTO);

                return existingPaymentChannel;
            })
            .map(paymentChannelRepository::save)
            .map(paymentChannelMapper::toDto);
    }

    /**
     * Get all the paymentChannels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentChannelDTO> findAll() {
        log.debug("Request to get all PaymentChannels");
        return paymentChannelRepository
            .findAll()
            .stream()
            .map(paymentChannelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one paymentChannel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentChannelDTO> findOne(Long id) {
        log.debug("Request to get PaymentChannel : {}", id);
        return paymentChannelRepository.findById(id).map(paymentChannelMapper::toDto);
    }

    /**
     * Delete the paymentChannel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentChannel : {}", id);
        paymentChannelRepository.deleteById(id);
    }
}
