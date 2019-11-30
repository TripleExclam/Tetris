package tetris.block;

import java.util.Random;

public enum Tile {
    EMP("#000000", 0, 1), // Bit shifts are for active tiles in a 5 x 5 matrix. (Could generalise)
    TEE("#a000f0", (1 << 3) | (1 << 4) | (1 << 5) | (1 << 7), 3),
    BAR("#00f0f0", (1 << 2) | (1 << 6) | (1 << 10) | (1 << 14), 4),
    RIT("#0000dc", (1 << 1) | (1 << 4) | (1 << 7) | (1 << 8), 3),
    LEF("#f0a000", (1 << 1) | (1 << 4) | (1 << 7) | (1 << 6), 3),
    ZIG("#00f000", (1 << 5) | (1 << 4) | (1 << 7) | (1 << 6), 3),
    ZAG("#eb0000", (1 << 8) | (1 << 7) | (1 << 4) | (1 << 3), 3),
    BOK("#f0f000", 1 | (1 << 1) | (1 << 2) | (1 << 3), 2);

    private static final Random RANDOM = new Random();

    private final String colour;
    private final int encoding;
    private final int dimension;

    Tile(String colour, int encoding, int dimension) {
        this.colour = colour;
        this.encoding = encoding;
        this.dimension = dimension;
    }

    public String getColour() {
        return colour;
    }

    public int getDimension() {
        return dimension;
    }

    public int getEncoding() {
        return encoding;
    }

    public static Tile getRandom() {
        return Tile.values()[RANDOM.nextInt(Tile.values().length - 1) + 1];
    }

    public static Tile getRandom(Random rand) {
        return Tile.values()[rand.nextInt(Tile.values().length - 1) + 1];
    }
}
