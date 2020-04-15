package io.github.dungeonsworkshop.mapbuilder;

import io.github.dungeonsworkshop.mapbuilder.IO.FileTypeHelper;
import io.github.dungeonsworkshop.mapbuilder.MCUtils.StructureHelper;
import net.morbz.minecraft.world.World;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class MapBuilder
{

    public static final int STATE_MASK = 0xF;
    public static final int X = 9, Y = 5, Z = 10;

    public static void main(String[] args) throws IOException, DataFormatException
    {

        String tileString = FileTypeHelper.loadTileB64();
        byte[] decompressed = FileTypeHelper.decompress(new Base64().decode(tileString));

        List<Byte> tileBlocks = new ArrayList<>();
        List<Integer> tileData = new ArrayList<>();

        for (int i = 0; i < X * Y * Z; i++)
        {
            tileBlocks.add(decompressed[i]);
        }

        for (int i = 0; i < decompressed.length - (X * Y * Z); i++)
        {
            byte states = decompressed[(X * Y * Z) + i];

            int stateOne = (states >> 4) & STATE_MASK;
            int stateTwo = states & STATE_MASK;

            tileData.add(stateOne);
            tileData.add(stateTwo);
        }

        int it = 0;
        int xIT = 0;
        int zIT = 0;
        StringBuilder test = new StringBuilder();
        for (byte b : decompressed)
        {
            xIT++;
            if (decompressed[it] == 0)
            {
                test.append(".");
                test.append(" ");
                test.append(" ");
                test.append(" ");
                test.append(" ");
            }
            else
            {
                test.append(b);
                test.append(" ".repeat(Math.max(0, 4 - (b + "").length())));
            }
            if (xIT == X)
            {
                test.append("\n");
                xIT = 0;
                zIT++;
            }
            if (zIT == Z)
            {
                test.append("\n");
                zIT = 0;
            }
            if (it == X * Y * Z - 1) test.append("\n \n");
            it++;

        }

        World world = StructureHelper.getWorldFromTileBytes(tileBlocks, tileData, X, Y, Z);

        File file = world.save();


        //        System.out.println(Arrays.toString(tileBlocks.toArray()));
        //        System.out.println("");
        //        System.out.println(Arrays.toString(tileData));
        //        System.out.println("");
        //        System.out.println(Arrays.toString(decompressed));


        System.out.println(test);

    }
}