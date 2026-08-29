package com.github.skillfi.tensura_mf.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import com.github.skillfi.tensura_mf.client.entity.monster.OgreRenderer;
import com.github.skillfi.tensura_mf.client.entity.monster.PrimordialDaemonRenderer;
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
    }

    public static void registerRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> entityRenderers,
                                         BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> blockEntityRenderers) {
                                            entityRenderers.accept(MonsterEntityTypes.OGRE.get(), OgreRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.PRIMORDIAL_WHITE.get(), PrimordialDaemonRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.PRIMORDIAL_BLACK.get(), PrimordialDaemonRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.PRIMORDIAL_ROUGE.get(), PrimordialDaemonRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.PRIMORDIAL_VERT.get(), PrimordialDaemonRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.PRIMORDIAL_JAUNE.get(), PrimordialDaemonRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.PRIMORDIAL_VIOLET.get(), PrimordialDaemonRenderer::new);
                                            entityRenderers.accept(MonsterEntityTypes.PRIMORDIAL_BLEU.get(), PrimordialDaemonRenderer::new);
                                         }

    public static void registerLivingEntityRenderer() {
        EntityRendererRegistry.register(MonsterEntityTypes.OGRE, OgreRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.PRIMORDIAL_WHITE, PrimordialDaemonRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.PRIMORDIAL_BLACK, PrimordialDaemonRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.PRIMORDIAL_ROUGE, PrimordialDaemonRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.PRIMORDIAL_VERT, PrimordialDaemonRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.PRIMORDIAL_JAUNE, PrimordialDaemonRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.PRIMORDIAL_VIOLET, PrimordialDaemonRenderer::new);
        EntityRendererRegistry.register(MonsterEntityTypes.PRIMORDIAL_BLEU, PrimordialDaemonRenderer::new);
    }

}
