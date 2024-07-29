package en.pchz.common.request;

import java.time.LocalDate;

public record CatMasterRequest(String name, LocalDate birthdate) {
}
