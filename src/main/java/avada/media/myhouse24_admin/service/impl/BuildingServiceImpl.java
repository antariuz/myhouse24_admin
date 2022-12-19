package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Building;
import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.dto.BuildingDTO;
import avada.media.myhouse24_admin.model.dto.ResponseByPage;
import avada.media.myhouse24_admin.model.dto.RoleDTO;
import avada.media.myhouse24_admin.model.dto.StaffDTO;
import avada.media.myhouse24_admin.model.request.BuildingRequest;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.repo.BuildingRepo;
import avada.media.myhouse24_admin.repo.FloorRepo;
import avada.media.myhouse24_admin.repo.SectionRepo;
import avada.media.myhouse24_admin.repo.StaffRepo;
import avada.media.myhouse24_admin.service.BuildingService;
import avada.media.myhouse24_admin.spec.BuildingSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepo buildingRepo;
    private final SectionRepo sectionRepo;
    private final FloorRepo floorRepo;
    private final StaffRepo staffRepo;
    private final BuildingSpec buildingSpec;

    @Override
    @Transactional
    public void saveBuilding(Building buildingRequested, List<MultipartFile> images) {
        Building building = buildingRepo.save(new Building());
        building.setTitle(buildingRequested.getTitle());
        building.setAddress(buildingRequested.getAddress());
        buildingRequested.getSections().forEach(section -> building.getSections().add(sectionRepo.save(section)));
        buildingRequested.getFloors().forEach(floor -> building.getFloors().add(floorRepo.save(floor)));
        buildingRequested.getStaff().stream().map(Staff::getId).collect(Collectors.toSet()).forEach(staffId -> building.getStaff().add(staffRepo.findById(staffId).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + staffId))));
        for (int i = 0; i < images.size(); i++) {
            if (!Objects.equals(images.get(i).getOriginalFilename(), "")) {
                try {
                    String changedFileName = "image" + (i + 1) + Objects.requireNonNull(images.get(i).getOriginalFilename()).substring(Objects.requireNonNull(images.get(i).getOriginalFilename()).lastIndexOf("."));
                    FileUtil.saveFile("buildings/" + building.getId(), changedFileName, images.get(i));
                    switch (i) {
                        case 0:
                            building.setImage1(changedFileName);
                            break;
                        case 1:
                            building.setImage2(changedFileName);
                            break;
                        case 2:
                            building.setImage3(changedFileName);
                            break;
                        case 3:
                            building.setImage4(changedFileName);
                            break;
                        case 4:
                            building.setImage5(changedFileName);
                            break;
                    }
                } catch (IOException e) {
                    log.error("Not able to save file. File path: " + e.getMessage());
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateBuilding(Long id, Building buildingRequested, List<MultipartFile> images) {
        sectionRepo.saveAll(buildingRequested.getSections());
        floorRepo.saveAll(buildingRequested.getFloors());
        buildingRequested
                .setStaff(buildingRequested.getStaff().stream().map(Staff::getId).collect(Collectors.toSet())
                        .stream().map(staffId -> staffRepo.findById(staffId)
                                .orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + staffId)))
                        .collect(Collectors.toList()));
        for (int i = 0; i < images.size(); i++) {
            if (!Objects.equals(images.get(i).getOriginalFilename(), "")) {
                try {
                    String changedFileName = "image" + (i + 1) + Objects.requireNonNull(images.get(i).getOriginalFilename()).substring(Objects.requireNonNull(images.get(i).getOriginalFilename()).lastIndexOf("."));
                    FileUtil.saveFile("buildings/" + buildingRequested.getId(), changedFileName, images.get(i));
                    switch (i) {
                        case 0:
                            buildingRequested.setImage1(changedFileName);
                            break;
                        case 1:
                            buildingRequested.setImage2(changedFileName);
                            break;
                        case 2:
                            buildingRequested.setImage3(changedFileName);
                            break;
                        case 3:
                            buildingRequested.setImage4(changedFileName);
                            break;
                        case 4:
                            buildingRequested.setImage5(changedFileName);
                            break;
                    }
                } catch (IOException e) {
                    log.error("Not able to save file. File path: " + e.getMessage());
                }
            } else {
                Building building = buildingRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Building not found with ID: " + id));
                switch (i) {
                    case 0:
                        buildingRequested.setImage1(building.getImage1());
                        break;
                    case 1:
                        buildingRequested.setImage2(building.getImage2());
                        break;
                    case 2:
                        buildingRequested.setImage3(building.getImage3());
                        break;
                    case 3:
                        buildingRequested.setImage4(building.getImage4());
                        break;
                    case 4:
                        buildingRequested.setImage5(building.getImage5());
                        break;
                }
            }
        }
        buildingRepo.save(buildingRequested);
    }

    @Override
    @Transactional
    public ResponseByPage<BuildingDTO> getAllBuildingsDTO(BuildingRequest buildingRequest) {
        Page<Building> buildingsPage =
                buildingRepo.findAll(buildingSpec.getBuildings(buildingRequest), PageRequest.of(buildingRequest.getPageIndex() - 1, buildingRequest.getPageSize()));
        ResponseByPage<BuildingDTO> buildingsByPageDTO = new ResponseByPage<>();
        buildingsByPageDTO.setItemsCount(buildingsPage.getTotalElements());
        for (Building building : buildingsPage) {
            Hibernate.initialize(building.getSections());
            Hibernate.initialize(building.getFloors());
            Hibernate.initialize(building.getStaff());
            BuildingDTO buildingDTO = new BuildingDTO();
            buildingDTO.setId(building.getId());
            buildingDTO.setTitle(building.getTitle());
            buildingDTO.setAddress(building.getAddress());
            buildingDTO.setImage1(building.getImage1());
            buildingDTO.setImage2(building.getImage2());
            buildingDTO.setImage3(building.getImage3());
            buildingDTO.setImage4(building.getImage4());
            buildingDTO.setImage5(building.getImage5());
            buildingDTO.setSections(building.getSections());
            buildingDTO.setFloors(building.getFloors());
            List<StaffDTO> staffDTOList = new ArrayList<>();
            for (Staff staff : building.getStaff()) {
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(staff.getId());
                staffDTO.setFullName(staff.getFirstname() + " " + staff.getLastname());
                staffDTO.setRole(new RoleDTO(staff.getRole().getId(), staff.getRole().getTitle()));
                staffDTOList.add(staffDTO);
            }
            buildingDTO.setStaffList(staffDTOList);
            buildingsByPageDTO.getData().add(buildingDTO);
        }
        return buildingsByPageDTO;
    }

    @Override
    public void deleteBuildingById(Long id) {
        try {
            FileUtil.deleteFolder(Paths.get("uploaded/buildings/" + id));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        buildingRepo.deleteById(id);
    }

    @Transactional
    public List<BuildingDTO> getAllBuildingsDTO() {
        return buildingRepo
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(building -> {
                    Hibernate.initialize(building.getSections());
                    Hibernate.initialize(building.getFloors());
                    BuildingDTO buildingDTO = new BuildingDTO();
                    buildingDTO.setId(building.getId());
                    buildingDTO.setTitle(building.getTitle());
                    buildingDTO.setSections(building.getSections());
                    buildingDTO.setFloors(building.getFloors());
                    return buildingDTO;
                })
                .collect(Collectors.toList());
    }

}
