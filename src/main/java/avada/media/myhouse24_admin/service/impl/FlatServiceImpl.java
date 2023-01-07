package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.*;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.FlatRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.repo.*;
import avada.media.myhouse24_admin.repo.systemSettings.TariffRepo;
import avada.media.myhouse24_admin.service.FlatService;
import avada.media.myhouse24_admin.spec.FlatSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlatServiceImpl implements FlatService {

    private final FlatRepo flatRepo;
    private final BuildingRepo buildingRepo;
    private final SectionRepo sectionRepo;
    private final FloorRepo floorRepo;
    private final AccountRepo accountRepo;
    private final UserRepo userRepo;
    private final TariffRepo tariffRepo;
    private final FlatSpec flatSpec;

    @Override
    @Transactional
    public ResponseByPage<FlatDTO> getAllFlats(FlatRequest flatRequest) {
        Page<Flat> flatsPage =
                flatRepo.findAll(flatSpec.getFlats(flatRequest), PageRequest.of(flatRequest.getPageIndex() - 1, flatRequest.getPageSize()));
        ResponseByPage<FlatDTO> flatsByPageDTO = new ResponseByPage<>();
        flatsByPageDTO.setItemsCount(flatsPage.getTotalElements());
        for (Flat flat : flatsPage) {
            FlatDTO flatDTO = new FlatDTO();
            flatDTO.setId(flat.getId());
            flatDTO.setNumber(flat.getNumber().toString());
            flatDTO.setTotalSquare(flat.getTotalSquare());
            BuildingDTO buildingDTO = new BuildingDTO();
            buildingDTO.setId(flat.getBuilding().getId());
            buildingDTO.setTitle(flat.getBuilding().getTitle());
            flatDTO.setBuilding(buildingDTO);
            flatDTO.setSection(flat.getSection());
            flatDTO.setFloor(flat.getFloor());
            if (flat.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(flat.getUser().getId());
                Hibernate.initialize(flat.getUser().getProfile());
                userDTO.setFullName(flat.getUser().getProfile().getLastname(), flat.getUser().getProfile().getFirstname(), flat.getUser().getProfile().getMiddleName());
                flatDTO.setUser(userDTO);
            }
            if (flat.getTariff() != null) {
                TariffDTO tariffDTO = new TariffDTO();
                tariffDTO.setId(flat.getTariff().getId());
                tariffDTO.setName(flat.getTariff().getName());
                flatDTO.setTariff(tariffDTO);
            }
            if (flat.getAccount() != null) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setId(flat.getAccount().getId());
                accountDTO.setUniqueNumber(flat.getAccount().getUniqueNumber());
                flatDTO.setAccount(accountDTO);
                flatDTO.setBalance(flat.getAccount().getBalance());
            }
            flatsByPageDTO.getData().add(flatDTO);
        }
        return flatsByPageDTO;
    }

    @Override
    @Transactional
    public void saveFlat(FlatDTO flatRequested) {
        Flat flat = new Flat();
        flat.setId(flatRequested.getId());
        flat.setNumber(Long.parseLong(flatRequested.getNumber()));
        flat.setTotalSquare(flatRequested.getTotalSquare());
        Building building = buildingRepo.findById(flatRequested.getBuilding().getId()).orElseThrow(() -> new EntityNotFoundException("Building not found with ID: " + flatRequested.getBuilding().getId()));
        flat.setBuilding(building);
        Section section;
        if (flatRequested.getSection().getId() != null) {
            section = sectionRepo.findById(flatRequested.getSection().getId()).orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + flatRequested.getSection().getId()));
            flat.setSection(section);
        }
        if (flatRequested.getFloor().getId() != null)
            flat.setFloor(floorRepo.findById(flatRequested.getFloor().getId()).orElseThrow(() -> new EntityNotFoundException("Floor not found with ID: " + flatRequested.getFloor().getId())));
        User user;
        if (flatRequested.getUser().getId() != null) {
            user = userRepo.findById(flatRequested.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + flatRequested.getUser().getId()));
            flat.setUser(user);
        }
        if (flatRequested.getTariff().getId() != null)
            flat.setTariff(tariffRepo.findById(flatRequested.getTariff().getId()).orElseThrow(() -> new EntityNotFoundException("Tariff not found with ID: " + flatRequested.getTariff().getId())));
        if (flatRequested.getAccount().getUniqueNumber() != null) {
            Account account = accountRepo.findByUniqueNumber(flatRequested.getAccount().getUniqueNumber());
            if (account != null) {
                account.setFlat(flat);
                flat.setAccount(account);
                accountRepo.save(account);
            } else {
                Account newAccount = new Account();
                newAccount.setUniqueNumber(flatRequested.getAccount().getUniqueNumber());
                newAccount.setStatus(Account.Status.ACTIVE);
                newAccount.setBalance(0d);
                newAccount.setFlat(flat);
                accountRepo.save(newAccount);
                flat.setAccount(newAccount);
            }
        }
        flatRepo.save(flat);
    }

    @Override
    public void updateFlat(Long id, FlatDTO flatRequested) {
        Flat flat = flatRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + id));
        flat.setNumber(Long.parseLong(flatRequested.getNumber()));
        flat.setTotalSquare(flatRequested.getTotalSquare());
        flat.setBuilding(buildingRepo.findById(flatRequested.getBuilding().getId()).orElseThrow(() -> new EntityNotFoundException("Building not found with ID: " + flatRequested.getBuilding().getId())));
        if (flatRequested.getSection().getId() != null)
            flat.setSection(sectionRepo.findById(flatRequested.getSection().getId()).orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + flatRequested.getSection().getId())));
        if (flatRequested.getFloor().getId() != null)
            flat.setFloor(floorRepo.findById(flatRequested.getFloor().getId()).orElseThrow(() -> new EntityNotFoundException("Floor not found with ID: " + flatRequested.getFloor().getId())));
        if (flatRequested.getUser().getId() != null)
            flat.setUser(userRepo.findById(flatRequested.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + flatRequested.getUser().getId())));
        if (flatRequested.getTariff().getId() != null)
            flat.setTariff(tariffRepo.findById(flatRequested.getTariff().getId()).orElseThrow(() -> new EntityNotFoundException("Tariff not found with ID: " + flatRequested.getTariff().getId())));
        if (flatRequested.getAccount().getUniqueNumber() != null) {
            Account account = accountRepo.findByUniqueNumber(flatRequested.getAccount().getUniqueNumber());
            if (account != null) flat.setAccount(account);
            else {
                Account newAccount = new Account();
                newAccount.setUniqueNumber(flatRequested.getAccount().getUniqueNumber());
                newAccount.setStatus(Account.Status.ACTIVE);
                newAccount.setBalance(0d);
                newAccount.setFlat(flat);
                accountRepo.save(newAccount);
                flat.setAccount(newAccount);
            }
        }
        flatRepo.save(flat);
    }

    @Transactional
    public List<FlatDTO> getAllFlats(Long buildingId, Long sectionId, Long floorId) {
        List<Flat> flats;
        if (sectionId != null) {
            if (floorId != null)
                flats = flatRepo.findAllByBuildingIdAndSectionIdAndFloorId(buildingId, sectionId, floorId);
            else flats = flatRepo.findAllByBuildingIdAndSectionId(buildingId, sectionId);
        } else {
            if (floorId != null) flats = flatRepo.findAllByBuildingIdAndFloorId(buildingId, floorId);
            else flats = flatRepo.findAllByBuildingId(buildingId);
        }
        return flats
                .stream()
                .map(flat -> {
                    FlatDTO flatDTO = new FlatDTO(flat.getId(), flat.getNumber());
                    if (flat.getUser() != null) {
                        Hibernate.initialize(flat.getUser().getProfile());
                        UserDTO userDTO = new UserDTO();
                        userDTO.setId(flat.getUser().getId());
                        userDTO.setFullName(flat.getUser().getProfile().getLastname(), flat.getUser().getProfile().getFirstname(), flat.getUser().getProfile().getMiddleName());
                        userDTO.setPhoneNumber(flat.getUser().getProfile().getPhoneNumber());
                        flatDTO.setUser(userDTO);
                    }
                    if (flat.getAccount() != null) {
                        AccountDTO accountDTO = new AccountDTO();
                        accountDTO.setId(flat.getAccount().getId());
                        accountDTO.setUniqueNumber(flat.getAccount().getUniqueNumber());
                        flatDTO.setAccount(accountDTO);
                    }
                    return flatDTO;
                })
                .collect(Collectors.toList());
    }

}
