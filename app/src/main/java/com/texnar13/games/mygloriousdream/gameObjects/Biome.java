package com.texnar13.games.mygloriousdream.gameObjects;

public class Biome {
    // ---------------- константы ----------------
    // --- тип биома ---
    // травяной
    public static final int BIOME_TYPE_GRASS = 0;

    // --- блоки ---
    // воздух
    public static final int BLOCK_AIR = 0;
    // земля
    public static final int BLOCK_DIRT = 1;


    // ---------------- переменные ----------------
    // тип биома
    private int type;
    // карта
    byte[][] map;


    // ---------------- конструктор ----------------
    Biome() {
        type = BIOME_TYPE_GRASS;
        map = new byte[][]{
                {BLOCK_AIR, BLOCK_AIR, BLOCK_AIR, BLOCK_DIRT, BLOCK_AIR},
                {BLOCK_AIR, BLOCK_DIRT, BLOCK_DIRT, BLOCK_DIRT, BLOCK_DIRT},
                {BLOCK_AIR, BLOCK_AIR, BLOCK_AIR, BLOCK_DIRT, BLOCK_AIR},
                {BLOCK_AIR, BLOCK_DIRT, BLOCK_DIRT, BLOCK_DIRT, BLOCK_AIR},
                {BLOCK_AIR, BLOCK_DIRT, BLOCK_DIRT, BLOCK_DIRT, BLOCK_AIR}
        };
    }
}
