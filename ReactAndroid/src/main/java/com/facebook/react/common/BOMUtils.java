/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.react.common;

import java.io.IOException;
import java.nio.charset.Charset;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;

public class BOMUtils {
  private static final Options UNICODE_BOMS = Options.of(
      ByteString.decodeHex("efbbbf"), // UTF-8-BOM
      ByteString.decodeHex("feff"), // UTF-16-BE-BOM
      ByteString.decodeHex("fffe") // UTF-16-LE-BOM
  );

  public static Charset readBomAsCharset(BufferedSource source, Charset charset) throws IOException {
      switch(source.select(UNICODE_BOMS)) {
          case 0:
            return StandardCharsets.UTF_8;
          case 1:
            return StandardCharsets.UTF_16BE;
          case 2:
            return StandardCharsets.UTF_16LE;
          case -1:
            return charset;
          default:
            throw new IOException();
      }
  }
}
