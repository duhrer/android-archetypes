package com.blogspot.tonyatkins.archetype.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.blogspot.tonyatkins.archetype.R;

import java.lang.StringBuffer;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by duhrer on 1/11/14.
 */
public class RandomDiceActivity extends Activity {

    public static final int PASSES = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_dice);

        String output = prepareAllStatistics();
        TextView view = (TextView) findViewById(R.id.randomDiceOutputView);
        view.setText(output);
    }

    private String prepareAllStatistics() {
        StringBuffer buffer = new StringBuffer();

        long[] mathRandomData = mathRandomData(PASSES);
        buffer.append("Math Random Single Die Randomness:\n");
        buffer.append(reportOnSingleRandomness(mathRandomData));
        buffer.append("\n\nMath Random Double Die Randomness:\n");
        buffer.append(reportOnDoubleRandomness(mathRandomData));
        buffer.append("\n\nMath Random Combined Double Die Randomness:\n");
        long[] mathRandomCombinedData = mathRandomDoubleData(PASSES / 2);
        buffer.append(reportOnDoubleCombinationRandomness(mathRandomCombinedData));

        long[] randomData = randomData(PASSES);
        buffer.append("\n\nRandom Single Die Randomness:\n");
        buffer.append(reportOnSingleRandomness(randomData));
        buffer.append("\n\nRandom Double Die Randomness:\n");
        buffer.append(reportOnDoubleRandomness(randomData));
        buffer.append("\n\nRandom Combined Double Die Randomness:\n");
        long[] randomCombinedData = randomDoubleData(PASSES / 2);
        buffer.append(reportOnDoubleCombinationRandomness(randomCombinedData));

        long[] secureRandomData = secureRandomData(PASSES);
        buffer.append("\n\nSecure Random Single Die Randomness:\n");
        buffer.append(reportOnSingleRandomness(secureRandomData));
        buffer.append("\n\nSecure Random Double Die Randomness:\n");
        buffer.append(reportOnDoubleRandomness(secureRandomData));
        buffer.append("\n\nSecure Random Combined Double Die Randomness:\n");
        long[] secureRandomCombinedData = secureRandomDoubleData(PASSES/2);
        buffer.append(reportOnDoubleCombinationRandomness(secureRandomCombinedData));

        return buffer.toString();
    }

    private long[] mathRandomData(int passes) {
        long[] data = new long[passes];
        for (int a = 0; a < passes; a++) {
            data[a] = Math.round(Math.random() * 5) + 1;
        }
        return data;
    }

    private long[] mathRandomDoubleData(int passes) {
        long[] data = new long[passes];
        for (int a = 0; a < passes; a++) {
            data[a] = (int) Math.round(Math.random() * 35.0);
        }
        return data;
    }

    private long[] secureRandomData(int passes) {
        SecureRandom sr = new SecureRandom();
        long[] data = new long[passes];
        for (int a = 0; a < passes; a++) {
            data[a] = Math.round(sr.nextInt(6) + 1);
        }
        return data;
    }

    private long[] secureRandomDoubleData(int passes) {
        SecureRandom sr = new SecureRandom();
        long[] data = new long[passes];
        for (int a = 0; a < passes; a++) {
            data[a] = Math.round(sr.nextInt(36));
        }
        return data;
    }

    private long[] randomData(int passes) {
        Random r = new Random();
        long[] data = new long[passes];
        for (int a = 0; a < passes; a++) {
            data[a] = Math.round(r.nextInt(6) + 1);
        }
        return data;
    }

    private long[] randomDoubleData(int passes) {
        Random sr = new Random();
        long[] data = new long[passes];
        for (int a = 0; a < passes; a++) {
            data[a] = Math.round(sr.nextInt(36));
        }
        return data;
    }

    private String reportOnSingleRandomness(long[] data) {
        StringBuffer buffer = new StringBuffer();

        int[] accumulator = new int[7];
        for (int a=0; a < data.length; a++) {
            accumulator[(int) data[a]]++;
        }

        for (int b=1; b <= 6; b++) {
            buffer.append(b + " observed " + accumulator[b] + " times.\n");
        }

        return buffer.toString();
    }

    private String reportOnDoubleRandomness(long[] data) {
        StringBuffer buffer = new StringBuffer();

        int doubles = 0;
        int nonDoubles = 0;
        int[] accumulator = new int[36];
        for (int a=0; a < data.length/2; a+=2) {
            long die1 = data[a];
            long die2 = data[a+1];
            if (die1 == die2) {
                doubles++;
            }
            else {
                nonDoubles++;
            }
            int combination = Math.round(((die1-1) * 6) + (die2-1));

            accumulator[combination]++;
        }

        for (int b=0; b < 36; b++) {
            long die1 = Math.round(b/6) + 1;
            long die2 = (b % 6) + 1;
            buffer.append(die1 + "/" + die2 + " observed " + accumulator[b] + " times.\n");
        }

        buffer.append("Doubles occured " + doubles + " times.\n");
        buffer.append("Non-doubles occured " + nonDoubles + " times.\n");

        return buffer.toString();
    }

    private String reportOnDoubleCombinationRandomness(long[] data) {
        StringBuffer buffer = new StringBuffer();

        int[] accumulator = new int[36];
        int doubles = 0;
        int nonDoubles = 0;
        for (int a=0; a < data.length; a++) {
            long combination = data[a];
            long die1 = Math.round(combination/6) + 1;
            long die2 = (combination % 6) + 1;
            accumulator[Math.round(combination)]++;
            if (die1 == die2) {
                doubles++;
            }
            else {
                nonDoubles++;
            }
        }

        for (int b=0; b < 36; b++) {
            long die1 = Math.round(b/6) + 1;
            long die2 = (b % 6) + 1;
            buffer.append(die1 + "/" + die2 + " observed " + accumulator[b] + " times.\n");
        }

        buffer.append("Doubles occured " + doubles + " times.\n");
        buffer.append("Non-doubles occured " + nonDoubles + " times.\n");

        return buffer.toString();
    }
}
