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

import net.minecraft.nbt.NbtCompound

class IntSerializer : AbstractFunctionalDataSerializer<Int>(
    NbtCompound::getInt,
    NbtCompound::putInt
)

class FloatSerializer : AbstractFunctionalDataSerializer<Float>(
    NbtCompound::getFloat,
    NbtCompound::putFloat
)

class DoubleSerializer : AbstractFunctionalDataSerializer<Double>(
    NbtCompound::getDouble,
    NbtCompound::putDouble
)

class StringSerializer : AbstractFunctionalDataSerializer<String>(
    NbtCompound::getString,
    NbtCompound::putString
)

class ByteSerializer : AbstractFunctionalDataSerializer<Byte>(
    NbtCompound::getByte,
    NbtCompound::putByte
)

class BooleanSerializer : AbstractFunctionalDataSerializer<Boolean>(
    NbtCompound::getBoolean,
    NbtCompound::putBoolean
)

class ShortSerializer : AbstractFunctionalDataSerializer<Short>(
    NbtCompound::getShort,
    NbtCompound::putShort
)

class LongSerializer : AbstractFunctionalDataSerializer<Long>(
    NbtCompound::getLong,
    NbtCompound::putLong
)

class CharSerializer : AbstractFunctionalDataSerializer<Char>(
    { tag, key -> tag.getShort(key).toInt().toChar() },
    { tag, key, value -> tag.putShort(key, value.code.toShort()) }
)
