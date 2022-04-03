package terracraft.common.network.packet;

import terracraft.Artifacts;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BoneMealPacket {
    public static final ResourceLocation ID = new ResourceLocation(Artifacts.MOD_ID, "bone_meal");

    public static void send(BlockPos pos) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeLong(pos.asLong());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl network, FriendlyByteBuf buf, PacketSender sender) {
        BlockPos pos = BlockPos.of(buf.readLong());
        server.execute(() -> {
                BlockState state = player.level.getBlockState(pos);
                if (state.getBlock() instanceof BonemealableBlock fertilizable) {
                    if (fertilizable.isBonemealSuccess(player.level, player.getRandom(), pos, state)) {
                        fertilizable.performBonemeal(player.server.getLevel(player.level.dimension()), player.getRandom(), pos, state);
                        player.level.levelEvent(2005, pos, 0);
                        player.level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
                    }
                }
        });
    }
}
