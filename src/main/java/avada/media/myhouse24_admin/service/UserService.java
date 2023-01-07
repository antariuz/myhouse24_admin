package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.dto.UserDTO;
import avada.media.myhouse24_admin.model.request.SelectResponse;
import avada.media.myhouse24_admin.model.request.UserRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    ResponseByPage<UserDTO> getAllUsersByPage(UserRequest userRequest);

    String getNewUniqueID();

    boolean checkEmail(Long id, String email);
    boolean checkUniqueId(Long id, String uniqueId);
    SelectResponse searchForUser(String query, Integer page);
    void saveUser(User user, MultipartFile profileImage);
    void deleteUserById(Long id);
    void updateUser(Long id, User user, MultipartFile profileImage);
    List<UserDTO> getNewUsers();
    UserDTO getUser(Long id);
    UserDTO getUserByAccountId(Long accountId);

}
