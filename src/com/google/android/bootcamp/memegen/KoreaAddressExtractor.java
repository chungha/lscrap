package com.google.android.bootcamp.memegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chungha on 12/15/14.
 */
public class KoreaAddressExtractor implements Extractor {
    static String [] firstLayerTokens = {
      "서울",
      "제주",
      "인천",
      "부산",
      "경기",
      "강원",
      "충청",
      "전라",
      "경상"
    };
    static String [] secondLayerPatterns = {
      " .*도 .*시 .*동 [1-9][0-9]*",
      " .*시 .*구 .*동 .* [1-9][0-9]*",
    };

    @Override
    public List<String> extract(String s) {
      Set<String> result = new HashSet<String>();
      for (String token : firstLayerTokens) {
        if (s.contains(token)) {
          for (String p : secondLayerPatterns) {
            Pattern pattern = Pattern.compile(p);
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
              result.add(s.substring(matcher.start(), matcher.end()).trim());
            }
          }
        }
      }
      return new ArrayList<String>(result);
    }
}
