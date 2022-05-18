package terracraft.common.potions.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import terracraft.common.init.ModComponents;

public class BlankEffect extends TerrariaEffect {

    public BlankEffect(MobEffectCategory type, int color, boolean isInstant) {
        super(type, color, isInstant);
    }

    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % Math.max(5, 30/(level+1)) == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int level) {
    }
}
