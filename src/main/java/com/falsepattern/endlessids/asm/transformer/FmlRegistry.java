package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import lombok.var;
import org.objectweb.asm.tree.FieldNode;
import com.falsepattern.endlessids.asm.AsmTransformException;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class FmlRegistry implements IClassNodeTransformer
{
    public FmlRegistry() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        var field = AsmUtil.findField(cn, "MAX_BLOCK_ID", true);
        if (field != null) {
            field.value = ExtendedConstants.maxBlockID;
        }
        field = AsmUtil.findField(cn, "MAX_ITEM_ID", true);
        if (field != null) {
            field.value = ExtendedConstants.maxBlockID;
        }
        boolean found = false;
        for (final MethodNode method : cn.methods) {
            if (AsmUtil.transformInlinedSizeMethod(cn, method, VanillaConstants.maxBlockID, ExtendedConstants.maxBlockID, true)) {
                found = true;
            }
            AsmUtil.transformInlinedSizeMethod(cn, method, 31999, ExtendedConstants.maxBlockID, true);
        }
        if (!found) {
            throw new AsmTransformException("can't find 4095 constant in any method");
        }
    }
}
