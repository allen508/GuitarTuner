package com.allen508.fretflex.sampler;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.Arrays;

public class SampleRecorder {

    private ReadCallbackHandler onSampleRead;

    private int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    private int BytesPerElement = 2; // 2 bytes in 16bit format

    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord recorder = null;
    private boolean isRecording;

    public SampleRecorder(ReadCallbackHandler onSampleRead) {

        this.onSampleRead = onSampleRead;
        this.recorder = initAudioRecorder();

    }

    private AudioRecord initAudioRecorder() {

        //int bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
        //        RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);

        return new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);

    }

    public void Start() {

        recorder.startRecording();
        isRecording = true;

        while (isRecording) {
            //Log.d("RECORDER0", "Looping");

            short sSignal[] = new short[BufferElements2Rec];

            recorder.read(sSignal, 0, BufferElements2Rec);

            byte bSignal[] = short2byte(sSignal);

            //Log.d("RECORDER1", Arrays.toString(bSignal));
            this.onSampleRead.onRead(bSignal, sSignal);

            //Sleep
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            //sData[i] = 0;
        }
        return bytes;

    }

    public void stop() {
        this.isRecording = false;
        this.recorder.stop();
        this.recorder.release();
    }

    public void destroy() {
        stop();
        this.recorder = null;
    }


}


