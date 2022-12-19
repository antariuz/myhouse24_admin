package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.MasterRequest;
import avada.media.myhouse24_admin.model.dto.StatisticsDTO;
import avada.media.myhouse24_admin.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("statistics")
public class StatisticsPageController {

    private final BuildingRepo buildingRepo;
    private final UserRepo userRepo;
    private final MasterRequestRepo masterRequestRepo;
    private final FlatRepo flatRepo;
    private final AccountRepo accountRepo;

    @GetMapping({"/", ""})
    public ModelAndView showStatisticsPage() {
        return new ModelAndView("pages/statistics");
    }

    @GetMapping("get-data")
    public @ResponseBody StatisticsDTO getStatisticsData() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setBuildingsCount(buildingRepo.count());
        statisticsDTO.setActiveUsersCount(userRepo.countUsersByStatusNotInactive());
        statisticsDTO.setMasterRequestsCount(masterRequestRepo.countMasterRequestsByStatus(MasterRequest.Status.IN_WORK));
        statisticsDTO.setFlatsCount(flatRepo.count());
        statisticsDTO.setAccountsCount(accountRepo.count());
        statisticsDTO.setNewMasterRequestsCount(masterRequestRepo.countMasterRequestsByStatus(MasterRequest.Status.NEW));
        statisticsDTO.setAccountsTotalDebt(0.00d);
        statisticsDTO.setAccountsTotalBalance(0.00d);
        statisticsDTO.setAccountsTotalTransaction(0.00d);
        return statisticsDTO;
    }

}
