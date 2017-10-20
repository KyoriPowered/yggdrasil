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
package net.kyori.yggdrasil.service.session;

import net.kyori.blizzard.NonNull;
import net.kyori.blizzard.Nullable;
import net.kyori.yggdrasil.exception.AuthenticationException;
import net.kyori.yggdrasil.profile.Profile;

import java.net.InetAddress;

/**
 * A session service.
 */
public interface SessionService {
  /**
   * Checks if the specified profile has joined a server.
   *
   * @param profile the partial profile
   * @param serverId the server id
   * @param address the address connected from
   * @return the filled profile
   * @throws AuthenticationException if an exception occurs while checking
   */
  @Nullable
  Profile hasJoined(@NonNull final Profile profile, @NonNull final String serverId, @Nullable final InetAddress address) throws AuthenticationException;

  /**
   * Gets a filled profile.
   *
   * @param profile the partial profile
   * @param secure if the profile should be securely filled
   * @return the filled profile
   * @throws AuthenticationException if an exception occurs while filling
   */
  @NonNull
  Profile profile(@NonNull final Profile profile, final boolean secure) throws AuthenticationException;
}
