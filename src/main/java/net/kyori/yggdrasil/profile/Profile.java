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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
  static @NonNull Profile of(final @Nullable UUID id, final @Nullable String name) {
    return new ProfileImpl(id, name);
  }

  /**
   * Gets the id of the profile.
   *
   * @return the id
   */
  /* @Nullable */ UUID id();

  /**
   * Gets the name of the profile.
   *
   * @return the name
   */
  /* @Nullable */ String name();

  /**
   * Gets the properties of the profile.
   *
   * @return the properties
   */
  @NonNull ProfilePropertyMap properties();
}
