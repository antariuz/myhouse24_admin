package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Counter;
import avada.media.myhouse24_admin.model.Counter.Status;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.CounterRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.repo.CounterRepo;
import avada.media.myhouse24_admin.repo.FlatRepo;
import avada.media.myhouse24_admin.repo.systemSettings.ServiceRepo;
import avada.media.myhouse24_admin.service.CounterService;
import avada.media.myhouse24_admin.spec.CounterSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterServiceImpl implements CounterService {

    private final CounterRepo counterRepo;
    private final CounterSpec counterSpec;
    private final FlatRepo flatRepo;
    private final ServiceRepo serviceRepo;

    @Override
    @Transactional
    public ResponseByPage<CounterDTO> getAllCounters(CounterRequest counterRequest) {
        Page<Counter> counters =
                counterRepo.findAll(counterSpec.getCounters(counterRequest), PageRequest.of(counterRequest.getPageIndex() - 1, counterRequest.getPageSize()));
        ResponseByPage<CounterDTO> responseByPage = new ResponseByPage<>();
        responseByPage.setItemsCount(counters.getTotalElements());
        responseByPage.setData(counters.stream().map(counter -> {
                    CounterDTO counterDTO = new CounterDTO();
                    counterDTO.setId(counter.getId());
                    counterDTO.setUniqueNumber(counter.getUniqueNumber());
                    counterDTO.setStatus(new StatusDTO(counter.getStatus().name(), counter.getStatus().getTitle()));
                    counterDTO.setRequestedDate(counter.getRequestedDate());
                    counterDTO.setAmount(counter.getAmount());
                    BuildingDTO buildingDTO = new BuildingDTO();
                    buildingDTO.setId(counter.getFlat().getBuilding().getId());
                    buildingDTO.setTitle(counter.getFlat().getBuilding().getTitle());
                    counterDTO.setBuilding(buildingDTO);
                    if (counter.getFlat().getSection() != null) counterDTO.setSection(counter.getFlat().getSection());
                    FlatDTO flatDTO = new FlatDTO();
                    flatDTO.setId(counter.getFlat().getId());
                    flatDTO.setTitle(counter.getFlat().getNumber());
                    Hibernate.initialize(counter.getFlat().getUser().getProfile());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(counter.getFlat().getUser().getId());
                    userDTO.setFullName(counter.getFlat().getUser().getProfile().getLastname(), counter.getFlat().getUser().getProfile().getFirstname(), counter.getFlat().getUser().getProfile().getMiddleName());
                    flatDTO.setUser(userDTO);
                    counterDTO.setFlat(flatDTO);
                    if (counter.getService() != null) {
                        ServiceDTO serviceDTO = new ServiceDTO();
                        serviceDTO.setId(counter.getService().getId());
                        serviceDTO.setName(counter.getService().getName());
                        counterDTO.setService(serviceDTO);
                        counterDTO.setUnit(counter.getService().getUnit());
                    }
                    return counterDTO;
                })
                .collect(Collectors.toList()));
        return responseByPage;
    }

    @Override
    public String getNewUniqueNumber() {
        long id = 11102200000L;
        try {
            id = id + counterRepo.findFirstByOrderByIdDesc().getId() + 1L;
        } catch (NullPointerException e) {
            log.error("There is no any accounts in DB. Error: " + e.getMessage());
            id++;
        }
        return Long.toString(id);
    }

    @Override
    public boolean isUniqueNumberNotExists(Long id, String number) {
        if (id != null && number.equals(counterRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id))
                .getUniqueNumber())) {
            return true;
        } else return !counterRepo.existsByUniqueNumber(number);
    }

    @Override
    public List<StatusDTO> getAllStatus() {
        return Arrays.stream(Status.values())
                .map(status -> new StatusDTO(status.name(), status.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveCounter(CounterDTO counterDTO) {
        Counter counter = new Counter();
        counter.setUniqueNumber(counterDTO.getUniqueNumber());
        counter.setRequestedDate(counterDTO.getRequestedDate());
        counter.setAmount(counterDTO.getAmount());
        counter.setStatus(Status.valueOf(counterDTO.getStatus().getValue()));
        counter.setFlat(flatRepo.findById(counterDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + counterDTO.getFlat().getId())));
        if (counterDTO.getService() != null)
            counter.setService(serviceRepo.findById(counterDTO.getService().getId()).orElseThrow(() -> new EntityNotFoundException("Service not found with ID: " + counterDTO.getService().getId())));
        counterRepo.save(counter);
    }

    @Override
    public void updateCounter(Long id, CounterDTO counterDTO) {
        Counter counter = counterRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Counter not found with ID: " + id));
        counter.setUniqueNumber(counterDTO.getUniqueNumber());
        counter.setRequestedDate(counterDTO.getRequestedDate());
        counter.setAmount(counterDTO.getAmount());
        counter.setStatus(Status.valueOf(counterDTO.getStatus().getValue()));
        counter.setFlat(flatRepo.findById(counterDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + counterDTO.getFlat().getId())));
        if (counterDTO.getService() != null)
            counter.setService(serviceRepo.findById(counterDTO.getService().getId()).orElseThrow(() -> new EntityNotFoundException("Service not found with ID: " + counterDTO.getService().getId())));
        counterRepo.save(counter);
    }

    @Override
    public void deleteCounter(Long id) {
        counterRepo.deleteById(id);
    }

}
