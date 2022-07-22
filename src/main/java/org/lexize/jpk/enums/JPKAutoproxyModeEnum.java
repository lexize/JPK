package org.lexize.jpk.enums;

public enum JPKAutoproxyModeEnum {

    Off ("off"),
    Front ("front"),
    Latch ("latch"),
    Member ("member");

    private String name;
    JPKAutoproxyModeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
