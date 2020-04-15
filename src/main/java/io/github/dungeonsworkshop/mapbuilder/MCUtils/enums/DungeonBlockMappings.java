package io.github.dungeonsworkshop.mapbuilder.MCUtils.enums;

import net.morbz.minecraft.blocks.*;

public enum DungeonBlockMappings {

    AIR(0,"minecraft:air", SimpleBlock.AIR),
    STONE(1,"minecraft:stone", StoneBlock.STONE),

    STONE_BRICK(0x62, "minecraft:stone_brick", StoneBrickBlock.NORMAL),

    GRASS(0x1F, "minecraft:grass", new CustomBlock(31, 1, 1)),

    GRASS_BLOCK(0x02,"minecraft:grass_block", SimpleBlock.GRASS),
    DIRT(0x03, "minecraft:dirt", DirtBlock.DIRT),
    GRASSY_DIRT(-0x0d, "minecraft:coarse_dirt", DirtBlock.COARSE_DIRT),
    VERY_GRASSY_DIRT(-0x20, "minecraft:podzol", DirtBlock.PODZOL),

    OAK_FENCE(0x55, "minecraft:oak_fence", SimpleBlock.FENCE),
    ACACIA_LOG(0x11, "minecraft:acacia_log", new CustomBlock(17, 0, 0));


    private int bedrockID;
    private String namespaceID;
    private IBlock block;

    DungeonBlockMappings(int bedrockID, String namespaceID, IBlock block){
        this.bedrockID = bedrockID;
        this.namespaceID = namespaceID;
        this.block = block;
    }

    public int getBedrockID() {
        return bedrockID;
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public IBlock getIBlock() {
        return block;
    }


    public static DungeonBlockMappings getByHex(int hex) {
        for(DungeonBlockMappings block : values()) {
            if(block.getBedrockID() == hex) return block;
        }
        return STONE;
    }

    public static DungeonBlockMappings getByNamespaceID(String namespaceID) {
        for(DungeonBlockMappings block : values()) {
            if(block.getNamespaceID().equalsIgnoreCase(namespaceID)) return block;
        }
        return AIR;
    }

}
