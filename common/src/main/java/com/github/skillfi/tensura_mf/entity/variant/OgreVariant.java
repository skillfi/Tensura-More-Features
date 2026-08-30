package com.github.skillfi.tensura_mf.entity.variant;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import io.github.manasmods.tensura.entity.monster.GoblinEntity;
import io.github.manasmods.tensura.entity.variant.GoblinVariant;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

@UtilityClass
public class OgreVariant {

    private static String textureRoot(OgreEntity entity) {
        return entity.getResource().getPath().equals("ogre") ? "ogre" : "kijin";
    }

    @RequiredArgsConstructor
    @Getter
    public static enum Gender {
        MALE(0, "male"),
        FEMALE(1, "female"),
        OTHER(2, "unisex");

        private static final Gender[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Gender::getId)).toArray((x$0) -> new Gender[x$0]);
        private final int id;
        private final String location;

        public static Gender byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static enum Horns {
        ONE(1, "one"),
        TWO(2, "dual");


        private static final Horns[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Horns::getId)).toArray((x$0) -> new Horns[x$0]);
        private final int id;
        private final String location;

        public static Horns byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(OgreEntity entity) {
            return (Util.getRandom(values(), entity.getRandom())).getId();
        }
    }

    @Getter
    public static enum Skin {
        LIGHT(1, "light_"),
        DARK(2, "dark_");

        private static final Skin[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Skin::getId)).toArray((x$0) -> new Skin[x$0]);
        private final int id;
        private final String location;
        private final EnumMap<Gender, ResourceLocation> textures;

        private Skin(int id, String name) {
            this.id = id;
            this.location = name;
            this.textures = new EnumMap(Gender.class);

            for(Gender gender : Gender.values()) {
                this.textures.put(gender, TensuraMf.create("textures/entity/ogre/" + gender.getLocation() + "/skin/" + name + gender.getLocation() + ".png"));
            }

        }

        public static ResourceLocation getTextureLocation(OgreEntity entity) {
            Skin skin = entity.getSkin();
            String root = textureRoot(entity);
            String skinName = root.equals("kijin") ? "light_" : skin.getLocation();
            return TensuraMf.create("textures/entity/" + root + "/" + entity.getGender().getLocation() + "/skin/" + skinName + entity.getGender().getLocation() + ".png");
        }

        public static Skin byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(OgreEntity entity) {
            return Util.getRandom(values(), entity.getRandom()).getId();
        }
    }

    @Getter
    public static enum Face {
        FACE_A(0, "unisex/faces/face_a", Gender.OTHER),
        FACE_B(1, "unisex/faces/face_b", Gender.OTHER),
        FACE_C(2, "male/faces/face_c", Gender.MALE),
        FACE_D(3, "unisex/faces/face_d", Gender.OTHER),
        FACE_E(4, "unisex/faces/face_e", Gender.OTHER);

        private static final Face[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Face::getId)).toArray((x$0) -> new Face[x$0]);
        private static final List<Integer> MALE_LIST = Arrays.stream(values()).filter((skin) -> skin.getGender() != Gender.FEMALE).map(Face::getId).toList();
        private static final List<Integer> FEMALE_LIST = Arrays.stream(values()).filter((skin) -> skin.getGender() != Gender.MALE).map(Face::getId).toList();
        private final int id;
        private final String location;
        private final Gender gender;
        private final ResourceLocation texture;

        private Face(int id, String name, Gender gender) {
            this.id = id;
            this.location = name;
            this.gender = gender;
            this.texture = TensuraMf.create("textures/entity/ogre/" + name + ".png");
        }

        public ResourceLocation getTextureLocation() {
            return this.texture;
        }

        public ResourceLocation getTextureLocation(OgreEntity entity) {
            String location = this.location;
            if (textureRoot(entity).equals("kijin") && this == FACE_C) location = "unisex/faces/face_a";
            return TensuraMf.create("textures/entity/" + textureRoot(entity) + "/" + location + ".png");
        }

        public static Face byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(Gender gender, OgreEntity entity) {
            return gender.equals(Gender.FEMALE) ? (Integer)FEMALE_LIST.get(entity.getRandom().nextInt(FEMALE_LIST.size())) : (Integer)MALE_LIST.get(entity.getRandom().nextInt(MALE_LIST.size()));
        }
    }

    @Getter
    public static enum Hair {
        LONG(4, "hair_long_"),
        SHORT(8, "hair_short_");

        private static final Hair[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Hair::getId)).toArray((x$0) -> new Hair[x$0]);
        private final int id;
        private final String location;
        private final EnumMap<Gender, ResourceLocation> textures;

        private Hair(int id, String name) {
            this.id = id;
            this.location = name;
            this.textures = new EnumMap(Gender.class);

            for(Gender g : Gender.values()) {
                ResourceLocation resourceLocation = TensuraMf.create("textures/entity/ogre/" + g.getLocation() + "/hair/" + name + g.getLocation() + ".png");
                this.textures.put(g, resourceLocation);
            }

        }

        public static ResourceLocation getTextureLocation(OgreEntity entity) {
            Hair hair = entity.getHair();
            String gender = entity.getGender().getLocation();
            String fileGender = textureRoot(entity).equals("kijin") && entity.isMale() ? "female" : gender;
            return TensuraMf.create("textures/entity/" + textureRoot(entity) + "/" + gender + "/hair/" + hair.getLocation() + fileGender + ".png");
        }

        public static Hair byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(OgreEntity entity) {
            return Util.getRandom(values(), entity.getRandom()).getId();
        }
    }

    @Getter
    public static enum Top {
        FUR(0, "fur");


        private static final Top[] BY_ID = (Top[])Arrays.stream(values()).sorted(Comparator.comparingInt(Top::getId)).toArray((x$0) -> new Top[x$0]);
        private final int id;
        private final String location;
        private final ResourceLocation texture;

        private Top(int id, String name) {
            this.id = id;
            this.location = name;
            this.texture = TensuraMf.create("textures/entity/ogre/unisex/top/chest_" + name + ".png");
        }

        public static ResourceLocation getTextureLocation(OgreEntity entity) {
            return TensuraMf.create("textures/entity/" + textureRoot(entity) + "/unisex/top/chest_" + entity.getTop().getLocation() + ".png");
        }

        public static Top byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(OgreEntity entity) {
            return (Util.getRandom(values(), entity.getRandom())).getId();
        }
    }

    @Getter
    public static enum Bottom {
        FUR(0, "fur");


        private static final Bottom[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Bottom::getId)).toArray((x$0) -> new Bottom[x$0]);
        private final int id;
        private final String location;
        private final ResourceLocation texture;

        private Bottom(int id, String name) {
            this.id = id;
            this.location = name;
            this.texture = TensuraMf.create("textures/entity/ogre/unisex/bottom/legs_" + name + ".png");
        }

        public static ResourceLocation getTextureLocation(OgreEntity entity) {
            return TensuraMf.create("textures/entity/" + textureRoot(entity) + "/unisex/bottom/legs_" + entity.getBottom().getLocation() + ".png");
        }

        public static Bottom byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(OgreEntity entity) {
            return ((Bottom)Util.getRandom(values(), entity.getRandom())).getId();
        }
    }
}
