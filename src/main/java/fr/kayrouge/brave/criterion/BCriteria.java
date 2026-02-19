package fr.kayrouge.brave.criterion;

import fr.kayrouge.brave.BRAVE;
import net.minecraft.advancement.criterion.Criteria;

public class BCriteria {

    public static final BecomeAgentCriterion BECOME_AGENT = Criteria.register(BRAVE.MOD_ID+":become_agent", new BecomeAgentCriterion());


    public static void init() {}

}
