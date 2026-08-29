package com.github.skillfi.tensura_mf.client.entity.monster;

import com.github.skillfi.tensura_mf.client.entity.layer.PrimordialDaemonLayer;
import com.github.skillfi.tensura_mf.entity.monster.PrimordialDaemonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.manasmods.tensura.item.tool.SimpleShieldItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

/** Shared GeckoLib renderer for the primordial daemon variants. */
public class PrimordialDaemonRenderer extends GeoEntityRenderer<PrimordialDaemonEntity> {
    private static final String LEFT_HAND = "leftItem";
    private static final String RIGHT_HAND = "rightItem";
    private static final String LEFT_BOOT = "leftBootArmor";
    private static final String RIGHT_BOOT = "rightBootArmor";
    private static final String LEFT_ARMOR_LEG = "leftLegArmor";
    private static final String RIGHT_ARMOR_LEG = "rightLegArmor";
    private static final String CHESTPLATE = "bodyArmor";
    private static final String RIGHT_SLEEVE = "rightArmArmor";
    private static final String LEFT_SLEEVE = "leftArmArmor";
    private static final String HELMET = "headArmor";
    protected ItemStack mainHandItem;
    protected ItemStack offhandItem;
    
    public PrimordialDaemonRenderer(EntityRendererProvider.Context context) {
        super(context, new PrimordialDaemonModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new PrimordialDaemonLayer.Face(this));
        this.addRenderLayer(new PrimordialDaemonLayer.Top(this));
        this.addRenderLayer(new PrimordialDaemonLayer.Bottom(this));
        this.addRenderLayer(new ItemArmorGeoLayer<PrimordialDaemonEntity>(this) {
            protected @Nullable ItemStack getArmorItemForBone(GeoBone bone, PrimordialDaemonEntity animatable) {
                ItemStack var10000;
                switch (bone.getName()) {
                    case "leftBootArmor":
                    case "rightBootArmor":
                        var10000 = this.bootsStack;
                        break;
                    case "leftLegArmor":
                    case "rightLegArmor":
                        var10000 = this.leggingsStack;
                        break;
                    case "bodyArmor":
                    case "rightArmArmor":
                    case "leftArmArmor":
                        var10000 = this.chestplateStack;
                        break;
                    case "headArmor":
                        var10000 = this.helmetStack;
                        break;
                    default:
                        var10000 = null;
                }

                return var10000;
            }

            protected @NotNull EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, PrimordialDaemonEntity animatable) {
                EquipmentSlot var10000;
                switch (bone.getName()) {
                    case "leftBootArmor":
                    case "rightBootArmor":
                        var10000 = EquipmentSlot.FEET;
                        break;
                    case "leftLegArmor":
                    case "rightLegArmor":
                        var10000 = EquipmentSlot.LEGS;
                        break;
                    case "rightArmArmor":
                        var10000 = !animatable.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                        break;
                    case "leftArmArmor":
                        var10000 = animatable.isLeftHanded() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                        break;
                    case "bodyArmor":
                        var10000 = EquipmentSlot.CHEST;
                        break;
                    case "headArmor":
                        var10000 = EquipmentSlot.HEAD;
                        break;
                    default:
                        var10000 = super.getEquipmentSlotForBone(bone, stack, animatable);
                }

                return var10000;
            }

            protected @NotNull ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, PrimordialDaemonEntity animatable, HumanoidModel<?> baseModel) {
                ModelPart var10000;
                switch (bone.getName()) {
                    case "leftBootArmor":
                    case "leftLegArmor":
                        var10000 = baseModel.leftLeg;
                        break;
                    case "rightBootArmor":
                    case "rightLegArmor":
                        var10000 = baseModel.rightLeg;
                        break;
                    case "rightArmArmor":
                        var10000 = baseModel.rightArm;
                        break;
                    case "leftArmArmor":
                        var10000 = baseModel.leftArm;
                        break;
                    case "bodyArmor":
                        var10000 = baseModel.body;
                        break;
                    case "headArmor":
                        var10000 = baseModel.head;
                        break;
                    default:
                        var10000 = super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
                }

                return var10000;
            }
        });
        this.addRenderLayer(new BlockAndItemGeoLayer<PrimordialDaemonEntity>(this) {
            protected @Nullable ItemStack getStackForBone(GeoBone bone, PrimordialDaemonEntity animatable) {
                ItemStack var10000;
                switch (bone.getName()) {
                    case "leftItem" -> var10000 = animatable.isLeftHanded() ? PrimordialDaemonRenderer.this.mainHandItem : PrimordialDaemonRenderer.this.offhandItem;
                    case "rightItem" -> var10000 = animatable.isLeftHanded() ? PrimordialDaemonRenderer.this.offhandItem : PrimordialDaemonRenderer.this.mainHandItem;
                    default -> var10000 = null;
                }

                return var10000;
            }

            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, PrimordialDaemonEntity animatable) {
                ItemDisplayContext var10000;
                switch (bone.getName()) {
                    case "leftItem":
                    case "rightItem":
                        var10000 = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                        break;
                    default:
                        var10000 = ItemDisplayContext.NONE;
                }

                return var10000;
            }

            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, PrimordialDaemonEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == PrimordialDaemonRenderer.this.mainHandItem || stack == PrimordialDaemonRenderer.this.offhandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                    if (stack == PrimordialDaemonRenderer.this.mainHandItem && !animatable.isLeftHanded() || stack == PrimordialDaemonRenderer.this.offhandItem && animatable.isLeftHanded()) {
                        if (stack.getItem() instanceof ShieldItem || stack.getItem() instanceof SimpleShieldItem) {
                            poseStack.translate((double)0.0F, (double)0.125F, (double)-0.25F);
                        }
                    } else if ((stack == PrimordialDaemonRenderer.this.mainHandItem && animatable.isLeftHanded() || stack == PrimordialDaemonRenderer.this.offhandItem && !animatable.isLeftHanded()) && (stack.getItem() instanceof ShieldItem || stack.getItem() instanceof SimpleShieldItem)) {
                        poseStack.translate((double)0.0F, (double)0.125F, (double)0.25F);
                        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    protected float getShadowRadius(PrimordialDaemonEntity entity) {
        return entity.isBaby() ? 0.25F : 0.5F;
    }

    protected float getDeathMaxRotation(PrimordialDaemonEntity animatable) {
        return 0.0F;
    }

    public void preRender(PoseStack poseStack, PrimordialDaemonEntity daemon, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        this.mainHandItem = daemon.getMainHandItem();
        this.offhandItem = daemon.getOffhandItem();
        this.entityRenderTranslations.set(poseStack.last().pose());
        float scale = daemon.isBaby() ? 0.5F : 1.0F;
        this.scaleModelForRender(this.scaleWidth * scale, this.scaleHeight * scale, poseStack, daemon, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
