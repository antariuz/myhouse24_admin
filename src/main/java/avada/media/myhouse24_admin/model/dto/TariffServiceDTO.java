package avada.media.myhouse24_admin.model.dto;

import lombok.Data;

@Data
public class TariffServiceDTO {

    private Long id;
    private ServiceDTO service;
    private Double price;

}
