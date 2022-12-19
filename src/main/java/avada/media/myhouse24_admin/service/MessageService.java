package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.MessageDTO;
import avada.media.myhouse24_admin.model.dto.ResponseByPage;

public interface MessageService {

    ResponseByPage<MessageDTO> getAllMessages(Integer pageIndex, Integer pageSize);

    void saveMessage(MessageDTO messageDTO);

    void deleteMessage(Long id);

}
