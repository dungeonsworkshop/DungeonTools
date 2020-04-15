package io.github.dungeonsworkshop.mapbuilder;

import io.github.dungeonsworkshop.mapbuilder.IO.FileTypeHelper;
import io.github.dungeonsworkshop.mapbuilder.MCUtils.StructureHelper;
import net.morbz.minecraft.world.World;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.zip.DataFormatException;

public class MapBuilder {

    public static final int X = 9, Y = 5, Z = 10;

    public static void main(String[] args) throws IOException, DataFormatException {

        String tileString = FileTypeHelper.loadTileB64();
        byte[] decompressed = FileTypeHelper.decompress(new Base64().decode(tileString));

        List<Byte> tileBlocks = new ArrayList<>();
        List<Integer> tileData = new ArrayList<>();

        for (int i = 0; i < X * Y * Z; i++) {
            tileBlocks.add(decompressed[i]);
        }

        for (int i = 0; i < decompressed.length - (X * Y * Z); i++) {
            BitSet bitSet = fromByte(decompressed[(X * Y * Z) + i]);

            BitSet bitset1 = bitSet.get(0, 3);
            BitSet bitset2 = bitSet.get(4, 7);

            int stateOne = Integer.parseInt((bitset1.get(0) ? 1 : 0) + "" + (bitset1.get(1) ? 1 : 0) + "" + (bitset1.get(2) ? 1 : 0) + "" + (bitset1.get(3) ? 1 : 0), 2);
            int stateTwo = Integer.parseInt((bitset2.get(0) ? 1 : 0) + "" + (bitset2.get(1) ? 1 : 0) + "" + (bitset2.get(2) ? 1 : 0) + "" + (bitset2.get(3) ? 1 : 0), 2);

            tileData.add(stateOne);
            tileData.add(stateTwo);
        }

        int it = 0;
        int xIT = 0;
        int zIT = 0;
        StringBuilder test = new StringBuilder();
        for (int y = 0; y < decompressed.length; y++){

            xIT++;
            test.append("");
            if(decompressed[it] == 0){
                test.append(".");
                test.append(" ");
                test.append(" ");
                test.append(" ");
                test.append(" ");
            }else{
                test.append(decompressed[y]);
                for(int i = 0; i < 4 - (decompressed[y] + "").length(); i++){
                    test.append(" ");
                }
            }
            if(xIT == X){
                test.append("\n");
                xIT = 0;
                zIT++;
            }
            if(zIT == Z){
                test.append("\n");
                zIT = 0;
            }
            if(it == X * Y * Z - 1)test.append("\n \n");
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

    public static BitSet fromByte(byte b)
    {
        BitSet bits = new BitSet(8);
        for (int i = 0; i < 8; i++)
        {
            bits.set(i, (b & 1) == 1);
            b >>= 1;
        }
        return bits;
    }

}
