package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Flat;
import avada.media.myhouse24_admin.model.Message;
import avada.media.myhouse24_admin.model.MessageNotification;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.dto.MessageDTO;
import avada.media.myhouse24_admin.model.dto.ResponseByPage;
import avada.media.myhouse24_admin.model.dto.StaffDTO;
import avada.media.myhouse24_admin.model.request.UserRequest;
import avada.media.myhouse24_admin.repo.*;
import avada.media.myhouse24_admin.service.MessageService;
import avada.media.myhouse24_admin.spec.UserSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;
    private final StaffRepo staffRepo;
    private final UserRepo userRepo;
    private final UserSpec userSpec;
    private final BuildingRepo buildingRepo;
    private final SectionRepo sectionRepo;
    private final FloorRepo floorRepo;
    private final FlatRepo flatRepo;
    private final MessageNotificationRepo messageNotificationRepo;

    @Override
    public ResponseByPage<MessageDTO> getAllMessages(Integer pageIndex, Integer pageSize) {
        Page<Message> messages =
                messageRepo.findAll(PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.ASC, "id")));
        ResponseByPage<MessageDTO> response = new ResponseByPage<>();
        response.setItemsCount(messages.getTotalElements());
        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setSubject(message.getSubject());
            messageDTO.setText(message.getText());
            messageDTO.setCreatedAt(message.getCreatedAt());
            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setEmail(message.getStaff().getEmail());
            staffDTO.setFullName(message.getStaff().getLastname(), message.getStaff().getFirstname());
            messageDTO.setStaff(staffDTO);
            messageDTO.setToWhom(message.getToWhom());
            response.getData().add(messageDTO);
        }
        return response;
    }

    @Override
    @Transactional
    public void saveMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setSubject(messageDTO.getSubject());
        message.setText(messageDTO.getText());
        message.setStaff(staffRepo.getStaffByEmail(messageDTO.getStaff().getEmail()));
        UserRequest userRequest = new UserRequest();
        userRequest.setHasDebt(messageDTO.isHaveDebt());
        if (messageDTO.getBuilding() != null) {
            userRequest.setBuilding(messageDTO.getBuilding().getId());
            String buildingTitle = buildingRepo.findById(messageDTO.getBuilding().getId()).orElseThrow(() -> new EntityNotFoundException("Building not found with ID: " + messageDTO.getBuilding().getId())).getTitle();
            String sectionTitle = null;
            String floorTitle = null;
            String flatTitle = null;
            if (messageDTO.getSection() != null) {
                userRequest.setSection(messageDTO.getSection().getId());
                sectionTitle = sectionRepo.findById(messageDTO.getSection().getId()).orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + messageDTO.getSection().getId())).getName();
            }
            if (messageDTO.getFloor() != null) {
                userRequest.setFloor(messageDTO.getFloor().getId());
                floorTitle = floorRepo.findById(messageDTO.getFloor().getId()).orElseThrow(() -> new EntityNotFoundException("Floor not found with ID: " + messageDTO.getFloor().getId())).getName();
            }
            if (messageDTO.getFlat() != null) {
                userRequest.setFlat(messageDTO.getFlat().getId());
                Flat flat = flatRepo.findById(messageDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + messageDTO.getFlat().getId()));
                if (flat != null) {
                    flatTitle = flat.getNumber().toString();
                    sectionTitle = flat.getSection().getName();
                    floorTitle = flat.getFloor().getName();
                }
            }
            message.setToWhom(buildingTitle + (sectionTitle != null ? ", " + sectionTitle : "") + (floorTitle != null ? ", " + floorTitle : "") + (flatTitle != null ? ", кв." + flatTitle : ""));
        } else {
            message.setToWhom("Всем");
        }
        List<User> users = userRepo.findAll(userSpec.getUsers(userRequest));
        message.setUsers(users);
        List<MessageNotification> messageNotifications = new ArrayList<>();
        users.forEach(user -> {
            MessageNotification messageNotification = new MessageNotification();
            messageNotification.setWasViewed(false);
            messageNotification.setMessage(message);
            messageNotification.setUser(user);
            messageNotifications.add(messageNotification);
        });
        messageNotificationRepo.saveAll(messageNotifications);
        message.setMessageNotifications(messageNotifications);
        messageRepo.save(message);
        log.info("Message with ID {} was successfully saved", message.getId());
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepo.deleteById(id);
        log.info("Message with id {} was successfully deleted", id);
    }

}
