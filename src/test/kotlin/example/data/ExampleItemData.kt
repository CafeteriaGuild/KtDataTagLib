/*
 * Copyright 2020 Nathan P. Bombana
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package example.data

import example.TestEnum
import dev.nathanpb.ktdatatag.data.MutableCompoundData
import dev.nathanpb.ktdatatag.serializer.EnumSerializer
import dev.nathanpb.ktdatatag.serializer.Serializers
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import java.util.*

class ExampleItemData(tag: CompoundTag) : MutableCompoundData(tag) {
    var counterInt by persistentDefaulted(10, Serializers.INT)

    var counterDouble by persistentDefaulted(0.0, Serializers.DOUBLE)

    var string by persistentDefaulted("that dummy thing", Serializers.STRING)

    var uuid by persistentDefaulted(UUID.randomUUID(), Serializers.UUID)

    var boolean by persistentDefaulted(false, Serializers.BOOLEAN)

    var nestedTag by persistentDefaulted(CompoundTag(), Serializers.COMPOUND_TAG)

    var enum by persistentDefaulted(TestEnum.A, EnumSerializer(TestEnum::class.java))

    var list by persistentDefaulted(emptyList(), Serializers.INT_LIST)

    var id by persistentDefaulted(Identifier("weirdomod:weirdoitem"), Serializers.IDENTIFIER)

    var nullable by persistent(Serializers.INT.nullable())
}
