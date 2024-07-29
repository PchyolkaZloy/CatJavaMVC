package en.pchz.dto;

import en.pchz.common.CatColor;

public enum CatColorDto {
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

    public static CatColorDto transferToDto(CatColor color) {
        for (CatColorDto dto : CatColorDto.values()) {
            if (color.toString().equals(dto.toString())) {
                return dto;
            }
        }
        return UNKNOWN;
    }

    public static CatColor transferToEntity(CatColorDto color) {
        for (CatColor entityColor : CatColor.values()) {
            if (color.toString().equals(entityColor.toString())) {
                return entityColor;
            }
        }
        return CatColor.UNKNOWN;
    }

    public static CatColorDto fromString(String colorString) {
        try {
            return CatColorDto.valueOf(colorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CatColorDto.UNKNOWN;
        }
    }
}
