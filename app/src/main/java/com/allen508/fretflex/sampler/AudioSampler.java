package com.allen508.fretflex.sampler;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioSampler extends Thread {

	private static final int RECORDER_SAMPLERATE = 44100;
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private static final int RECORDER_ELEMENT_SIZE = 2;
	private static final int BUFFER_SIZE = 1024 * 4;
	private static final int FFT_SIZE = 1024 * 32;
	private float[] lookupTable;
	private short[] audioBuffer;
	private float[] realSamples;
	private float[] imagSamples;
	private float[] magnitudes;
	private AudioRecord audioRecord;
	private FFT fftInstance;
	private FrequencyAnalyser tuner;

	private boolean isRunning = false;
	private int failCounter = 0;

	public AudioSampler(FrequencyAnalyser guitarTuner) {
		this.tuner = guitarTuner;
		createLookupTable();
		fftInstance = new FFT(FFT_SIZE);
	}

	private void createLookupTable() {
		lookupTable = new float[65536];
		for (int i = 0; i < lookupTable.length; i++)
			lookupTable[i] = (i - 32768f) / 32768f;
	}

	public void short2float(short[] in, float[] out) {
		for (int i = 0; i < Math.min(in.length, out.length); i++)
			out[i] = lookupTable[in[i]+32768];
	}

	public void stopProcessing() {
		isRunning = false;
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	public void run() {
		float realPower;
		float imagPower;
		isRunning = true;

		int minBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
				RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);

		int audioBufferSize = Math.max(minBufferSize, BUFFER_SIZE * RECORDER_ELEMENT_SIZE) * 2;

		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS,
				RECORDER_AUDIO_ENCODING, audioBufferSize);

		audioBuffer = new short[BUFFER_SIZE];
		realSamples = new float[FFT_SIZE];
		imagSamples = new float[FFT_SIZE];
		magnitudes = new float[FFT_SIZE / 2];

		if(audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
			isRunning = false;
			return;
		}

		audioRecord.startRecording();

		while (isRunning) {
			if(audioRecord.read(audioBuffer, 0, audioBuffer.length) != audioBuffer.length) {
				isRunning = false;
				break;
			}

			for (int i = 0; i < realSamples.length; i++) {
				realSamples[i] = 0f;
				imagSamples[i] = 0f;
			}
			short2float(audioBuffer, realSamples);

			fftInstance.applyWindow(realSamples, imagSamples);
			fftInstance.fft(realSamples, imagSamples);

			for (int i = 0; i < realSamples.length / 2; i++) {
				realPower = realSamples[i]/FFT_SIZE;
				realPower = realPower * realPower;
				imagPower = imagSamples[i]/FFT_SIZE;
				imagPower = imagPower * imagPower;
				magnitudes[i] = (float) Math.log10(Math.sqrt(realPower + imagPower));
			}

			if(!tuner.processFFTSamples(magnitudes, RECORDER_SAMPLERATE, (float)RECORDER_SAMPLERATE/(float)BUFFER_SIZE))
				failCounter++;
			else
				failCounter = 0;

			if(failCounter > 10) {
				isRunning = false;
			}
		}

		audioRecord.stop();
		audioRecord.release();

		isRunning = false;
	}

}
