package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.MasterRequestDTO;
import avada.media.myhouse24_admin.model.request.MasterRequestRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface MasterRequestService {

    ResponseByPage<MasterRequestDTO> getAllMasterRequests(MasterRequestRequest MasterRequestRequest);

    void saveMasterRequest(@RequestBody MasterRequestDTO masterRequestDTO);

    void updateMasterRequest(@PathVariable Long id, MasterRequestDTO masterRequestDTO);

}
