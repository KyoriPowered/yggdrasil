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

import com.google.common.base.MoreObjects;
import net.kyori.blizzard.NonNull;
import net.kyori.blizzard.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * A profile.
 */
public interface Profile {
  /**
   * Creates a profile from the specified id and name.
   *
   * @param id the id
   * @param name the name
   * @return the profile
   * @throws IllegalArgumentException if both {@code id} and {@code name} are null
   */
  @NonNull
  static Profile of(@Nullable final UUID id, @Nullable final String name) {
    return new Impl(id, name);
  }

  /**
   * Gets the id of the profile.
   *
   * @return the id
   */
  @Nullable
  UUID id();

  /**
   * Gets the name of the profile.
   *
   * @return the name
   */
  @Nullable
  String name();

  /**
   * Gets the properties of the profile.
   *
   * @return the properties
   */
  @NonNull
  ProfilePropertyMap properties();

  /**
   * A simple implementation of a profile.
   */
  class Impl implements Profile {
    @Nullable private final UUID id;
    @Nullable private final String name;
    private final ProfilePropertyMap properties = new ProfilePropertyMap();

    /**
     * Constructs a profile.
     *
     * @param id the id
     * @param name the name
     * @throws IllegalArgumentException if both {@code id} and {@code name} are null
     */
    protected Impl(@Nullable final UUID id, @Nullable final String name) {
      if(id == null && name == null) {
        throw new IllegalArgumentException("id and name are both null");
      }
      this.id = id;
      this.name = name;
    }

    @Nullable
    @Override
    public UUID id() {
      return this.id;
    }

    @Nullable
    @Override
    public String name() {
      return this.name;
    }

    @NonNull
    @Override
    public ProfilePropertyMap properties() {
      return this.properties;
    }

    @Override
    public boolean equals(final Object other) {
      if(this == other) {
        return true;
      }
      if(other == null || !(other instanceof Profile)) {
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
}
