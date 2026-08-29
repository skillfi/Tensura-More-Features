package com.github.skillfi.tensura_mf.client.entity.layer;

import com.github.skillfi.tensura_mf.entity.monster.PrimordialDaemonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.manasmods.tensura.client.TensuraColors;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@NoArgsConstructor
public class PrimordialDaemonLayer{
    private static RenderType getRenderType(ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }

    public static class Face extends GeoRenderLayer<PrimordialDaemonEntity> {

        public Face(GeoRenderer<PrimordialDaemonEntity> renderLayer){
            super(renderLayer);
        }

        public void render(PoseStack poseStack, PrimordialDaemonEntity entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            int color = entity.getTopColor() != 0 ? entity.getTopColor() : -1;
            if (entity.isInvisible()) {
                Player player = Minecraft.getInstance().player;
                if (player == null || entity.isInvisibleTo(player)) {
                    return;
                }

                color = TensuraColors.getARGBWithAlpha(color, 0.1F);
            }

            RenderType type = getRenderType(entity.getFace().getTextureLocation());
            this.getRenderer().reRender(this.getDefaultBakedModel(entity), poseStack, bufferSource, entity, type, bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY, color);
        }


    }

    public static class Top extends GeoRenderLayer<PrimordialDaemonEntity> {
        public Top(GeoRenderer<PrimordialDaemonEntity> renderer) {
            super(renderer);
        }

        public void render(PoseStack poseStack, PrimordialDaemonEntity entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            int color = entity.getTopColor() != 0 ? entity.getTopColor() : -1;
            if (entity.isInvisible()) {
                Player player = Minecraft.getInstance().player;
                if (player == null || entity.isInvisibleTo(player)) {
                    return;
                }

                color = TensuraColors.getARGBWithAlpha(color, 0.1F);
            }

            RenderType type = getRenderType(entity.getTop().getTexture());
            this.getRenderer().reRender(this.getDefaultBakedModel(entity), poseStack, bufferSource, entity, type, bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY, color);
        }
    }

    public static class Bottom extends GeoRenderLayer<PrimordialDaemonEntity> {
        public Bottom(GeoRenderer<PrimordialDaemonEntity> renderer) {
            super(renderer);
        }

        public void render(PoseStack poseStack, PrimordialDaemonEntity entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            int color = entity.getBottomColor() != 0 ? entity.getBottomColor() : -1;
            if (entity.isInvisible()) {
                Player player = Minecraft.getInstance().player;
                if (player == null || entity.isInvisibleTo(player)) {
                    return;
                }

                color = TensuraColors.getARGBWithAlpha(color, 0.1F);
            }

            RenderType type = getRenderType(entity.getBottom().getTexture());
            this.getRenderer().reRender(this.getDefaultBakedModel(entity), poseStack, bufferSource, entity, type, bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY, color);
        }
    }
}
