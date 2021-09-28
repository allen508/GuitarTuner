package com.allen508.strumKit.sampler;

public class FrequencyAnalyser {

	private static final String TAG = "FrequencyAnalyser";
	private static final int LOW_CUT_OFF_FREQUENCY = 50;
	private static final int HIGH_CUT_OFF_FREQUENCY = 2500;
	private static final int HPS_ORDER = 3;

	private FrequencyAnalyserCallback callback;

	private float[] magnitudes;
	private float[] hps;
	private float updateRate;
	private long lastUpdateTimestamp;
	private float hzPerSample;
	private float strongestFrequency;
	private float detectedFrequency;
	private float lastDetectedFrequency;
	private int pitchHoldCounter = 0;
	private HPS hpsInstance;

	public FrequencyAnalyser(FrequencyAnalyserCallback callback) {
		this.callback = callback;
		hpsInstance = new HPS();
	}

	public boolean processFFTSamples(float[] mag, int sampleRate, float updateRate) {

		this.lastUpdateTimestamp = System.currentTimeMillis();
		this.updateRate = updateRate;
		this.magnitudes = mag;

		hzPerSample = ((float)(sampleRate / 2)) / mag.length;

		for (int i = 0; i < LOW_CUT_OFF_FREQUENCY / hzPerSample; i++)
			mag[i] = Float.NEGATIVE_INFINITY;
		for (int i = (int)(HIGH_CUT_OFF_FREQUENCY / hzPerSample); i < mag.length; i++)
			mag[i] = Float.NEGATIVE_INFINITY;

		if(hps == null || hps.length != mag.length)
			hps = new float[mag.length];
		hpsInstance.hps(mag, hps, HPS_ORDER);

		int maxIndex = 0;
		for (int i = 1; i < hps.length; i++) {
			if(hps[maxIndex] < hps[i])
				maxIndex = i;
		}
		strongestFrequency = maxIndex * hzPerSample;
		detectedFrequency = strongestFrequency;

		if(detectedFrequency > lastDetectedFrequency*0.99 && detectedFrequency < lastDetectedFrequency*1.01) {
			pitchHoldCounter++;
		} else {
			pitchHoldCounter = 0;
		}

		boolean success = callback.process(this);

		lastDetectedFrequency = detectedFrequency;

		return success;
	}

	public float getDetectedFrequency() {
		return detectedFrequency;
	}

	public int getPitchHoldCounter() { return pitchHoldCounter; }

	public interface FrequencyAnalyserCallback {
		public boolean process(FrequencyAnalyser analyser);
	}

}
