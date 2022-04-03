package terracraft.common.item.magic;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.item.TerrariaItemConfigurable;

import java.util.Random;

public class ManaCrystalItem extends TerrariaItemConfigurable {

    Random rand = new Random();

    public ManaCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player player = (Player)entity;
        if (ModComponents.MANA_HANDLER.get(player).getMaxMana() < 200) {
            ModComponents.MANA_HANDLER.get(player).addMaxMana(20);
            ModComponents.MANA_HANDLER.get(player).addCurrentMana(20);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }
        float pitch = rand.nextFloat() + 0.25f;
        if (pitch < 0.75f) {
            pitch = 0.75F;
        }
        player.level.playSound(null, player.blockPosition(), ModSoundEvents.MANA_CRYSTAL_USE, SoundSource.PLAYERS, 0.50f, pitch);
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 15;
    }
}
