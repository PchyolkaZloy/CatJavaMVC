package en.pchz;

import en.pchz.common.response.CatResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CatResponseMap {
    public static final Map<String, CatResponse> catResponseMap = new HashMap<>() {{
        put("Fluffy",
                new CatResponse(
                        1,
                        "Fluffy",
                        LocalDate.of(2019, 7, 1),
                        "Persian",
                        "WHITE",
                        1
                ));
        put("Whiskers",
                new CatResponse(
                        2,
                        "Fluffy",
                        LocalDate.of(2018, 12, 10),
                        "Siamese",
                        "GRAY",
                        2
                ));
        put("Mittens",
                new CatResponse(
                        3,
                        "Mittens",
                        LocalDate.of(2020, 2, 25),
                        "Maine Coon",
                        "BROWN",
                        1
                ));
        put("Snowball",
                new CatResponse(
                        4,
                        "Snowball",
                        LocalDate.of(2017, 5, 30),
                        "Scottish Fold",
                        "WHITE",
                        3
                ));
    }};
}
