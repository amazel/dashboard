package com.platillogodin.dashboard.domain;

/**
 * Created by Hugo Lezama on August - 2018
 */
public enum MenuDishType {

    STARTER("ENTRADA"), MAIN("PLATO FUERTE"), SIDE("GUARNICION"), BEVERAGE("BEBIDA"), DESSERT("POSTRE"), OTHER("EXTRA");

    private String spanishName;

    MenuDishType(String spanishName) {
        this.spanishName = spanishName;
    }

    public static MenuDishType fromSpanishName(String text) {
        for (MenuDishType type : MenuDishType.values()) {
            if (type.spanishName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
