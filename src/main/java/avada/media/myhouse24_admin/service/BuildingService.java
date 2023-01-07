package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.Building;
import avada.media.myhouse24_admin.model.dto.BuildingDTO;
import avada.media.myhouse24_admin.model.request.BuildingRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BuildingService {

    void saveBuilding(Building building, List<MultipartFile> images);

    void updateBuilding(Long id, Building building, List<MultipartFile> images);

    ResponseByPage<BuildingDTO> getAllBuildingsDTO(BuildingRequest buildingRequest);

    void deleteBuildingById(Long id);

    List<BuildingDTO> getAllBuildingsDTO();

}
