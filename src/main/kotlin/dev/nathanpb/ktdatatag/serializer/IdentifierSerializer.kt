package dev.nathanpb.ktdatatag.serializer

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier

class IdentifierSerializer : DataSerializer<Identifier> {
    override fun write(tag: NbtCompound, key: String, data: Identifier) {
        tag.putString(key, data.toString())
    }

    override fun read(tag: NbtCompound, key: String): Identifier {
        return Identifier(tag.getString(key))
    }
}
