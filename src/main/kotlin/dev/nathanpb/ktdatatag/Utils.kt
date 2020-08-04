package dev.nathanpb.ktdatatag

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i

fun Vec3i.toBlockPos() = BlockPos(x, y, z)
fun BlockPos.toVec3i() = Vec3i(x, y, z)
