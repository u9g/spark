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

package me.lucko.spark.sponge;

import com.google.common.collect.Lists;

import me.lucko.spark.common.platform.world.AbstractChunkInfo;
import me.lucko.spark.common.platform.world.CountMap;
import me.lucko.spark.common.platform.world.WorldInfoProvider;

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.world.chunk.WorldChunk;
import org.spongepowered.api.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sponge8WorldInfoProvider implements WorldInfoProvider {
    private final Server server;

    public Sponge8WorldInfoProvider(Server server) {
        this.server = server;
    }

    @Override
    public Result<Sponge7ChunkInfo> poll() {
        Result<Sponge7ChunkInfo> data = new Result<>();

        for (ServerWorld world : this.server.worldManager().worlds()) {
            List<WorldChunk> chunks = Lists.newArrayList(world.loadedChunks());

            List<Sponge7ChunkInfo> list = new ArrayList<>(chunks.size());
            for (WorldChunk chunk : chunks) {
                list.add(new Sponge7ChunkInfo(chunk));
            }

            data.put(world.key().value(), list);
        }

        return data;
    }

    static final class Sponge7ChunkInfo extends AbstractChunkInfo<EntityType<?>> {
        private final CountMap<EntityType<?>> entityCounts;

        Sponge7ChunkInfo(WorldChunk chunk) {
            super(chunk.chunkPosition().x(), chunk.chunkPosition().z());

            this.entityCounts = new CountMap.Simple<>(new HashMap<>());
            for (Entity entity : chunk.entities()) {
                this.entityCounts.increment(entity.type());
            }
        }

        @Override
        public CountMap<EntityType<?>> getEntityCounts() {
            return this.entityCounts;
        }

        @Override
        public String entityTypeName(EntityType<?> type) {
            return EntityTypes.registry().valueKey(type).value();
        }

    }
}
