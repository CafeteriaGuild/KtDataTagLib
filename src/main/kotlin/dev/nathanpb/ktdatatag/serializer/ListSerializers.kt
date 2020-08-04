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

    abstract fun readList(tag: ListTag): List<T>
    abstract fun writeList(list: List<T>): ListTag

    override fun read(tag: CompoundTag, key: String): List<T> {
        return readList(tag.getList(key, type.toInt()))
    }

    override fun write(tag: CompoundTag, key: String, data: List<T>) {
        tag.put(key, writeList(data))
    }
}

abstract class AbstractListTagSerializer<T, A: Tag> (
    type: Byte,
    private val clasz: Class<A>,
    private val unwrap: (A)->T,
    private val wrap: (T)->A
): AbstractListSerializer<T>(type) {

    override fun readList(tag: ListTag): List<T> {
        return tag.filterIsInstance(clasz).map(unwrap)
    }


    override fun writeList(list: List<T>): ListTag {
        return ListTag().apply {
            list.map(wrap).forEach {
                add(it)
            }
        }
    }
}

class IntListSerializer : AbstractListTagSerializer<Int, IntTag>(
    IntTag.of(0).type,
    IntTag::class.java,
    IntTag::getInt,
    IntTag::of
)

class FloatListSerializer : AbstractListTagSerializer<Float, FloatTag>(
    FloatTag.of(0F).type,
    FloatTag::class.java,
    FloatTag::getFloat,
    FloatTag::of
)

class DoubleListSerializer : AbstractListTagSerializer<Double, DoubleTag>(
    DoubleTag.of(0.0).type,
    DoubleTag::class.java,
    DoubleTag::getDouble,
    DoubleTag::of
)

class StringListSerializer : AbstractListTagSerializer<String, StringTag>(
    StringTag.of("").type,
    StringTag::class.java,
    StringTag::asString,
    StringTag::of
)

class ByteListSerializer : AbstractListTagSerializer<Byte, ByteTag>(
    ByteTag.of(0x0).type,
    ByteTag::class.java,
    ByteTag::getByte,
    ByteTag::of
)

class BooleanListSerializer : AbstractListTagSerializer<Boolean, ByteTag>(
    ByteTag.of(0x0).type,
    ByteTag::class.java,
    { it.byte.toInt() != 0 },
    ByteTag::of
)

class ShortListSerializer : AbstractListTagSerializer<Short, ShortTag>(
    ShortTag.of(0).type,
    ShortTag::class.java,
    ShortTag::getShort,
    ShortTag::of
)

class LongListSerializer : AbstractListTagSerializer<Long, LongTag>(
    LongTag.of(0).type,
    LongTag::class.java,
    LongTag::getLong,
    LongTag::of
)

class CharListSerializer : AbstractListTagSerializer<Char, ShortTag>(
    ShortTag.of(0).type,
    ShortTag::class.java,
    { tag -> tag.short.toChar() },
    { char -> ShortTag.of(char.toShort()) }
)

// what a (almost) useless thing
class CompoundTagListSerializer : AbstractListTagSerializer<CompoundTag, CompoundTag>(
    CompoundTag().type,
    CompoundTag::class.java,
    { it },
    { it }
)

class UUIDListSerializer : AbstractListTagSerializer<UUID, StringTag>(
    StringTag.of("").type,
    StringTag::class.java,
    { UUID.fromString(it.asString()) },
    { StringTag.of(it.toString()) }
)

class EnumListSerializer<T: Enum<T>>(clasz: Class<T>) : AbstractListTagSerializer<T, StringTag>(
    StringTag.of("").type,
    StringTag::class.java,
    { java.lang.Enum.valueOf(clasz, it.asString()) },
    { StringTag.of(it.name) }
)

class IdentifierListSerializer : AbstractListTagSerializer<Identifier, StringTag>(
    StringTag.of("").type,
    StringTag::class.java,
    { Identifier(it.asString()) },
    { StringTag.of(it.toString()) }
)

class ItemStackListSerializer : AbstractListTagSerializer<ItemStack, CompoundTag>(
    CompoundTag().type,
    CompoundTag::class.java,
    { ItemStack.fromTag(it) },
    { it.toTag(CompoundTag()) }
)
