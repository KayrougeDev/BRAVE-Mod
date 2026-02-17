package fr.kayrouge.brave.items;

import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.component.BComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ResetItem extends Item {
    public ResetItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        BComponents.PLAYER_DATA.get(user).setAgent(Agents.DEFAULT);
        return super.use(world, user, hand);
    }
}
