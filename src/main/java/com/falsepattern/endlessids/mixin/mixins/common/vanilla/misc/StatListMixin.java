package com.falsepattern.endlessids.mixin.mixins.common.vanilla.misc;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import net.minecraft.stats.StatList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StatList.class)
public abstract class StatListMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = VanillaConstants.blockIDCount), @Constant(intValue = 32000)},
                    require = 4)
    private static int modifyMineBlockStatArraySize(int constant) {
        return ExtendedConstants.blockIDCount;
    }

}
