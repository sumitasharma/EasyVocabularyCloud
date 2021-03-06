package com.example.sumitasharma.easyvocabulary.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.ApiService;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.Example;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.RetroClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.LAST_SAVED_POSITION;

class GetDataFromDictionary {
    private final Context mContext;
    private final JobService mJobService;
    private final JobParameters mJobParameters;
    private HashMap<String, String> words = new HashMap<>();

    GetDataFromDictionary(WordDbPopulatorJobService wordDbPopulatorService, Context context, JobParameters jobParameters) {
        mContext = context;
        mJobParameters = jobParameters;
        mJobService = wordDbPopulatorService;
    }


    private void populateDatabase(final HashMap<String, String> words, final Context context) {
        Timber.i("Inside populateDatabase");
        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();


        // Calling JSON

        for (final String word : words.keySet()) {
            Call<Example> call = api.getMyJSON(word);

            // Enqueue Callback will be call when get response...

            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    if (response.isSuccessful()) {
                        Example example = response.body();
                        List<String> definitions = example.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getSenses().get(0).getDefinitions();
                        String meaning = null;
                        if (definitions != null) {
                            for (String definition : definitions) {
                                meaning = definition;
                            }


                            // Create new empty ContentValues object
                            ContentValues contentValues = new ContentValues();

                            // Put the task description and selected mPriority into the ContentValues
                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD, word);
                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD_MEANING, meaning);
                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD_LEVEL, words.get(word));
                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, false);
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dateFormat.format(new Date());
                            contentValues.put(WordContract.WordsEntry.COLUMN_LAST_UPDATED, date);
                            // Insert the content values via a ContentResolver
                            Timber.i("meaning :" + meaning);
                            mContext.getContentResolver().insert(WordContract.WordsEntry.CONTENT_URI, contentValues);
                        } else {
                            Timber.i("Error while fetching the data from API for word " + example.getResults());
                        }
                    } else {
                        Timber.i("Error while fetching the data from API");
                    }
                    mJobService.jobFinished(mJobParameters, true);
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Timber.i("Error");
                }
            });

        }
    }

    public void dataFromDictionary() {
        int last_saved_position;
        SharedPreferences sharedPref = mContext.getSharedPreferences(LAST_SAVED_POSITION, Context.MODE_PRIVATE);
        last_saved_position = sharedPref.getInt(LAST_SAVED_POSITION, 0);
        Timber.i("dataFrom Dictionary");
        HashMap<String, String> words = new HashMap<>();

        AssetManager assetManager = mContext.getAssets();
        // To load text file
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_easy"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            // skipping till last_saved_position which we get from the shared preferences
            for (int i = 0; i < last_saved_position; i++)
                bufferedReader.readLine();
            for (int i = 0; i < 10 && (mLine = bufferedReader.readLine()) != null; i++) {
                Timber.i("Easy words :" + mLine);
                words.put(mLine, "Easy");
            }
        } catch (IOException e) {
            //log the exception
            Timber.i("Exception during reading of the Assets file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_moderate"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            // skipping till last_saved_position which we get from the shared preferences
            for (int i = 0; i < last_saved_position; i++)
                bufferedReader.readLine();
            for (int i = 0; i < 10 && (mLine = bufferedReader.readLine()) != null; i++) {
                words.put(mLine, "Moderate");
                Timber.i("Moderate Words " + mLine);
            }
        } catch (IOException e) {
            //log the exception
            Timber.i("Exception during reading of the Assets file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                    Timber.i("Error");
                }
            }
        }
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_difficult"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            // skipping till last_saved_position which we get from the shared preferences
            for (int i = 0; i < last_saved_position; i++)
                bufferedReader.readLine();
            for (int i = 0; i < 10 && (mLine = bufferedReader.readLine()) != null; i++) {
                Timber.i("Difficult words " + mLine);
                words.put(mLine, "Difficult");
            }
            sharedPref = mContext.getSharedPreferences(LAST_SAVED_POSITION, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(LAST_SAVED_POSITION, last_saved_position + 10);
            editor.apply();
        } catch (IOException e) {
            //log the exception
            Timber.i("Exception during reading of the Assets file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                    Timber.i("Error");
                }
            }
        }

        populateDatabase(words, mContext);
    }
}
