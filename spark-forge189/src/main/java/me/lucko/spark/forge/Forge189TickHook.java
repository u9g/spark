/*
 * This file is part of spark.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.lucko.spark.forge;

import me.lucko.spark.common.tick.AbstractTickHook;
import me.lucko.spark.common.tick.TickHook;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Forge189TickHook extends AbstractTickHook implements TickHook {
    private final TickEvent.Type type;

    public Forge189TickHook(TickEvent.Type type) {
        this.type = type;
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (e.phase != TickEvent.Phase.START) {
            return;
        }

        if (e.type != this.type) {
            return;
        }

        onTick();
    }

    @Override
    public void start() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @Override
    public void close() {
        FMLCommonHandler.instance().bus().unregister(this);
    }

}
