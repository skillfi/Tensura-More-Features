package com.github.skillfi.tensura_mf.neoforge.data;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.api.data.IResource;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class TensuraMfLanguageProvider extends LanguageProvider {
    public TensuraMfLanguageProvider(PackOutput output, String language) {
        super(output, TensuraMf.MOD_ID, language);
    }


    public void add(IResource key, String name) {
        add(key.getNameTranslationKey(), name);
    }
}
