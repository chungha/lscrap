package com.google.android.bootcamp.memegen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.Executors;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

/**
 * Activity for creating a meme.
 */
public class CreateMemeActivity extends Activity {

  private static final String TAG = CreateMemeActivity.class.getCanonicalName();
  private Store store;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    store = new Store(this.getApplicationContext().getSharedPreferences("places_store", 0));

    setContentView(R.layout.activity_create_meme);

    if (getIntent() != null) {
      if (getIntent().getAction().equals(Intent.ACTION_SEND)) {
        final String message = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        Log.d(TAG, "intent.getAction().equals(Intent.ACTION_SEND) - " + message);

        final Context context = this;
        Executors.newSingleThreadExecutor().submit(new Runnable() {
          @Override
          public void run() {
            Log.d(TAG, "Extraction!");
            TagContentExtractor e = new TagContentExtractor();
            KoreaAddressExtractor ke = new KoreaAddressExtractor();
            try {
              List<String> contents = e.extract(message);
              for (String c : contents) {
                Log.d(TAG, "--- " + c);
                List<String> addresses = ke.extract(c);
                for (String a : addresses) {
                  Log.d(TAG, "+++ " + a);
                  Pair<Double, Double> p = AddressToGeoPointTranslator.run(context, a);
                  if (p != null) {
                    Log.d(TAG, "GeoTag OK - " + String.format("%s - %f / %f", a, p.first, p.second));

                    store.add(message, a, p.first, p.second);
                  }
                }
              }
            } catch (MalformedURLException e1) {
              e1.printStackTrace();
            } catch (BoilerpipeProcessingException e1) {
              e1.printStackTrace();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        });

      }
    }
  }
}
