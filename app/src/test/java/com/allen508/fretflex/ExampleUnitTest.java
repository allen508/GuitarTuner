package com.allen508.fretflex;

import com.allen508.fretflex.sampler.TuningUtils;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void find_nearest_pitch_correct() {

        float frequency = 84f;

        TuningUtils utils = new TuningUtils();
        TuningUtils.Difference diff = utils.tuneToStandard(frequency);

        boolean isTuned = utils.isTuned(diff);

        System.out.println("Test frequency - " + frequency);
        System.out.println("        Tuning - " + diff.getTuningName());
        System.out.println("      Is tuned - " + isTuned);

    }

}