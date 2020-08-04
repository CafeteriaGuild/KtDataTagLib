package dev.nathanpb.ktdatatag.serializer

import dev.nathanpb.ktdatatag.toBlockPos
import dev.nathanpb.ktdatatag.toVec3i
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.LongTag
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

class BlockPosSerializer : DataSerializer<BlockPos> {
    override fun write(tag: CompoundTag, key: String, data: BlockPos) {
        tag.putLong(key, data.asLong())
    }

    override fun read(tag: CompoundTag, key: String): BlockPos {
        return BlockPos.fromLong(tag.getLong(key))
    }
}

class Vec3dSerializer : DataSerializer<Vec3d> {
    override fun write(tag: CompoundTag, key: String, data: Vec3d) {
        tag.put(key, CompoundTag().also {
            it.putDouble("x", data.x)
            it.putDouble("y", data.y)
            it.putDouble("z", data.z)
        })
    }

    override fun read(tag: CompoundTag, key: String): Vec3d {
        return tag.getCompound(key).run {
            Vec3d(getDouble("x"), getDouble("y"), getDouble("z"))
        }
    }
}

class Vec3iSerializer : DataSerializer<Vec3i> {
    override fun write(tag: CompoundTag, key: String, data: Vec3i) {
        Serializers.BLOCKPOS.write(tag, key, data.toBlockPos())
    }

    override fun read(tag: CompoundTag, key: String): Vec3i {
        return Serializers.BLOCKPOS.read(tag, key).toVec3i()
    }
}

class BlockPosListSerializer : AbstractListTagSerializer<BlockPos, LongTag>(
    LongTag.of(0).type,
    LongTag::class.java,
    { BlockPos.fromLong(it.long) },
    { LongTag.of(it.asLong()) }
)

class Vec3dListSerializer : AbstractListTagSerializer<Vec3d, CompoundTag>(
    CompoundTag().type,
    CompoundTag::class.java,
    { Vec3d(it.getDouble("x"), it.getDouble("y"), it.getDouble("z")) },
    {
        CompoundTag().apply {
            putDouble("x", it.x)
            putDouble("y", it.y)
            putDouble("z", it.z)
        }
    }
)

class Vec3iListSerializer : AbstractListTagSerializer<Vec3i, LongTag>(
    LongTag.of(0).type,
    LongTag::class.java,
    { BlockPos.fromLong(it.long).toVec3i() },
    { LongTag.of(it.toBlockPos().asLong()) }
)
