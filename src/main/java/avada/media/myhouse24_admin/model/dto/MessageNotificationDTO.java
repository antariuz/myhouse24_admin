package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.MessageNotification;
import lombok.Data;

@Data
public class MessageNotificationDTO {

    private Long id;
    private boolean wasViewed;
    private MessageDTO message;
    private UserDTO user;

    public MessageNotificationDTO messageNotificationToDto(MessageNotification messageNotification) {
        MessageNotificationDTO messageNotificationDTO = new MessageNotificationDTO();
        messageNotificationDTO.setId(messageNotification.getId());
        messageNotificationDTO.setWasViewed(messageNotification.isWasViewed());
//        messageNotificationDTO.setMessage(new MessageDTO().messageToDto(messageNotification.getMessage()));
        messageNotificationDTO.setUser(new UserDTO().userToDto(messageNotification.getUser()));
        return messageNotificationDTO;
    }

}
