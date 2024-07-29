package en.pchz.common.response;

import java.time.LocalDate;
import java.util.List;

public record CatMasterResponse(Integer id, String name, LocalDate birthdate, List<CatResponse> cats) {
}
