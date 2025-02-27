package com.falsepattern.endlessids.mixin.mixins.client.vanilla;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@SideOnly(Side.CLIENT)
@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {

    @Shadow private WorldClient clientWorldController;


    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) throws IOException {
        int var2 = packetIn.func_148920_c().chunkXPos * 16;
        int var3 = packetIn.func_148920_c().chunkZPos * 16;
        if (packetIn.func_148921_d() != null) {
            DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(packetIn.func_148921_d()));

            for(int var5 = 0; var5 < packetIn.func_148922_e(); ++var5) {
                short var6 = dataInput.readShort();
                short idLSB = dataInput.readShort();
                byte idMSB = dataInput.readByte();
                int id = (idLSB & 0xFFFF) | ((idMSB & 0xFF) << 16);
                int var9 = dataInput.readByte() & 15;
                int var10 = var6 >> 12 & 15;
                int var11 = var6 >> 8 & 15;
                int var12 = var6 & 255;
                this.clientWorldController.func_147492_c(var10 + var2, var12, var11 + var3, Block.getBlockById(id), var9);
            }
        }

    }
}
