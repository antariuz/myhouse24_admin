package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.Status;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.dto.StatusDTO;
import avada.media.myhouse24_admin.model.dto.UserDTO;
import avada.media.myhouse24_admin.model.request.SelectResponse;
import avada.media.myhouse24_admin.model.request.UserRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersPageController {

    private final UserService userService;

    @GetMapping({"/", ""})
    public ModelAndView showUsersPage() {
        return new ModelAndView("pages/users");
    }

    @GetMapping("get-all-users")
    public @ResponseBody ResponseByPage<UserDTO> getAllUsers(UserRequest userRequest) {
        return userService.getAllUsersByPage(userRequest);
    }

    @GetMapping("get-all-status")
    public @ResponseBody List<StatusDTO> getAllStatus() {
        if (Status.values().length != 0) {
            List<StatusDTO> statusDTOList = new ArrayList<>();
            for (Status status : Status.values()) {
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setValue(status.name());
                statusDTO.setTitle(status.getTitle());
                statusDTOList.add(statusDTO);
            }
            return statusDTOList;
        } else return new ArrayList<>();
    }

    @GetMapping("get-new-unique-id")
    public @ResponseBody String getNewUniqueID() {
        return userService.getNewUniqueID();
    }

    @PostMapping("email-check")
    public @ResponseBody boolean checkEmail(@RequestParam(required = false) Long id, String email) {
        return userService.checkEmail(id, email);
    }

    @PostMapping("unique-id-check")
    public @ResponseBody boolean checkUniqueId(@RequestParam(required = false) Long id, String uniqueId) {
        return userService.checkUniqueId(id, uniqueId);
    }

    @GetMapping("search-user")
    public @ResponseBody SelectResponse searchForUser(@RequestParam String query,
                                                      @RequestParam Integer page) {
        return userService.searchForUser(query, page);
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveUser(@RequestPart User user,
                                         @RequestPart(required = false) MultipartFile profileImage) {
        userService.saveUser(user, profileImage);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateUser(@PathVariable Long id,
                                           @RequestPart User user,
                                           @RequestPart(required = false) MultipartFile profileImage) {
        userService.updateUser(id, user, profileImage);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-new-users")
    public @ResponseBody List<UserDTO> getNewUsersCount() {
        return userService.getNewUsers();
    }

    @GetMapping("get-user")
    public @ResponseBody UserDTO getUser(Long id) {
        return userService.getUser(id);
    }

    @GetMapping("get-user-by-account-id")
    public @ResponseBody UserDTO getUserByAccountId(Long accountId) {
        return userService.getUserByAccountId(accountId);
    }

}
