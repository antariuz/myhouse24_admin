package avada.media.myhouse24_admin.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    NEW("Новый"),
    ACTIVE("Активен"),
    INACTIVE("Отключен");

    private final String title;

}
