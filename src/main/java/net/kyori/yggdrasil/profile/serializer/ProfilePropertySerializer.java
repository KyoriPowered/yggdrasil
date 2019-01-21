/*
 * This file is part of yggdrasil, licensed under the MIT License.
 *
 * Copyright (c) 2017-2018 KyoriPowered
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
package net.kyori.yggdrasil.profile.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.kyori.yggdrasil.profile.ProfileProperty;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Type;

public class ProfilePropertySerializer implements JsonDeserializer<ProfileProperty>, JsonSerializer<ProfileProperty> {
  @Override
  public ProfileProperty deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    final JsonObject object = json.getAsJsonObject();
    final String name = object.getAsJsonPrimitive("name").getAsString();
    final String value = object.getAsJsonPrimitive("value").getAsString();
    if(object.has("signature")) {
      return ProfileProperty.of(name, value, object.getAsJsonPrimitive("signature").getAsString());
    } else {
      return ProfileProperty.of(name, value);
    }
  }

  @Override
  public JsonElement serialize(final ProfileProperty src, final Type typeOfSrc, final JsonSerializationContext context) {
    final JsonArray result = new JsonArray();

    final JsonObject object = new JsonObject();

    object.addProperty("name", src.name());
    object.addProperty("value", src.value());

    final @Nullable String signature = src.signature();
    if(signature != null) {
      object.addProperty("signature", signature);
    }

    return result;
  }
}
