package avada.media.myhouse24_admin.repo;

import avada.media.myhouse24_admin.model.systemSettings.pages.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Role getRoleByName(String name);

    Set<Role> getRolesByNameNotLike(String name, Sort sort);

    Set<Role> getRolesByNameNotLikeAndNameNotLike(String firstName, String secondName, Sort sort);

}
