package en.pchz.common.response;

import java.time.LocalDate;

public record CatResponse(
        Integer id,
        String name,
        LocalDate birthdate,
        String breed,
        String color,
        Integer catMasterId) {
}
