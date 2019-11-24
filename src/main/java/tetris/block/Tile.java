package tetris.block;

import java.util.Random;

public enum Tile {
    EMP("#000000", 0), // Bit shifts are for active tiles in a 5 x 5 matrix. (Could generalise)
    TEE("#a000f0", (1 << 17) | (1 << 13) | (1 << 12) | (1 << 11)),
    BAR("#00f0f0", (1 << 14) | (1 << 13) | (1 << 12) | (1 << 11)),
    LEF("#0000dc", (1 << 18) | (1 << 17) | (1 << 12) | (1 << 7)),
    RIT("#f0a000", (1 << 17) | (1 << 16) | (1 << 12) | (1 << 7)),
    ZIG("#00f000", (1 << 18) | (1 << 17) | (1 << 12) | (1 << 11)),
    ZAG("#eb0000", (1 << 17) | (1 << 16) | (1 << 13) | (1 << 12));

    private final String colour;
    private final int encoding;

    Tile(String colour, int encoding) {
        this.colour = colour;
        this.encoding = encoding;
    }

    public String getColour() {
        return colour;
    }

    public int getEncoding() {
        return encoding;
    }

    public static Tile getRandom() {
        return Tile.values()[new Random().nextInt(Tile.values().length - 1) + 1];
    }
}
