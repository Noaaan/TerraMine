package terramine.common.item.curio.feet;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;

public class ObsidianWaterWalkingBootsItem extends TrinketTerrariaItem {

	public ObsidianWaterWalkingBootsItem() {
		//noinspection UnstableApiUsage
        LivingFluidCollisionCallback.EVENT.register(ObsidianWaterWalkingBootsItem::onFluidCollision);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		ModComponents.SWIM_ABILITIES.maybeGet(entity).ifPresent(swimAbilities -> {
			if (entity.isInWater()) {
				swimAbilities.setWet(true);
			} else if (entity.isOnGround() || (entity instanceof Player player && player.getAbilities().flying)) {
				swimAbilities.setWet(false);
			}
		});

		super.tick(stack, slot, entity);
	}

	private static boolean onFluidCollision(LivingEntity entity, FluidState fluidState) {
		if (TrinketsHelper.isEquipped(ModItems.OBSIDIAN_WATER_WALKING_BOOTS, entity) && !entity.isCrouching()) {
			return !fluidState.is(FluidTags.LAVA);
		}

		return false;
	}
}
