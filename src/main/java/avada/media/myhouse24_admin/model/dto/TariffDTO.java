package avada.media.myhouse24_admin.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TariffDTO {

    private Long id;
    private String name;
    private String description;
    private String updatedAt;
    private List<TariffServiceDTO> tariffServices = new ArrayList<>();

    public TariffDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
