package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Flat;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.common.FileUtil;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.SelectResponse;
import avada.media.myhouse24_admin.model.request.UserRequest;
import avada.media.myhouse24_admin.repo.FlatRepo;
import avada.media.myhouse24_admin.repo.ProfileRepo;
import avada.media.myhouse24_admin.repo.UserRepo;
import avada.media.myhouse24_admin.service.UserService;
import avada.media.myhouse24_admin.spec.UserSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserSpec userSpec;

    private final ProfileRepo profileRepo;

    private final PasswordEncoder passwordEncoder;
    private final FlatRepo flatRepo;

    @Override
    @Transactional
    public ResponseByPage<UserDTO> getAllUsersByPage(UserRequest userRequest) {
        Page<User> userPage =
                userRepo.findAll(userSpec.getUsers(userRequest), PageRequest.of(userRequest.getPageIndex() - 1, userRequest.getPageSize()));
        ResponseByPage<UserDTO> userByPageDTO = new ResponseByPage<>();
        userByPageDTO.setItemsCount(userPage.getTotalElements());
        for (User user : userPage) {
            Hibernate.initialize(user.getProfile());
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUniqueId(user.getUniqueId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFullName(user.getProfile().getLastname() + " " + user.getProfile().getFirstname() + " " + user.getProfile().getMiddleName());
            userDTO.setFirstname(user.getProfile().getFirstname());
            userDTO.setMiddleName(user.getProfile().getMiddleName());
            userDTO.setLastname(user.getProfile().getLastname());
            if (user.getProfile().getBirthdate() != null)
                userDTO.setBirthdate(user.getProfile().getBirthdate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            userDTO.setNotes(user.getProfile().getNotes());
            userDTO.setPhoneNumber(user.getProfile().getPhoneNumber());
            userDTO.setViberLogin(user.getProfile().getViberLogin());
            userDTO.setTelegramLogin(user.getProfile().getTelegramLogin());
            userDTO.setProfileImage(user.getProfile().getProfileImage());
            userDTO.setHasDebt(userRepo.getUserBalancesByUserId(user.getId()).stream().filter(Objects::nonNull).anyMatch(balance -> balance < 0));
            userDTO.setCreatedAt(new SimpleDateFormat("dd.MM.yyyy").format(user.getCreatedAt()));
            userDTO.setStatus(new StatusDTO(user.getStatus().name(), user.getStatus().getTitle()));
            List<Flat> flats = flatRepo.findAllByUserId(user.getId());
            if (!flats.isEmpty()) {
                List<FlatDTO> flatDTOS = new ArrayList<>();
                Set<BuildingDTO> buildingDTOS = new HashSet<>();
                for (Flat flat : flats) {
                    FlatDTO flatDTO = new FlatDTO();
                    flatDTO.setId(flat.getId());
                    flatDTO.setTitle(flat.getNumber(), flat.getBuilding().getTitle());
                    flatDTOS.add(flatDTO);
                    BuildingDTO buildingDTO = new BuildingDTO();
                    buildingDTO.setId(flat.getBuilding().getId());
                    buildingDTO.setTitle(flat.getBuilding().getTitle());
                    buildingDTOS.add(buildingDTO);
                }
                userDTO.setFlats(flatDTOS);
                userDTO.setBuildings(buildingDTOS);
            }
            if (userDTO.getHasDebt() != user.isHasDebt()) {
                user.setHasDebt(userDTO.getHasDebt());
                userRepo.save(user);
            }
            userByPageDTO.getData().add(userDTO);
        }
        return userByPageDTO;
    }

    @Override
    public String getNewUniqueID() {
        int QTY_OF_ZEROS = 6;
        Long lastId = userRepo.getLastId();
        Long newId = (lastId != null ? lastId : 0) + 1L;
        StringBuilder uniqueId = new StringBuilder();
        for (int i = 0; i < QTY_OF_ZEROS - String.valueOf(newId).length(); i++) {
            uniqueId.append("0");
        }
        uniqueId.append(newId);
        return uniqueId.toString();
    }

    @Override
    public boolean checkEmail(Long id, String email) {
        if (id != null && email.equals(userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id))
                .getEmail())) {
            return true;
        } else return !userRepo.existsByEmail(email);
    }

    @Override
    public boolean checkUniqueId(Long id, String uniqueId) {
        if (id != null && uniqueId.equals(userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id))
                .getUniqueId())) {
            return true;
        } else return !userRepo.existsByUniqueId(uniqueId);
    }

    @Override
    @Transactional
    public SelectResponse searchForUser(String query, Integer page) {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullName(query);
        userRequest.setPageIndex(page);
        userRequest.setPageSize(10);
        Page<User> users = userRepo.findAll(userSpec.getUsers(userRequest), PageRequest.of(userRequest.getPageIndex() - 1, userRequest.getPageSize()));
        SelectResponse selectResponse = new SelectResponse();
        if (!users.getContent().isEmpty()) {
            List<SelectResponse.Result> resultsList = selectResponse.getResults();
            for (User user : users) {
                Hibernate.initialize(user.getProfile());
                SelectResponse.Result result = new SelectResponse.Result();
                result.setId(user.getId());
                result.setText(user.getProfile().getLastname(), user.getProfile().getFirstname(), user.getProfile().getMiddleName());
                resultsList.add(result);
            }
            selectResponse.setResults(resultsList);
        }
        selectResponse.setItemsCount(users.getTotalElements());
        return selectResponse;
    }

    @Override
    @Transactional
    public void saveUser(User user, MultipartFile profileImage) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (profileImage != null) {
            try {
                String changedFileName = "profile-image-" + user.getId() + Objects.requireNonNull(profileImage.getOriginalFilename()).substring(profileImage.getOriginalFilename().lastIndexOf("."));
                FileUtil.saveFile("users", changedFileName, profileImage);
                user.getProfile().setProfileImage(changedFileName);
            } catch (IOException e) {
                log.error("Not able to save file. File path: " + e.getMessage());
            }
        } else if (user.getId() != null) {
            String previousProfileImage = userRepo.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + user.getId())).getProfile().getProfileImage();
            user.getProfile().setProfileImage(previousProfileImage);
        }
        profileRepo.save(user.getProfile());
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User userRequested, MultipartFile profileImage) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        user.setUniqueId(userRequested.getUniqueId());
        user.setEmail(userRequested.getEmail());
        user.setStatus(userRequested.getStatus());
        user.setPassword(passwordEncoder.encode(userRequested.getPassword()));
        Hibernate.initialize(user.getProfile());
        user.getProfile().setFirstname(userRequested.getProfile().getFirstname());
        user.getProfile().setMiddleName(userRequested.getProfile().getMiddleName());
        user.getProfile().setLastname(userRequested.getProfile().getLastname());
        user.getProfile().setPhoneNumber(userRequested.getProfile().getPhoneNumber());
        user.getProfile().setBirthdate(userRequested.getProfile().getBirthdate());
        user.getProfile().setViberLogin(userRequested.getProfile().getViberLogin());
        user.getProfile().setTelegramLogin(userRequested.getProfile().getTelegramLogin());
        user.getProfile().setNotes(userRequested.getProfile().getNotes());
        if (profileImage != null) {
            try {
                String changedFileName = "profile-image-" + id + Objects.requireNonNull(profileImage.getOriginalFilename()).substring(profileImage.getOriginalFilename().lastIndexOf("."));
                FileUtil.saveFile("users", changedFileName, profileImage);
                user.getProfile().setProfileImage(changedFileName);
            } catch (IOException e) {
                log.error("Not able to save file. File path: " + e.getMessage());
            }
        }
        profileRepo.save(user.getProfile());
        userRepo.save(user);
    }

    @Override
    @Transactional
    public List<UserDTO> getNewUsers() {
        return userRepo.getNewUsers().stream().map(user -> {
            Hibernate.initialize(user.getProfile());
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFullName(user.getProfile().getLastname(), user.getProfile().getFirstname(), user.getProfile().getMiddleName());
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        Hibernate.initialize(user.getProfile());
        if (user.getProfile().getProfileImage() != null) {
            try {
                Files.delete(Paths.get("uploaded/users/" + user.getProfile().getProfileImage()));
            } catch (IOException e) {
                log.error("Not able to delete file. File path: " + e.getMessage());
            }
        }
        userRepo.delete(user);
    }

}
