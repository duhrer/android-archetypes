package com.blogspot.tonyatkins.archetype.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.tonyatkins.archetype.R;

import java.util.ArrayList;

public class SpeechRecognizerActivity extends Activity {
    protected SpeechRecognizer sr;
    protected TextView resultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_recognizer);

        // Wire up the recognizer button
        Button recognizerButton = (Button) findViewById(R.id.recognizerVoiceInputButton);
        recognizerButton.setOnClickListener(new LaunchRecognizerListener());

        this.resultsTextView = (TextView) findViewById(R.id.recognizerResultsTextView);

        this.sr=SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new SimpleRecognitionListener());
    }

    private class LaunchRecognizerListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
            sr.startListening(intent);
        }
    }

    private class SimpleRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {
            StringBuffer output = new StringBuffer();
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                if (i > 0) { output.append(", "); }
                output.append(data.get(i));
            }
            resultsTextView.setText(output.toString());
        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }
}
