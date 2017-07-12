package com.teambr.mako.api.mako;

import com.teambr.mako.utils.Reference;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class MakoRegistry {

    private static MakoRegistry ourInstance = new MakoRegistry();
    private HashMap<String, IMako> makoRegistry;
    private Random random;
    private MakoRegistry() {
        makoRegistry = new HashMap<>();
        random = new Random();
        this.addMako(new SimpleMako("air", new ResourceLocation(Reference.MODID, "icons/air"), 0xFFFF00));
        this.addMako(new SimpleMako("water", new ResourceLocation(Reference.MODID, "icons/water"), 0x00C8C8));
        this.addMako(new SimpleMako("nature", new ResourceLocation(Reference.MODID, "icons/nature"), 0x00C832));
        this.addMako(new SimpleMako("fire", new ResourceLocation(Reference.MODID, "icons/fire"), 0xC86400));
        this.addMako(new CombinedMako("ice", null, 0x00FFFF, this.getMako("water"), this.getMako("air")));
        this.addMako(new CombinedMako("storm", null, 0xC8C8C8, this.getMako("fire"), this.getMako("air")));
        this.addMako(new CombinedMako("mystic", null, 0xFFC8FF, this.getMako("nature"), this.getMako("water")));
        this.addMako(new CombinedMako("magma", null, 0xFF6400, this.getMako("nature"), this.getMako("fire")));
    }

    public static MakoRegistry getInstance() {
        return ourInstance;
    }

    public void addMako(IMako mako) {
        if (!makoRegistry.containsKey(mako.getName())) {
            makoRegistry.put(mako.getName(), mako);
        }
    }

    public IMako getMako(String name) {
        if (makoRegistry.containsKey(name)) {
            return makoRegistry.get(name);
        }
        return null;
    }

    public IMako getRandomPureMako() {
        List<IMako> pure = new ArrayList<>();
        makoRegistry.forEach((s, mako) -> {
            if (mako instanceof SimpleMako) pure.add(mako);
        });
        return pure.get(random.nextInt(pure.size()));
    }

    public CombinedMako getCombinedMakoFromParents(IMako... parents) {
        for (IMako mako : makoRegistry.values()) {
            if (!(mako instanceof CombinedMako)) continue;
            if (parents.length != ((CombinedMako) mako).getParents().length) continue;
            boolean isEqual = true;
            for (IMako m1 : parents) {
                if (!Arrays.asList((((CombinedMako) mako).getParents())).contains(m1)) isEqual = false;
            }
            if (isEqual) return (CombinedMako) mako;
        }
        return null;
    }

    public HashMap<String, IMako> getMakoRegistry() {
        return makoRegistry;
    }
}
