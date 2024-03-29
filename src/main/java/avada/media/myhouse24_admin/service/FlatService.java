package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.FlatDTO;
import avada.media.myhouse24_admin.model.request.FlatRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;

import java.util.List;

public interface FlatService {

    ResponseByPage<FlatDTO> getAllFlats(FlatRequest flatRequest);

    void saveFlat(FlatDTO flat);

    void updateFlat(Long id, FlatDTO flat);

    List<FlatDTO> getAllFlats(Long buildingId, Long sectionId, Long floorId);

}
