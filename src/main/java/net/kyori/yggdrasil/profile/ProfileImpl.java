/*
 * This file is part of yggdrasil, licensed under the MIT License.
 *
 * Copyright (c) 2017-2019 KyoriPowered
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

import com.google.common.base.MoreObjects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * A simple implementation of a profile.
 */
public class ProfileImpl implements Profile {
  private final @Nullable UUID id;
  private final @Nullable String name;
  private final ProfilePropertyMap properties = ProfilePropertyMap.create();

  /**
   * Constructs a profile.
   *
   * @param id the id
   * @param name the name
   * @throws IllegalArgumentException if both {@code id} and {@code name} are null
   */
  protected ProfileImpl(final @Nullable UUID id, final @Nullable String name) {
    if(id == null && name == null) {
      throw new IllegalArgumentException("id and name are both null");
    }
    this.id = id;
    this.name = name;
  }

  @Override
  public /* @Nullable */ UUID id() {
    return this.id;
  }

  @Override
  public /* @Nullable */ String name() {
    return this.name;
  }

  @Override
  public @NonNull ProfilePropertyMap properties() {
    return this.properties;
  }

  @Override
  public boolean equals(final Object other) {
    if(this == other) {
      return true;
    }
    if(!(other instanceof Profile)) {
      return false;
    }
    final Profile that = (Profile) other;
    return Objects.equals(this.id, that.id())
      && Objects.equals(this.name, that.name());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("id", this.id)
      .add("name", this.name)
      .add("properties", this.properties)
      .toString();
  }
}
