package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.service.BuildingService;
import avada.media.myhouse24_admin.service.FlatService;
import avada.media.myhouse24_admin.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("messages")
public class MessagesPageController {

    private final MessageService messageService;
    private final BuildingService buildingService;
    private final FlatService flatService;

    @GetMapping({"/", ""})
    public ModelAndView showMessagesPage() {
        return new ModelAndView("pages/messages");
    }

    @GetMapping("get-all-messages")
    public @ResponseBody ResponseByPage<MessageDTO> getAllMessages(@RequestParam(required = false) Integer pageIndex,
                                                                   @RequestParam(required = false) Integer pageSize) {
        return messageService.getAllMessages(pageIndex, pageSize);
    }

    @GetMapping("get-all-buildings")
    public @ResponseBody List<BuildingDTO> getAllBuildings() {
        return buildingService.getAllBuildingsDTO();
    }

    @GetMapping("get-all-flats-by-building-and-section-and-floor")
    public @ResponseBody List<FlatDTO> getAllFlatsByBuildingAndSectionAndFloor(Long buildingId,
                                                                               @RequestParam(required = false) Long sectionId,
                                                                               @RequestParam(required = false) Long floorId) {
        return flatService.getAllFlats(buildingId, sectionId, floorId);
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveMessage(@RequestBody MessageDTO messageDTO, Principal principal) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setEmail(principal.getName());
        messageDTO.setStaff(staffDTO);
        messageService.saveMessage(messageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }


}
