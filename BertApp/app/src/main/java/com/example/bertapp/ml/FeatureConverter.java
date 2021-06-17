/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/
package com.example.bertapp.ml;

import com.example.bertapp.tokenization.FullTokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Convert String to features that can be fed into BERT model. */
public final class FeatureConverter {
  private final FullTokenizer tokenizer;
  private final int maxSeqLen;

  public FeatureConverter(
          Map<String, Integer> inputDic, boolean doLowerCase, int maxSeqLen) {
    this.tokenizer = new FullTokenizer(inputDic, doLowerCase);
    this.maxSeqLen = maxSeqLen;
  }

  public Feature convert(String phrase) {
    List<String> origTokens = Arrays.asList(phrase.trim().split("\\s+"));
    List<Integer> tokenToOrigIndex = new ArrayList<>();
    List<String> allDocTokens = new ArrayList<>();
    for (int i = 0; i < origTokens.size(); i++) {
      String token = origTokens.get(i);
      List<String> subTokens = tokenizer.tokenize(token);
      for (String subToken : subTokens) {
        tokenToOrigIndex.add(i);
        allDocTokens.add(subToken);
      }
    }

    // -3 accounts for [CLS], [SEP] and [SEP].
    int maxContextLen = maxSeqLen - 2; // queryTokens.size() - 3;
    if (allDocTokens.size() > maxContextLen) {
      allDocTokens = allDocTokens.subList(0, maxContextLen);
    }

    List<String> tokens = new ArrayList<>();
//    List<Integer> segmentIds = new ArrayList<>();

    // Map token index to original index (in feature.origTokens).
    Map<Integer, Integer> tokenToOrigMap = new HashMap<>();

    // Start of generating the features.
    tokens.add("[CLS]");
//    segmentIds.add(0);

    // For Text Input.
    for (int i = 0; i < allDocTokens.size(); i++) {
      String docToken = allDocTokens.get(i);
      tokens.add(docToken);
//      segmentIds.add(1);
      tokenToOrigMap.put(tokens.size(), tokenToOrigIndex.get(i));
    }

    // For ending mark.
    tokens.add("[SEP]");
//    segmentIds.add(1);

    List<Integer> inputIds = tokenizer.convertTokensToIds(tokens);
//    List<Integer> inputMask = new ArrayList<>(Collections.nCopies(inputIds.size(), 1));

    while (inputIds.size() < maxSeqLen) {
      inputIds.add(0);
//      inputMask.add(0);
//      segmentIds.add(0);
    }

    return new Feature(inputIds, origTokens, tokenToOrigMap); // inputMask, segmentIds, origTokens, tokenToOrigMap);
  }
}
