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

import net.minecraft.nbt.CompoundTag

class IntSerializer : AbstractFunctionalDataSerializer<Int>(
    CompoundTag::getInt,
    CompoundTag::putInt
)

class FloatSerializer : AbstractFunctionalDataSerializer<Float>(
    CompoundTag::getFloat,
    CompoundTag::putFloat
)

class DoubleSerializer : AbstractFunctionalDataSerializer<Double>(
    CompoundTag::getDouble,
    CompoundTag::putDouble
)

class StringSerializer : AbstractFunctionalDataSerializer<String>(
    CompoundTag::getString,
    CompoundTag::putString
)

class ByteSerializer : AbstractFunctionalDataSerializer<Byte>(
    CompoundTag::getByte,
    CompoundTag::putByte
)

class BooleanSerializer : AbstractFunctionalDataSerializer<Boolean>(
    CompoundTag::getBoolean,
    CompoundTag::putBoolean
)

class ShortSerializer : AbstractFunctionalDataSerializer<Short>(
    CompoundTag::getShort,
    CompoundTag::putShort
)

class LongSerializer : AbstractFunctionalDataSerializer<Long>(
    CompoundTag::getLong,
    CompoundTag::putLong
)

class CharSerializer : AbstractFunctionalDataSerializer<Char>(
    { tag, key -> tag.getShort(key).toChar() },
    { tag, key, value -> tag.putShort(key, value.toShort()) }
)
