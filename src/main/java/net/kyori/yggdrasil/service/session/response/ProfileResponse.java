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
package net.kyori.yggdrasil.service.session.response;

import net.kyori.yggdrasil.profile.ProfilePropertyMap;
import net.kyori.yggdrasil.service.response.Response;

import java.util.UUID;

/**
 * A response for a request submitted to end {@code profile} endpoint.
 */
public class ProfileResponse extends Response {
  private UUID id;
  private String name;
  private ProfilePropertyMap properties;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public UUID id() {
    return this.id;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String name() {
    return this.name;
  }

  /**
   * Gets the properties.
   *
   * @return the properties
   */
  public ProfilePropertyMap properties() {
    return this.properties;
  }
}
