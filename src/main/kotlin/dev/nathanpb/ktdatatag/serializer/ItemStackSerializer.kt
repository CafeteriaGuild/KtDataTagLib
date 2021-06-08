package dev.nathanpb.ktdatatag.serializer

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

class ItemStackSerializer : DataSerializer<ItemStack> {
    override fun write(tag: NbtCompound, key: String, data: ItemStack) {
        tag.put(key, data.writeNbt(NbtCompound()))
    }

    override fun read(tag: NbtCompound, key: String): ItemStack {
        return ItemStack.fromNbt(tag.getCompound(key))
    }
}
