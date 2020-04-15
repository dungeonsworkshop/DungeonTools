package io.github.dungeonsworkshop.mapbuilder.MCUtils;

import net.morbz.minecraft.blocks.CustomBlock;
import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;

import java.util.List;

public class StructureHelper {

    public static World getWorldFromTileBytes(List<Byte> tileBlocks, List<Integer> blockData, int xSize, int ySize, int zSize){
        DefaultLayers layers = new DefaultLayers();
        layers.setLayer(0, Material.AIR);

        IGenerator generator = new FlatGenerator(layers);

        Level level = new Level("GeneratedWorld", generator);

        level.setGameType(GameType.CREATIVE);
        level.setAllowCommands(true);
        level.setMapFeatures(false);
        level.setSpawnPoint(0,0,0);

        World world = new World(level, layers);

        int it = 0;
        for(int y = 0; y < ySize; y++){
            for(int z = 0; z < zSize; z++){
                for(int x = 0; x < xSize; x++){
                    if(x == 59 && y == 96 && z == 14) System.out.println(tileBlocks.get(it));

                    int ID = BlockMapper.toJavaIDFromBedrockID(tileBlocks.get(it));
                    int state = blockData.get(it);

                    IBlock block = new CustomBlock(BlockMapper.toJavaIDFromBedrockID(ID), state, 0);

                    world.setBlock(x, y, z, block);
                    it++;
                }
            }
        }

        return world;
    }

}
