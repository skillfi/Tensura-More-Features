package com.github.skillfi.tensura_mf.client;

import com.github.skillfi.tensura_mf.client.block.MagiculeIncubatorRenderer;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import com.github.skillfi.tensura_mf.client.entity.monster.OgreRenderer;
import com.github.skillfi.tensura_mf.client.entity.monster.KijinRenderer;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfClient {

    public static void init() {
        registerLivingEntityRenderer();
        registerBlockRenderer();
    }

    public static void registerRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> entityRenderers,
                                         BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> blockEntityRenderers) {
                                            entityRenderers.accept(MonsterEntityTypes.OGRE.get(), OgreRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.KIJIN.get(), KijinRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.MYSTIC_ONI.get(), KijinRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.WICKED_ONI.get(), KijinRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.SPIRIT_ONI.get(), KijinRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.DEATH_ONI.get(), KijinRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.DIVINE_ONI.get(), KijinRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.DIVINE_FIGHTER.get(), KijinRenderer::new);
                                         }

    public static void registerLivingEntityRenderer() {
        EntityRendererRegistry.register(MonsterEntityTypes.OGRE, OgreRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.KIJIN, KijinRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.MYSTIC_ONI, KijinRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.WICKED_ONI, KijinRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.SPIRIT_ONI, KijinRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.DEATH_ONI, KijinRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.DIVINE_ONI, KijinRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.DIVINE_FIGHTER, KijinRenderer::new);
    }

    public static void registerBlockRenderer() {
        BlockEntityRendererRegistry.register(TensuraMfBlocksEntities.MAGICULE_INCUBATOR_BLOCK.get(), MagiculeIncubatorRenderer::new);
    }

}
