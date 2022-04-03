package terracraft.mixin.item.goldwatch.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(Gui.class)
public abstract class GuiMixin {

	@Shadow @Final private Minecraft minecraft;
	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract Player getCameraPlayer();
	@Shadow protected abstract Font getFont();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderGuiClock(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (player == null || !getEquippedTrinkets(player)) {
			return;
		}

		int left = this.screenWidth - 22 - this.getFont().width(getTime());
		int top = this.screenHeight - 13;

		matrices.pushPose();
		Gui.drawString(matrices, Minecraft.getInstance().font, getTime(), left, top, 0xffffff);
	}

	@Unique
	private boolean getEquippedTrinkets(Player player) {
		boolean equipped = false;

		if (TrinketsHelper.isEquipped(ModItems.GOLD_WATCH, player) || TrinketsHelper.isEquipped(ModItems.GPS, player) || TrinketsHelper.isEquipped(ModItems.PDA, player)
				|| TrinketsHelper.isEquipped(ModItems.CELL_PHONE, player)) {
			equipped = true;
		}
		if (player.getInventory().contains(ModItems.GOLD_WATCH.getDefaultInstance()) || player.getInventory().contains(ModItems.GPS.getDefaultInstance()) || player.getInventory().contains(ModItems.PDA.getDefaultInstance())
				|| player.getInventory().contains(ModItems.CELL_PHONE.getDefaultInstance())) {
			equipped = true;
		}

		return equipped;
	}

	@Unique
	private String getTime() {
		Minecraft mc = Minecraft.getInstance();
		long time = mc.player.level.getDayTime();
		long day = mc.player.level.getDayTime() / 24000L;
		long currentTime = time - (24000L * day);
		long currentHour = (currentTime / 1000L) + 6L;
		double currentTimeMin = currentTime - ((currentHour - 6L) * 1000L);
		currentTimeMin = currentTimeMin / (1000.0D / 60.0D);
		int currentMin = (int) currentTimeMin;
		if (currentHour > 24)
			currentHour -= 24L;
		//if (this.settings.getStringValue(Settings.clock_time_format) == "time.24") {
		//	return get24HourTimeForString(currentHour, currentMin);
		//}
		return get12HourTimeForString(currentHour, currentMin);
	}

	@Unique
	private static String get12HourTimeForString(long currentHour, long currentMin) {
		StringBuilder sb = new StringBuilder();
		String period = "am";
		if (currentHour == 12) {
			period = "pm";
		}
		if (currentHour == 24) {
			currentHour = 12;
			period = "am";
		}
		if (currentHour > 12) {
			currentHour -= 12;
			period = "pm";
		}
		if (currentHour < 10)
			sb.append(0);
		sb.append(currentHour);
		return sb.toString() + ":" + getMinuteForString(currentMin) + " " + period;
	}

	@Unique
	private static String getMinuteForString(long currentMin) {
		StringBuilder sb = new StringBuilder();
		if (currentMin < 10)
			sb.append("0");
		sb.append(currentMin);
		return sb.toString();
	}
}
