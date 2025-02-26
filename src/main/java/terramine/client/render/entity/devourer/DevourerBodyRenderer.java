package terramine.client.render.entity.devourer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.DevourerModel;
import terramine.common.entity.devourer.DevourerBodyEntity;
import terramine.common.init.ModModelLayers;

@Environment(value=EnvType.CLIENT)
public class DevourerBodyRenderer extends MobRenderer<DevourerBodyEntity, DevourerModel<DevourerBodyEntity>> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/monsters/pre-hardmode/devourer/default.png");

    public DevourerBodyRenderer(EntityRendererProvider.Context context) {
        super(context, new DevourerModel<>(context.bakeLayer(ModModelLayers.DEVOURER)), 0.40F);
    }

    @Override
    public void render(@NotNull DevourerBodyEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull DevourerBodyEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(@NotNull DevourerBodyEntity entity, @NotNull PoseStack poseStack, float f, float g, float h) {
        super.setupRotations(entity, poseStack, f, g, h);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(entity.getXRot()));
    }
}
