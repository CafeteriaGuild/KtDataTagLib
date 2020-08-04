package dev.nathanpb.ktdatatag.serializer

import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag

class ItemStackSerializer : DataSerializer<ItemStack> {
    override fun write(tag: CompoundTag, key: String, data: ItemStack) {
        tag.put(key, data.toTag(CompoundTag()))
    }

    override fun read(tag: CompoundTag, key: String): ItemStack {
        return ItemStack.fromTag(tag.getCompound(key))
    }
}
