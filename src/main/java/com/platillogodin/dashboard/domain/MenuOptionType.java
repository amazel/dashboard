package com.platillogodin.dashboard.domain;

/**
 * Created by Hugo Lezama on August - 2018
 */
public enum MenuOptionType {

    MAIN("Plato Fuerte"), SIDE("Guarnición"), STARTER("Entrada"), BEVERAGE("Bebida"), DESSERT("Postre"), OTHER("Extra");

    private final String spanishName;

    MenuOptionType(String spanishName) {
        this.spanishName = spanishName;
    }

    public static MenuOptionType fromSpanishName(String text) {
        for (MenuOptionType type : MenuOptionType.values()) {
            if (type.spanishName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }

    public String getSpanishName() {
        return this.spanishName;
    }
}
