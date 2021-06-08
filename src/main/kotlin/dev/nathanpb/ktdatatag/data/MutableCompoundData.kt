/*
 * Copyright 2020 Nathan P. Bombana
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.nathanpb.ktdatatag.data

import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.minecraft.nbt.NbtCompound
import java.util.logging.Logger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class MutableCompoundData(val tag: NbtCompound) {
    val LOGGER = Logger.getLogger("KtDataTagLib")

    protected fun <T>persistentDefaulted(defaultValue: T, serializer: DataSerializer<T>, key: String? = null) : ReadWriteProperty<MutableCompoundData, T> {
        return object: ReadWriteProperty<MutableCompoundData, T> {

            init {
                if (serializer.isNullable()) {
                    LOGGER.warning("Detected the use of nullable serializer in persistentDefaulted accessor. Using non-defaulted accessor maybe will make more sense")
                }
            }

            override fun getValue(thisRef: MutableCompoundData, property: KProperty<*>): T {
                return if (serializer.has(tag, key ?: property.name)) {
                    serializer.read(tag, key ?: property.name) ?: defaultValue
                } else defaultValue
            }

            override fun setValue(thisRef: MutableCompoundData, property: KProperty<*>, value: T) {
                serializer.write(tag, key ?: property.name, value ?: defaultValue)
            }

        }
    }

    protected fun <T>persistent(serializer: DataSerializer<T>, key: String? = null) : ReadWriteProperty<MutableCompoundData, T> {
        return object: ReadWriteProperty<MutableCompoundData, T> {

            init {
                if (!serializer.isNullable()) {
                    LOGGER.warning("Attempt to use non-nullable serializer in non-defaulted accessor. Its recommended to switch to nullable accessors due null-safety purposes")
                }
            }

            override fun getValue(thisRef: MutableCompoundData, property: KProperty<*>): T {
                val has = serializer.has(tag, key ?: property.name)

                return if (has || (!has && !serializer.isNullable())) {
                    if (!has && !serializer.isNullable()) {
                        LOGGER.warning("Attempted to read a tag of key \"${key ?: property.name}\" that is not contained in the compound from a non-defaulted accessor. Attempting to return the internal default value anyway")
                    }
                    serializer.read(tag, key ?: property.name)
                } else null as T
            }

            override fun setValue(thisRef: MutableCompoundData, property: KProperty<*>, value: T) {
                serializer.write(tag, key ?: property.name, value)
            }

        }
    }

    override fun toString() = tag.toString()
}
