package dev.nathanpb.ktdatatag.serializer

import dev.nathanpb.ktdatatag.toBlockPos
import dev.nathanpb.ktdatatag.toVec3i
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtLong
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

class BlockPosSerializer : DataSerializer<BlockPos> {
    override fun write(tag: NbtCompound, key: String, data: BlockPos) {
        tag.putLong(key, data.asLong())
    }

    override fun read(tag: NbtCompound, key: String): BlockPos {
        return BlockPos.fromLong(tag.getLong(key))
    }
}

class Vec3dSerializer : DataSerializer<Vec3d> {
    override fun write(tag: NbtCompound, key: String, data: Vec3d) {
        tag.put(key, NbtCompound().also {
            it.putDouble("x", data.x)
            it.putDouble("y", data.y)
            it.putDouble("z", data.z)
        })
    }

    override fun read(tag: NbtCompound, key: String): Vec3d {
        return tag.getCompound(key).run {
            Vec3d(getDouble("x"), getDouble("y"), getDouble("z"))
        }
    }
}

class Vec3iSerializer : DataSerializer<Vec3i> {
    override fun write(tag: NbtCompound, key: String, data: Vec3i) {
        Serializers.BLOCKPOS.write(tag, key, data.toBlockPos())
    }

    override fun read(tag: NbtCompound, key: String): Vec3i {
        return Serializers.BLOCKPOS.read(tag, key).toVec3i()
    }
}

class BlockPosListSerializer : AbstractNbtListSerializer<BlockPos, NbtLong>(
    NbtLong.of(0).type,
    NbtLong::class.java,
    { BlockPos.fromLong(it.longValue()) },
    { NbtLong.of(it.asLong()) }
)

class Vec3dListSerializer : AbstractNbtListSerializer<Vec3d, NbtCompound>(
    NbtCompound().type,
    NbtCompound::class.java,
    { Vec3d(it.getDouble("x"), it.getDouble("y"), it.getDouble("z")) },
    {
        NbtCompound().apply {
            putDouble("x", it.x)
            putDouble("y", it.y)
            putDouble("z", it.z)
        }
    }
)

class Vec3iListSerializer : AbstractNbtListSerializer<Vec3i, NbtLong>(
    NbtLong.of(0).type,
    NbtLong::class.java,
    { BlockPos.fromLong(it.longValue()).toVec3i() },
    { NbtLong.of(it.toBlockPos().asLong()) }
)
