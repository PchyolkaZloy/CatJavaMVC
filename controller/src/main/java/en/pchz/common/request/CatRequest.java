package en.pchz.common.request;

import java.time.LocalDate;

public record CatRequest(String name, LocalDate birthdate, String breed, String color) {
}
