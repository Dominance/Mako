package com.teambr.mako.api.mako;

import com.teambr.mako.utils.Reference;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class MakoRegistry {

    private static MakoRegistry ourInstance = new MakoRegistry();
    private LinkedHashMap<String, IMako> makoRegistry;
    private Random random;

    private MakoRegistry() {
        makoRegistry = new LinkedHashMap<>();
        random = new Random();
        this.addMako(new SimpleMako("air", new ResourceLocation(Reference.MODID, "icons/air"), 0xFFFF00));
        this.addMako(new SimpleMako("water", new ResourceLocation(Reference.MODID, "icons/water"), 0x00C8C8));
        this.addMako(new SimpleMako("nature", new ResourceLocation(Reference.MODID, "icons/nature"), 0x00C832));
        this.addMako(new SimpleMako("fire", new ResourceLocation(Reference.MODID, "icons/fire"), 0xC86400));
        this.addMako(new CombinedMako("quintessence", null, 0, this.getMako("water"), this.getMako("air"), this.getMako("earth")));
        this.addMako(new CombinedMako("chaos", null, 0, this.getMako("fire"), this.getMako("air"), this.getMako("earth")));
        this.addMako(new CombinedMako("smoke", null, 0, this.getMako("fire"), this.getMako("air")));
        this.addMako(new CombinedMako("ice", null, 0x00FFFF, this.getMako("water"), this.getMako("air")));
        this.addMako(new CombinedMako("ooze", null, 0, this.getMako("water"), this.getMako("earth")));
        this.addMako(new CombinedMako("lightning", null, 0, this.getMako("air"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("steam", null, 0, this.getMako("water"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("mineral", null, 0, this.getMako("earth"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("radiance", null, 0, this.getMako("fire"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("obsidian", null, 0, this.getMako("magma"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("spark", null, 0, this.getMako("smoke"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("terracota", null, 0, this.getMako("ooze"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("crystal", null, 0, this.getMako("ice"), this.getMako("quintessence")));
        this.addMako(new CombinedMako("void", null, 0, this.getMako("air"), this.getMako("chaos")));
        this.addMako(new CombinedMako("alkali", null, 0, this.getMako("water"), this.getMako("chaos")));
        this.addMako(new CombinedMako("ash", null, 0, this.getMako("fire"), this.getMako("chaos")));
        this.addMako(new CombinedMako("dust", null, 0, this.getMako("earth"), this.getMako("chaos")));
        this.addMako(new CombinedMako("pumice", null, 0, this.getMako("magma"), this.getMako("chaos")));
        this.addMako(new CombinedMako("fumes", null, 0, this.getMako("smoke"), this.getMako("chaos")));
        this.addMako(new CombinedMako("silt", null, 0, this.getMako("ooze"), this.getMako("chaos")));
        this.addMako(new CombinedMako("frost", null, 0, this.getMako("ice"), this.getMako("chaos")));
//        this.addMako(new CombinedMako("storm", null, 0xC8C8C8, this.getMako("fire"), this.getMako("air")));
//        this.addMako(new CombinedMako("mystic", null, 0xFFC8FF, this.getMako("nature"), this.getMako("water")));
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
