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
import net.kyori.yggdrasil.profile.ProfilePropertyMap;
import net.kyori.yggdrasil.profile.ProfilePropertyMapImpl;

import java.lang.reflect.Type;
import java.util.Map;

public class ProfilePropertyMapSerializer implements JsonDeserializer<ProfilePropertyMap>, JsonSerializer<ProfilePropertyMap> {
  @Override
  public ProfilePropertyMap deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
    final ProfilePropertyMap result = new ProfilePropertyMapImpl();

    if(json instanceof JsonObject) {
      for(final Map.Entry<String, JsonElement> entry : ((JsonObject) json).entrySet()) {
        if(entry.getValue() instanceof JsonArray) {
          for(final JsonElement element : ((JsonArray) entry.getValue())) {
            result.put(entry.getKey(), ProfileProperty.of(entry.getKey(), element.getAsString()));
          }
        }
      }
    } else if(json instanceof JsonArray) {
      for(final JsonElement element : (JsonArray) json) {
        if(element instanceof JsonObject) {
          final JsonObject object = (JsonObject) element;
          final String name = object.getAsJsonPrimitive("name").getAsString();
          final String value = object.getAsJsonPrimitive("value").getAsString();
          if(object.has("signature")) {
            result.put(name, ProfileProperty.of(name, value, object.getAsJsonPrimitive("signature").getAsString()));
          } else {
            result.put(name, ProfileProperty.of(name, value));
          }
        }
      }
    }

    return result;
  }

  @Override
  public JsonElement serialize(final ProfilePropertyMap src, final Type typeOfSrc, final JsonSerializationContext context) {
    final JsonArray result = new JsonArray();

    for(final ProfileProperty property : src.values()) {
      result.add(context.serialize(property));
    }

    return result;
  }
}
