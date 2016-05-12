/* Copyright 2016 Google Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.codegen.php;

import com.google.api.Service;
import com.google.api.codegen.ApiaryConfig;
import com.google.api.codegen.DiscoveryProvider;
import com.google.api.codegen.GeneratedResult;
import com.google.api.codegen.SnippetDescriptor;
import com.google.common.collect.Multimap;
import com.google.protobuf.Api;
import com.google.protobuf.Method;

import java.io.IOException;

/**
 * DiscoveryProvider for PHP discovery doc codegen.
 */
public class PhpDiscoveryProvider implements DiscoveryProvider {
  private final PhpDiscoveryContext context;
  private final PhpProvider provider;

  public PhpDiscoveryProvider(Service service, ApiaryConfig apiaryConfig) {
    this.context = new PhpDiscoveryContext(service, apiaryConfig);
    this.provider = new PhpProvider();
  }

  @Override
  public GeneratedResult generateFragments(Method method, SnippetDescriptor snippetDescriptor) {
    return provider.generate(method, snippetDescriptor, context);
  }

  @Override
  public void output(String outputPath, Multimap<Method, GeneratedResult> methods)
      throws IOException {
    Api api = getService().getApis(0);
    String root =
        "autogenerated/"
            + api.getName()
            + "/"
            + api.getVersion()
            + "/"
            + context.getApiRevision()
            + "/";
    provider.output(root, outputPath, methods);
  }

  @Override
  public Service getService() {
    return context.getService();
  }
}