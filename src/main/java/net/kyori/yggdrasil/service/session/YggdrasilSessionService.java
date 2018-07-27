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
package net.kyori.yggdrasil.service.session;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.kyori.yggdrasil.exception.AuthenticationException;
import net.kyori.yggdrasil.exception.AuthenticationUnavailableException;
import net.kyori.yggdrasil.profile.Profile;
import net.kyori.yggdrasil.profile.ProfilePropertyMap;
import net.kyori.yggdrasil.profile.ProfilePropertyMapSerializer;
import net.kyori.yggdrasil.profile.ProfileSerializer;
import net.kyori.yggdrasil.service.response.Response;
import net.kyori.yggdrasil.service.session.response.HasJoinedResponse;
import net.kyori.yggdrasil.service.session.response.ProfileResponse;
import net.kyori.yggdrasil.util.UUIDTypeAdapter;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class YggdrasilSessionService implements SessionService {
  private static final HttpUrl BASE_URL = requireNonNull(HttpUrl.parse("https://sessionserver.mojang.com/session/minecraft/"), "could not parse url");
  private final LoadingCache<Profile, Profile> insecureProfiles = Caffeine.newBuilder()
    .expireAfterWrite(6, TimeUnit.HOURS)
    .build(key -> this.fill(key, false));
  private static final Gson GSON = new GsonBuilder()
    .registerTypeAdapter(Profile.class, new ProfileSerializer())
    .registerTypeAdapter(ProfilePropertyMap.class, new ProfilePropertyMapSerializer())
    .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
    .create();
  private final OkHttpClient client;

  public YggdrasilSessionService(final @NonNull OkHttpClient client) {
    this.client = client;
  }

  @Override
  public @Nullable Profile hasJoined(final @NonNull Profile profile, final @NonNull String serverId, final @Nullable InetAddress address) throws AuthenticationException {
    final HttpUrl.Builder url = BASE_URL.newBuilder()
      .addPathSegment("hasJoined")
      .addQueryParameter("username", profile.name())
      .addQueryParameter("serverId", serverId);
    if(address != null) {
      url.addQueryParameter("ip", address.getHostAddress());
    }

    final @Nullable HasJoinedResponse response = this.get(url.build(), HasJoinedResponse.class);
    if(response == null || response.id() == null) {
      return null;
    }

    final Profile result = Profile.of(response.id(), profile.name());
    if(response.properties() != null) {
      result.properties().putAll(response.properties());
    }
    return result;
  }

  @Override
  public @NonNull Profile profile(final @NonNull Profile profile, final boolean secure) throws AuthenticationException {
    if(profile.id() == null) {
      return profile;
    }

    if(!secure) {
      return this.insecureProfiles.get(profile);
    }

    return this.fill(profile, true);
  }

  private Profile fill(final Profile profile, final boolean secure) throws AuthenticationException {
    final HttpUrl.Builder url = BASE_URL.newBuilder()
      .addPathSegment("profile")
      .addPathSegment(UUIDTypeAdapter.string(profile.id()))
      .addQueryParameter("unsigned", String.valueOf(!secure));
    final @Nullable ProfileResponse response = this.get(url.build(), ProfileResponse.class);
    if(response == null) {
      return profile;
    }

    final Profile result = Profile.of(response.id(), response.name());
    result.properties().putAll(response.properties());
    profile.properties().putAll(response.properties());
    return result;
  }

  @Nullable
  private <T extends Response> T get(final HttpUrl url, final Class<T> responseClass) throws AuthenticationException {
    final Request request = new Request.Builder()
      .url(url)
      .build();
    return this.request(request, responseClass);
  }

  @Nullable
  private <T extends Response> T request(final Request request, final Class<T> responseClass) throws AuthenticationException {
    final @Nullable T response;
    try(final ResponseBody body = this.client.newCall(request).execute().body()) {
      if(body == null) {
        throw new AuthenticationUnavailableException("Could not resolve a response");
      }
      response = GSON.fromJson(body.string(), responseClass);
    } catch(final IllegalStateException | IOException | JsonParseException e) {
      throw new AuthenticationUnavailableException("Could not contact session server", e);
    }

    if(response == null) {
      return null;
    }

    if(!Strings.isNullOrEmpty(response.error())) {
      throw new AuthenticationException(response.errorMessage());
    }

    return response;
  }
}
