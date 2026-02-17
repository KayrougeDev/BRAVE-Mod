package fr.kayrouge.brave.agents.spell;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.math.Vec3d;

@Getter @Setter
public class SpellDataManager {

    @Getter(AccessLevel.PUBLIC)
    private static final SpellDataManager instance = new SpellDataManager();

    private Vec3d tpVec = new Vec3d(0,0,0);



}
