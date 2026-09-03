package com.github.skillfi.tensura_mf.api.energy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public enum NetworkType implements StringRepresentable {
    EMPTY("empty"),
    GENERATOR("generator"),
    PIPE("pipe"),
    RECEIVER("receiver");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}
