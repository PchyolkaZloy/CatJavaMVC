package en.pchz.common;

public enum CatColor {
    WHITE,
    BLACK,
    GRAY,
    BLUE,
    CHOCOLATE,
    BROWN,
    PURPLE,
    BEIGE,
    RED,
    CREAM,
    YELLOW,
    UNKNOWN;

    public static CatColor fromString(String colorString) {
        try {
            return CatColor.valueOf(colorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CatColor.UNKNOWN;
        }
    }
}
