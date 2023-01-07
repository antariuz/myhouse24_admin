package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.CounterDTO;
import avada.media.myhouse24_admin.model.dto.StatusDTO;
import avada.media.myhouse24_admin.model.request.CounterRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;

import java.util.List;

public interface CounterService {

    ResponseByPage<CounterDTO> getAllCounters(CounterRequest counterRequest);

    String getNewUniqueNumber();

    boolean isUniqueNumberNotExists(Long id, String number);

    List<StatusDTO> getAllStatus();

    void saveCounter(CounterDTO counterDTO);

    void updateCounter(Long id, CounterDTO counterDTO);

    void deleteCounter(Long id);

}
