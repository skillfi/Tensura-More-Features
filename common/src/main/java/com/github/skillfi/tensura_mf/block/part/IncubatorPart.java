package com.github.skillfi.tensura_mf.block.part;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;


@RequiredArgsConstructor
@Getter
public enum IncubatorPart implements StringRepresentable {
    BASE("base"),
    GLASS("glass"),
    TOP("top");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}
