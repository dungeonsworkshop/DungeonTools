package io.github.dungeonsworkshop.mapbuilder.MCUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;

public class BlockMapper {

    private static final BiMap<Integer, String> JAVA_ID_MAP = HashBiMap.create();
    private static final BiMap<Integer, String> BEDROCK_ID_MAP = HashBiMap.create();

    private static final ImmutablePair<Integer, String> STONE = new ImmutablePair(19, "minecraft:sponge");

    static{
        //Bedrock Map Setup
        try(InputStream inputStream = BlockMapper.class.getResourceAsStream("/BedrockIDMap.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);

            for(int i = 0; i < 255; i++)
                BEDROCK_ID_MAP.put(i, properties.getProperty(Integer.toString(i)));

            BEDROCK_ID_MAP.put(-28, properties.getProperty(Integer.toString(-28)));
            BEDROCK_ID_MAP.put(-30, properties.getProperty(Integer.toString(-30)));
            BEDROCK_ID_MAP.put(-32, properties.getProperty(Integer.toString(-32)));
            BEDROCK_ID_MAP.put(-81, properties.getProperty(Integer.toString(-81)));
            BEDROCK_ID_MAP.put(-94, properties.getProperty(Integer.toString(-94)));
            BEDROCK_ID_MAP.put(-98, properties.getProperty(Integer.toString(-98)));
            BEDROCK_ID_MAP.put(-13, properties.getProperty(Integer.toString(-13)));
            BEDROCK_ID_MAP.put(-58, properties.getProperty(Integer.toString(-58)));
            BEDROCK_ID_MAP.put(-117, properties.getProperty(Integer.toString(-117)));
            BEDROCK_ID_MAP.put(-115, properties.getProperty(Integer.toString(-115)));
            BEDROCK_ID_MAP.put(-122, properties.getProperty(Integer.toString(-122)));
            BEDROCK_ID_MAP.put(-101, properties.getProperty(Integer.toString(-101)));
            BEDROCK_ID_MAP.put(-119, properties.getProperty(Integer.toString(-119)));

        }catch (IOException e){ e.printStackTrace(); }

        //Java Map Setup
        try(InputStream inputStream = BlockMapper.class.getResourceAsStream("/JavaIDMap.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);

            for(int i = 0; i < 255; i++)
                JAVA_ID_MAP.put(i, properties.getProperty(Integer.toString(i)));

        }catch (IOException e){ e.printStackTrace(); }
    }

    public static int toJavaIDFromBedrockID(int BID){
        String bNameSpace = BEDROCK_ID_MAP.getOrDefault(BID, "nomapping: " + BID);
        if(!bNameSpace.contains("minecraft:")) bNameSpace = "minecraft:" + bNameSpace;
        if(!bNameSpace.contains("secondary:")) bNameSpace = "minecraft:" + bNameSpace.subSequence(10, bNameSpace.length());
        return JAVA_ID_MAP.inverse().getOrDefault(bNameSpace, 19);
    }

    public static int toBedrockIDFromJavaID(int JID){
        String jNameSpace = JAVA_ID_MAP.getOrDefault(JID, "nomapping: " + JID);
        return BEDROCK_ID_MAP.inverse().getOrDefault(jNameSpace, 19);
    }

}
