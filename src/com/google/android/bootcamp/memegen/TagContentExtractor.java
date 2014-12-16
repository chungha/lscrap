package com.google.android.bootcamp.memegen;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.DefaultExtractor;

/**
 * Created by chungha on 12/15/14.
 */
public class TagContentExtractor implements Extractor {
  @Override
  public List<String> extract(String string) throws MalformedURLException, BoilerpipeProcessingException {
    URL url = new URL(string);
    return Arrays.asList(DefaultExtractor.INSTANCE.getText(url).split("\r\n.,"));
  }
}
