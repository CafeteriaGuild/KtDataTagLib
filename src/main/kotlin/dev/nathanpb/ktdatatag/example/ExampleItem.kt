/*
 * Copyright 2020 Nathan P. Bombana
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.nathanpb.ktdatatag.example

import dev.nathanpb.ktdatatag.example.data.ExampleItemData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import java.util.*
import kotlin.random.Random

class ExampleItem : Item(Settings()){
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient) {
            val stack = user.getStackInHand(hand)
            val data = ExampleItemData(stack.orCreateNbt)

            user.sendMessage(Text.of("Before change: $data"), false)

            data.counterInt++
            data.counterDouble += 0.00000000002
            data.boolean = !data.boolean
            data.string = "Hey this is a new string ${data.counterInt}"
            data.uuid = UUID.randomUUID()
            data.nestedTag.putInt("randomInt", Random.nextInt())
            data.enum = TestEnum.values().random()
            data.list = data.list + data.list.size
            data.id = Registries.ITEM.ids.random()
            // data.nullable = 1

            user.sendMessage(Text.of("After change: $data ${data.nullable}"), false)
        }
        return super.use(world, user, hand)
    }
}
