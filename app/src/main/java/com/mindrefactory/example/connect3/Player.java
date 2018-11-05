package com.mindrefactory.example.connect3;

public enum Player {
    YELLOW("Yellow", 1), RED("Red", 2);

    private final String name;
    private final int intValue;

    Player(final String name, final int intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getPlayerImage() {
        if(this == YELLOW) {
            return R.drawable.yellow;
        }
        return R.drawable.red;
    }

    public int getIntValue() {
        return this.intValue;
    }
}
