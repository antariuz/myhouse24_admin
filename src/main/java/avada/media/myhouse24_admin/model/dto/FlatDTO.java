package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.Floor;
import avada.media.myhouse24_admin.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlatDTO {

    private Long id;
    private String number;
    private Double totalSquare;
    private Floor floor;
    private Section section;
    private BuildingDTO building;
    private UserDTO user;
    private TariffDTO tariff;
    private AccountDTO account;
    private Double balance;
    private String title;

    public void setTitle(Long number, String buildingName) {
        this.title = "№" + number + ", " + buildingName;
    }

    public void setTitle(Long number) {
        this.title = "№" + number;
    }

    public FlatDTO(Long id, Long number) {
        this.id = id;
        this.title = "№" + number;
    }
}
