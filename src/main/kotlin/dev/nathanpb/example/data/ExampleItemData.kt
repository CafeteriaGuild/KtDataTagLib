/*
 * Copyright 2020 Nathan P. Bombana
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.nathanpb.example.data

import dev.nathanpb.ktdatatag.data.MutableCompoundData
import dev.nathanpb.ktdatatag.serializer.Serializers
import net.minecraft.nbt.CompoundTag
import java.util.*

class ExampleItemData(tag: CompoundTag) : MutableCompoundData(tag) {
    var counterInt by persistent(10, Serializers.INT)

    var counterDouble by persistent(0.0, Serializers.DOUBLE)

    var string by persistent("that dummy thing", Serializers.STRING)

    var uuid by persistent(UUID.randomUUID(), Serializers.UUID)

    var boolean by persistent(false, Serializers.BOOLEAN)

    var nestedTag by persistent(CompoundTag(), Serializers.COMPOUND_TAG)
}
