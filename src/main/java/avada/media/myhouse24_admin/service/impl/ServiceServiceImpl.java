package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.dto.ServiceDTO;
import avada.media.myhouse24_admin.repo.systemSettings.ServiceRepo;
import avada.media.myhouse24_admin.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepo serviceRepo;

    @Override
    public List<ServiceDTO> getAllServicesDTO() {
        return serviceRepo
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(service -> {
                    ServiceDTO serviceDTO = new ServiceDTO();
                    serviceDTO.setId(service.getId());
                    serviceDTO.setName(service.getName());
                    serviceDTO.setUnit(service.getUnit());
                    return serviceDTO;
                })
                .collect(Collectors.toList());
    }

}
