package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.MasterRequest;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.MasterRequestRequest;
import avada.media.myhouse24_admin.repo.*;
import avada.media.myhouse24_admin.service.MasterRequestService;
import avada.media.myhouse24_admin.spec.MasterRequestSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterRequestServiceImpl implements MasterRequestService {

    private final MasterRequestRepo masterRequestRepo;
    private final MasterRequestSpec masterRequestSpec;
    private final FlatRepo flatRepo;
    private final StaffRepo staffRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    @Transactional
    public ResponseByPage<MasterRequestDTO> getAllMasterRequests(MasterRequestRequest MasterRequestRequest) {
        Page<MasterRequest> masterRequestsPage =
                masterRequestRepo.findAll(masterRequestSpec.getMasterRequests(MasterRequestRequest), PageRequest.of(MasterRequestRequest.getPageIndex() - 1, MasterRequestRequest.getPageSize()));
        ResponseByPage<MasterRequestDTO> masterRequestsByPageDTO = new ResponseByPage<>();
        masterRequestsByPageDTO.setItemsCount(masterRequestsPage.getTotalElements());
        for (MasterRequest masterRequest : masterRequestsPage) {
            MasterRequestDTO masterRequestDTO = new MasterRequestDTO();
            masterRequestDTO.setId(masterRequest.getId());
            masterRequestDTO.setRequestedDate(masterRequest.getRequestedDate());
            masterRequestDTO.setDescription(masterRequest.getDescription());
            masterRequestDTO.setComment(masterRequest.getComment());
            if (masterRequest.getRole() != null) {
                masterRequestDTO.setRole(new RoleDTO(masterRequest.getRole().getId(), masterRequest.getRole().getTitle()));
            } else {
                masterRequestDTO.setRole(new RoleDTO(0L, "Любой специалист"));
            }
            masterRequestDTO.setStatus(new StatusDTO(masterRequest.getStatus().name(), masterRequest.getStatus().getTitle()));
            FlatDTO flatDTO = new FlatDTO();
            flatDTO.setId(masterRequest.getFlat().getId());
            flatDTO.setTitle(masterRequest.getFlat().getNumber(), masterRequest.getFlat().getBuilding().getTitle());
            masterRequestDTO.setFlat(flatDTO);
            if (masterRequest.getStaff() != null) {
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(masterRequest.getStaff().getId());
                staffDTO.setFullName(masterRequest.getStaff().getLastname(), masterRequest.getStaff().getFirstname());
                staffDTO.setRole(new RoleDTO(masterRequest.getStaff().getRole().getId(), masterRequest.getStaff().getRole().getTitle()));
                masterRequestDTO.setStaff(staffDTO);
            }
            if (masterRequest.getUser() != null) {
                Hibernate.initialize(masterRequest.getUser().getProfile());
                UserDTO userDTO = new UserDTO();
                userDTO.setId(masterRequest.getUser().getId());
                userDTO.setFullName(masterRequest.getUser().getProfile().getLastname(), masterRequest.getUser().getProfile().getFirstname(), masterRequest.getUser().getProfile().getMiddleName());
                masterRequestDTO.setUser(userDTO);
                masterRequestDTO.setPhoneNumber(masterRequest.getUser().getProfile().getPhoneNumber());
            }
            masterRequestDTO.setCreatedAt(masterRequest.getCreatedAt());
            masterRequestsByPageDTO.getData().add(masterRequestDTO);
        }
        return masterRequestsByPageDTO;
    }

    @Override
    public void saveMasterRequest(MasterRequestDTO masterRequestDTO) {
        MasterRequest masterRequest = new MasterRequest();
        masterRequest.setId(masterRequestDTO.getId());
        masterRequest.setRequestedDate(masterRequestDTO.getRequestedDate());
        masterRequest.setDescription(masterRequestDTO.getDescription());
        masterRequest.setComment(masterRequestDTO.getComment());
        masterRequest.setFlat(flatRepo.findById(masterRequestDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + masterRequestDTO.getFlat().getId())));
        if (masterRequestDTO.getRole().getId() != 0) {
            masterRequest.setRole(roleRepo.findById(masterRequestDTO.getRole().getId()).orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + masterRequestDTO.getRole().getId())));
        }
        if (masterRequestDTO.getStaff().getId() != null) {
            masterRequest.setStaff(staffRepo.findById(masterRequestDTO.getStaff().getId()).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + masterRequestDTO.getStaff().getId())));
        }
        if (masterRequestDTO.getUser().getId() != null) {
            masterRequest.setUser(userRepo.findById(masterRequestDTO.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + masterRequestDTO.getUser().getId())));
        }
        masterRequest.setStatus(MasterRequest.Status.valueOf(masterRequestDTO.getStatus().getValue()));
        masterRequestRepo.save(masterRequest);
    }

    @Override
    public void updateMasterRequest(Long id, MasterRequestDTO masterRequestDTO) {
        MasterRequest masterRequest = masterRequestRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("MasterRequest not found with ID: " + id));
        masterRequest.setId(masterRequestDTO.getId());
        masterRequest.setRequestedDate(masterRequestDTO.getRequestedDate());
        masterRequest.setDescription(masterRequestDTO.getDescription());
        masterRequest.setComment(masterRequestDTO.getComment());
        masterRequest.setFlat(flatRepo.findById(masterRequestDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + masterRequestDTO.getFlat().getId())));
        if (masterRequestDTO.getRole().getId() != 0) {
            masterRequest.setRole(roleRepo.findById(masterRequestDTO.getRole().getId()).orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + masterRequestDTO.getRole().getId())));
        }
        if (masterRequestDTO.getStaff().getId() != null) {
            masterRequest.setStaff(staffRepo.findById(masterRequestDTO.getStaff().getId()).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + masterRequestDTO.getStaff().getId())));
        }
        if (masterRequestDTO.getUser().getId() != null) {
            masterRequest.setUser(userRepo.findById(masterRequestDTO.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + masterRequestDTO.getUser().getId())));
        }
        masterRequest.setStatus(MasterRequest.Status.valueOf(masterRequestDTO.getStatus().getValue()));
        masterRequestRepo.save(masterRequest);
    }

    @Override
    public void deleteMasterRequest(Long id) {
        masterRequestRepo.deleteById(id);
    }

}
