/*
 * This file is part of yggdrasil, licensed under the MIT License.
 *
 * Copyright (c) 2017 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.yggdrasil.profile;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.kyori.blizzard.Nullable;

import java.lang.reflect.Type;
import java.util.UUID;

public class ProfileSerializer implements JsonDeserializer<Profile>, JsonSerializer<Profile> {
  @Override
  public Profile deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    final JsonObject object = (JsonObject) json;
    @Nullable final UUID id = object.has("id") ? context.deserialize(object.get("id"), UUID.class) : null;
    @Nullable final String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
    return Profile.of(id, name);
  }

  @Override
  public JsonElement serialize(final Profile profile, final Type typeOfSrc, final JsonSerializationContext context) {
    final JsonObject object = new JsonObject();

    @Nullable final UUID id = profile.id();
    if(id != null) {
      object.add("id", context.serialize(id));
    }

    @Nullable final String name = profile.name();
    if(name != null) {
      object.addProperty("name", name);
    }

    return object;
  }
}
