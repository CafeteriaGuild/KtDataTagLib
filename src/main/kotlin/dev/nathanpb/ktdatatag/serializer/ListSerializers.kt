/*
 * Copyright 2020 Nathan P. Bombana
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.nathanpb.ktdatatag.serializer

import net.minecraft.item.ItemStack
import net.minecraft.nbt.*
import net.minecraft.util.Identifier
import java.util.*

abstract class AbstractListSerializer<T>(private val type: Byte) : DataSerializer<List<T>> {

    abstract fun readList(tag: NbtList): List<T>
    abstract fun writeList(list: List<T>): NbtList

    override fun read(tag: NbtCompound, key: String): List<T> {
        return readList(tag.getList(key, type.toInt()))
    }

    override fun write(tag: NbtCompound, key: String, data: List<T>) {
        tag.put(key, writeList(data))
    }
}

abstract class AbstractNbtListSerializer<T, A: NbtElement> (
    type: Byte,
    private val clasz: Class<A>,
    private val unwrap: (A)->T,
    private val wrap: (T)->A
): AbstractListSerializer<T>(type) {

    override fun readList(tag: NbtList): List<T> {
        return tag.filterIsInstance(clasz).map(unwrap)
    }


    override fun writeList(list: List<T>): NbtList {
        return NbtList().apply {
            list.map(wrap).forEach {
                add(it)
            }
        }
    }
}

class IntListSerializer : AbstractNbtListSerializer<Int, NbtInt>(
    NbtInt.of(0).type,
    NbtInt::class.java,
    NbtInt::intValue,
    NbtInt::of
)

class FloatListSerializer : AbstractNbtListSerializer<Float, NbtFloat>(
    NbtFloat.of(0F).type,
    NbtFloat::class.java,
    NbtFloat::floatValue,
    NbtFloat::of
)

class DoubleListSerializer : AbstractNbtListSerializer<Double, NbtDouble>(
    NbtDouble.of(0.0).type,
    NbtDouble::class.java,
    NbtDouble::doubleValue,
    NbtDouble::of
)

class StringListSerializer : AbstractNbtListSerializer<String, NbtString>(
    NbtString.of("").type,
    NbtString::class.java,
    NbtString::asString,
    NbtString::of
)

class ByteListSerializer : AbstractNbtListSerializer<Byte, NbtByte>(
    NbtByte.of(0x0).type,
    NbtByte::class.java,
    NbtByte::byteValue,
    NbtByte::of
)

class BooleanListSerializer : AbstractNbtListSerializer<Boolean, NbtByte>(
    NbtByte.of(0x0).type,
    NbtByte::class.java,
    { it.byteValue().toInt() != 0 },
    NbtByte::of
)

class ShortListSerializer : AbstractNbtListSerializer<Short, NbtShort>(
    NbtShort.of(0).type,
    NbtShort::class.java,
    NbtShort::shortValue,
    NbtShort::of
)

class LongListSerializer : AbstractNbtListSerializer<Long, NbtLong>(
    NbtLong.of(0).type,
    NbtLong::class.java,
    NbtLong::longValue,
    NbtLong::of
)

class CharListSerializer : AbstractNbtListSerializer<Char, NbtShort>(
    NbtShort.of(0).type,
    NbtShort::class.java,
    { tag -> tag.shortValue().toChar() },
    { char -> NbtShort.of(char.toShort()) }
)

// what a (almost) useless thing
class NbtCompoundListSerializer : AbstractNbtListSerializer<NbtCompound, NbtCompound>(
    NbtCompound().type,
    NbtCompound::class.java,
    { it },
    { it }
)

class UUIDListSerializer : AbstractNbtListSerializer<UUID, NbtString>(
    NbtString.of("").type,
    NbtString::class.java,
    { UUID.fromString(it.asString()) },
    { NbtString.of(it.toString()) }
)

class EnumListSerializer<T: Enum<T>>(clasz: Class<T>) : AbstractNbtListSerializer<T, NbtString>(
    NbtString.of("").type,
    NbtString::class.java,
    { java.lang.Enum.valueOf(clasz, it.asString()) },
    { NbtString.of(it.name) }
)

class IdentifierListSerializer : AbstractNbtListSerializer<Identifier, NbtString>(
    NbtString.of("").type,
    NbtString::class.java,
    { Identifier(it.asString()) },
    { NbtString.of(it.toString()) }
)

class ItemStackListSerializer : AbstractNbtListSerializer<ItemStack, NbtCompound>(
    NbtCompound().type,
    NbtCompound::class.java,
    { ItemStack.fromNbt(it) },
    { it.writeNbt(NbtCompound()) }
)
