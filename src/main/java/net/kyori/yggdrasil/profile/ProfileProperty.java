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

/**
 * A profile property.
 */
public interface ProfileProperty {
  /**
   * Creates a new profile property.
   *
   * @param name the name
   * @param value the value
   * @return the profile property
   */
  static @NonNull ProfileProperty of(final @NonNull String name, final @NonNull String value) {
    return new Impl(name, value);
  }

  /**
   * Creates a new profile property with an optional signature.
   *
   * @param name the name
   * @param value the value
   * @param signature the signature
   * @return the profile property
   */
  static @NonNull ProfileProperty of(final @NonNull String name, final @NonNull String value, final @Nullable String signature) {
    return new Impl(name, value, signature);
  }

  /**
   * Gets the name of this profile property.
   *
   * @return the name
   */
  @NonNull String name();

  /**
   * Gets the value of this profile property.
   *
   * @return the value
   */
  @NonNull String value();

  /**
   * Gets the signature of this profile property.
   *
   * @return the signature
   */
  @Nullable String signature();

  /**
   * A simple implementation of a profile property.
   */
  class Impl implements ProfileProperty {
    private final String name;
    private final String value;
    private final @Nullable String signature;

    protected Impl(final @NonNull String name, final @NonNull String value) {
      this(name, value, null);
    }

    protected Impl(final @NonNull String name, final @NonNull String value, final @Nullable String signature) {
      this.name = name;
      this.value = value;
      this.signature = signature;
    }

    @Override
    public @NonNull String name() {
      return this.name;
    }

    @Override
    public @NonNull String value() {
      return this.value;
    }

    @Override
    public @Nullable String signature() {
      return this.signature;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .add("name", this.name)
        .add("value", this.value)
        .add("signature", this.signature)
        .toString();
    }
  }
}
