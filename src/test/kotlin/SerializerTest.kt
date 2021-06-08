
import org.junit.jupiter.api.Assertions.*
import dev.nathanpb.ktdatatag.serializer.DataSerializer
import dev.nathanpb.ktdatatag.serializer.Serializers
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i
import org.junit.jupiter.api.Test
import java.util.*

class SerializerTest {
    private fun <T> testSerializer(serializer: DataSerializer<T>, sampleData: T, areEquals: (Any?, Any?)->Unit = ::assertEquals) {
        val tag = NbtCompound()
        val key = "foo"

        assertFalse(serializer.has(tag, key))
        assertFalse(tag.contains(key))

        serializer.write(tag, key, sampleData)

        areEquals(sampleData != null, serializer.has(tag, key))
        areEquals(sampleData != null, tag.contains(key))

        areEquals(sampleData, serializer.read(tag, key))

        if (!serializer.isNullable()) {
            testSerializer(serializer.nullable(), sampleData, areEquals)
            testSerializer(serializer.nullable(), null, areEquals)
        }
    }

    @Test
    fun int() {
        testSerializer(Serializers.INT, 32)
    }

    @Test
    fun float() {
        testSerializer(Serializers.FLOAT, 32F)
    }

    @Test
    fun double() {
        testSerializer(Serializers.DOUBLE, 32.0)
    }

    @Test
    fun string() {
        testSerializer(Serializers.STRING, "foobar")
    }

    @Test
    fun byte() {
        testSerializer(Serializers.BYTE, 0b0001)
    }

    @Test
    fun boolean() {
        testSerializer(Serializers.BOOLEAN, false)
    }

    @Test
    fun short() {
        testSerializer(Serializers.SHORT, 16.toShort())
    }

    @Test
    fun long() {
        testSerializer(Serializers.LONG, Long.MAX_VALUE)
    }

    @Test
    fun char() {
        testSerializer(Serializers.CHAR, 'A')
    }

    @Test
    fun NbtCompound() {
        val tag = NbtCompound().apply {
            putBoolean("boolean", true)
            putInt("int", Int.MAX_VALUE)
            putByte("byte", 0b1)
        }
        testSerializer(Serializers.COMPOUND_TAG, tag)
    }

    @Test
    fun uuid() {
        testSerializer(Serializers.UUID, UUID.randomUUID())
    }

    @Test
    fun identifier() {
        testSerializer(Serializers.IDENTIFIER, Identifier("dml", "emeritus_mask"))
    }

    @Test
    fun itemStack() {
        testSerializer(Serializers.ITEM_STACK, ItemStack(Items.TOTEM_OF_UNDYING)) { a, b ->
            if (a is ItemStack && b is ItemStack) {
                assertTrue(ItemStack.areEqual(a, b))
            } else {
                assertEquals(a, b)
            }
        }
    }

    @Test
    fun blockPos() {
        testSerializer(Serializers.BLOCKPOS, BlockPos(32, 16, 8))
    }

    @Test
    fun vec3d() {
        testSerializer(Serializers.VEC3D, Vec3d(32.0, 16.0, 8.0))
    }

    @Test
    fun vec3i() {
        testSerializer(Serializers.VEC3I, Vec3i(8, 16, 32))
    }
}
