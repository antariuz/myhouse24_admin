package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.MasterRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRequestRepo extends JpaRepository<MasterRequest, Long>, JpaSpecificationExecutor<MasterRequest> {

    long countMasterRequestsByStatus(MasterRequest.Status status);

}
