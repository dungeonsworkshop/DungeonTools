package io.github.dungeonsworkshop.mapbuilder.MCUtils;

import io.github.dungeonsworkshop.mapbuilder.MCUtils.enums.DungeonBlockMappings;
import net.morbz.minecraft.blocks.CustomBlock;
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
        level.setSpawnPoint(0,0,0);

        World world = new World(level, layers);

        int it = 0;
        for(int y = 0; y < ySize; y++){
            for(int z = 0; z < zSize; z++){
                for(int x = 0; x < xSize; x++){
                    int ID = DungeonBlockMappings.getByHex(tileBlocks.get(it)).getIBlock().getBlockId();
                    int State = tileBlocks.get(it);

                    world.setBlock(x, y + 30, z, new CustomBlock(ID, State, 0));
                    it++;
                }
            }
        }

        return world;
    }

}
