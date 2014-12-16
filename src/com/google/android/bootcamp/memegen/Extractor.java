package com.google.android.bootcamp.memegen;

import java.net.MalformedURLException;
import java.util.List;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

/**
 * Created by chungha on 12/15/14.
 */
public interface Extractor {
    public List<String> extract(String string) throws Exception;
}
