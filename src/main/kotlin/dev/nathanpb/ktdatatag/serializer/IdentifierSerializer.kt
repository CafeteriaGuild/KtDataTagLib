package dev.nathanpb.ktdatatag.serializer

import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier

class IdentifierSerializer : DataSerializer<Identifier> {
    override fun write(tag: CompoundTag, key: String, data: Identifier) {
        tag.putString(key, data.toString())
    }

    override fun read(tag: CompoundTag, key: String): Identifier {
        return Identifier(tag.getString(key))
    }
}
