
import dev.nathanpb.ktdatatag.data.MutableCompoundData
import dev.nathanpb.ktdatatag.serializer.Serializers
import net.minecraft.nbt.CompoundTag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MutableCompoundDataTest {

    @Test
    fun testSimple() {
        val data = object: MutableCompoundData(CompoundTag()) {
            var field by persistent(Serializers.STRING)
        }

        assertEquals("", data.field)

        data.field = "foobar"
        assertEquals("foobar", data.field)
    }

    @Test
    fun testSimpleNullable() {
        val data = object: MutableCompoundData(CompoundTag()) {
            var field by persistent(Serializers.STRING.nullable())
        }

        assertNull(data.field)

        data.field = "foobar"
        assertEquals("foobar", data.field)

        data.field = null
        assertNull(data.field)
    }

    @Test
    fun testDefaulted() {
        val data = object: MutableCompoundData(CompoundTag()) {
            var field by persistentDefaulted("foobar", Serializers.STRING)
        }

        assertEquals("foobar", data.field)

        data.field = "notfoobar"
        assertEquals("notfoobar", data.field)
    }

    @Test
    fun testDefaultedNullable() {
        val data = object: MutableCompoundData(CompoundTag()) {
            var field by persistentDefaulted("foobar", Serializers.STRING.nullable())
        }

        assertEquals("foobar", data.field)

        data.field = null
        assertEquals("foobar", data.field)

        data.field = "notfoobar"
        assertEquals("notfoobar", data.field)
    }
}
