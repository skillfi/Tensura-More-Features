package com.github.skillfi.tensura_mf.entity.variant;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.entity.monster.PrimordialDaemonEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

/** The seven primordial daemon colour variants. */
@RequiredArgsConstructor
@Getter
public enum PrimordialVariant {
    BLANC(0, Gender.FEMALE, Face.FACE_BLANC, Skin.LIGHT, Top.BLANC, Bottom.BLANC),
    NOIR(1, Gender.MALE, Face.FACE_NOIR, Skin.LIGHT, Top.NOIR, Bottom.NOIR),
    ROUGE(2, Gender.MALE, Face.FACE_NOIR, Skin.LIGHT, Top.ROUGE, Bottom.ROUGE),
    VERT(3, Gender.FEMALE, Face.FACE_NOIR, Skin.LIGHT, Top.VERT, Bottom.VERT),
    JAUNE(4, Gender.FEMALE, Face.FACE_NOIR, Skin.LIGHT, Top.JAUNE, Bottom.JAUNE),
    VIOLET(5, Gender.FEMALE, Face.FACE_NOIR, Skin.LIGHT, Top.VIOLET, Bottom.VIOLET),
    BLEU(6, Gender.FEMALE, Face.FACE_NOIR, Skin.LIGHT, Top.BLEU, Bottom.BLEU);

    private static final PrimordialVariant[] BY_ID = values();
    private final int id;
    private final Gender gender;
    private final Face face;
    private final Skin skin;
    private final Top top;
    private final Bottom bottom;

    public static PrimordialVariant byId(int id) {
        return BY_ID[Math.floorMod(id, BY_ID.length)];
    }

    public static PrimordialVariant random(PrimordialDaemonEntity entity) {
        return BY_ID[(Util.getRandom(values(), entity.getRandom())).getId()];
    }

    @Getter
    @RequiredArgsConstructor
    public enum Gender {
        MALE(0, "male"), FEMALE(1, "female"), OTHER(2, "unisex");

        private static final Gender[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Gender::getId)).toArray((x$0) -> new Gender[x$0]);
        private final int id;
        private final String location;

        public static Gender byId(int id) { return BY_ID[id % BY_ID.length]; }
    }

    @Getter
    public enum Skin {
        LIGHT(0, "light_"), DARK(1, "dark_");

        private static final Skin[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Skin::getId)).toArray((x$0) -> new Skin[x$0]);
        private final int id;
        private final String location;
        private final EnumMap<Gender, ResourceLocation> textures;

        private Skin(int id, String name) {
            this.id = id;
            this.location = name;
            this.textures = new EnumMap(Gender.class);

            for(Gender gender : Gender.values()) {
                this.textures.put(gender, TensuraMf.create("textures/entity/primordial_daemon/" + gender.getLocation() + "/skin/" + name + gender.getLocation() + ".png"));
            }

        }


        public static ResourceLocation getTextureLocation(PrimordialDaemonEntity entity) {
            return (ResourceLocation)entity.getSkin().textures.get(entity.getGender());
        }

        public static Skin byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(PrimordialDaemonEntity entity) {
            return Util.getRandom(values(), entity.getRandom()).getId();
        }
    }

    @Getter
    public static enum Face {
        FACE_BLANC(0, "unisex/faces/face_blanc", Gender.OTHER),
        FACE_NOIR(1, "unisex/faces/face_noir", Gender.OTHER),
        FACE_C(2, "male/faces/face_c", Gender.OTHER),
        FACE_D(3, "unisex/faces/face_d", Gender.OTHER),
        FACE_E(4, "unisex/faces/face_e", Gender.OTHER);

        private static final Face[] BY_ID = (Face[])Arrays.stream(values()).sorted(Comparator.comparingInt(Face::getId)).toArray((x$0) -> new Face[x$0]);
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

        public static Face byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public static int getRandom(Gender gender, PrimordialDaemonEntity entity) {
            return gender.equals(Gender.FEMALE) ? (Integer)FEMALE_LIST.get(entity.getRandom().nextInt(FEMALE_LIST.size())) : (Integer)MALE_LIST.get(entity.getRandom().nextInt(MALE_LIST.size()));
        }
    }

    @Getter
    public enum Top {
        BLANC(0, "blanc"),
        NOIR(1, "noir"),
        ROUGE(2, "rouge"),
        VERT(3, "vert"),
        JAUNE(4, "jaune"),
        VIOLET(5, "violet"),
        BLEU(6, "bleu");


        private static final Top[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Top::getId)).toArray((x$0) -> new Top[x$0]);
        private final int id;
        private final ResourceLocation texture;

        Top(int id, String name) { this.id = id; this.texture = TensuraMf.create("textures/entity/primordial_daemon/unisex/top/chest_" + name + ".png"); }
        public static Top byId(int id) { return BY_ID[id % BY_ID.length]; }
    }

    @Getter
    public enum Bottom {
        BLANC(0, "blanc"),
        NOIR(1, "noir"),
        ROUGE(2, "rouge"),
        VERT(3, "vert"),
        JAUNE(4, "jaune"),
        VIOLET(5, "violet"),
        BLEU(6, "bleu");


        private static final Bottom[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Bottom::getId)).toArray((x$0) -> new Bottom[x$0]);
        private final int id;
        private final ResourceLocation texture;

        Bottom(int id, String name) { this.id = id; this.texture = TensuraMf.create("textures/entity/primordial_daemon/unisex/bottom_" + name + ".png"); }
        public static Bottom byId(int id) { return BY_ID[id % BY_ID.length]; }
    }
}
