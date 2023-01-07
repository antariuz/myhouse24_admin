package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.MessageDTO;
import avada.media.myhouse24_admin.model.request.MessageRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;

public interface MessageService {

    ResponseByPage<MessageDTO> getAllMessages(MessageRequest messageRequest);

    void saveMessage(MessageDTO messageDTO);

    void deleteMessage(Long id);

}
