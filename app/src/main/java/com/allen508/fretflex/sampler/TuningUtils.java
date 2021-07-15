package com.allen508.fretflex.sampler;


import com.allen508.fretflex.data.Note;
import com.allen508.fretflex.data.NoteRepository;

import java.util.List;

public class TuningUtils {

    private NoteRepository repo;

    public TuningUtils (){
        repo = new NoteRepository();
    }

    public boolean isTuned(Difference diff){

        float tuningTolerance = 1.5f;

        boolean isInTune = false;

        if(diff.getHz() < 0){
            isInTune = diff.getHz() > (tuningTolerance * -1);
        }

        if(diff.getHz() > 0){
            isInTune = diff.getHz() < tuningTolerance;
        }

        return isInTune;
    }

    public Difference tuneToStandard(float frequency){
        String tuningName = "Standard";
        List<Note> standardTuning = repo.getStandardTuning();

        Note referenceNote = findClosestNote(standardTuning, frequency);
        return getDifference(repo.getAllNotes(), referenceNote, frequency, tuningName);
    }

    private Difference getDifference(List<Note> lookupNotes, Note referenceNote, float frequency, String tuningName) {

        float diffHz = frequency - referenceNote.getFrequency();
        double diffIntervalRatio = 0;

        Note previousFromReference = lookupNotes.get(referenceNote.getLookupIndex() - 1);
        Note nextFromReference = lookupNotes.get(referenceNote.getLookupIndex() + 1);

        // Frequency is higher than reference frequency
        if(diffHz > 0) {
            diffIntervalRatio = diffHz/(nextFromReference.getFrequency() - referenceNote.getFrequency());
        }

        // Frequency is lower than reference frequency
        if(diffHz < 0) {
            diffIntervalRatio = diffHz/(referenceNote.getFrequency() - previousFromReference.getFrequency());
        }

        return new Difference(diffHz, diffIntervalRatio, referenceNote, frequency, tuningName, previousFromReference, nextFromReference);

    }




    private Note findClosestNote(List<Note> notes, float frequency)
    {
        int n = notes.size();

        // Corner cases
        if (frequency <= notes.get(0).getFrequency())
            return notes.get(0);
        if (frequency >= notes.get(n - 1).getFrequency())
            return notes.get(n - 1);

        // Doing binary search
        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;

            if (notes.get(mid).getFrequency() == frequency)
                return notes.get(mid);

            /* If target is less than array element,
               then search in left */
            if (frequency < notes.get(mid).getFrequency()) {

                // If target is greater than previous
                // to mid, return closest of two
                if (mid > 0 && frequency > notes.get(mid - 1).getFrequency())
                    return getClosest(notes.get(mid - 1), notes.get(mid), frequency);

                /* Repeat for left half */
                j = mid;
            }

            // If target is greater than mid
            else {
                if (mid < n-1 && frequency < notes.get(mid).getFrequency())
                    return getClosest(notes.get(mid), notes.get(mid - 1), frequency);
                i = mid + 1; // update i
            }
        }

        // Only single element left after search
        return notes.get(mid);
    }

    private Note getClosest(Note val1, Note val2, float target) {
        if (target - val1.getFrequency() >= val2.getFrequency() - target)
            return val2;
        else
            return val1;
    }

    public class Difference {
        private float hz;
        private double intervalRatio;
        private Note referenceNote;
        private float sourceFrequency;
        private String tuningName;
        private Note previousNoteFromReference;
        private Note nextNoteFromReference;

        Difference(float hz, double intervalRatio, Note referenceNote, float sourceFrequency, String tuningName, Note previousNoteFromReference, Note nextNoteFromReference){
            this.hz = hz;
            this.intervalRatio = intervalRatio;
            this.referenceNote = referenceNote;
            this.sourceFrequency = sourceFrequency;
            this.tuningName = tuningName;
            this.previousNoteFromReference = previousNoteFromReference;
            this.nextNoteFromReference = nextNoteFromReference;
        }

        public Note getNextNoteFromReference() {
            return nextNoteFromReference;
        }

        public Note getPreviousNoteFromReference() {
            return previousNoteFromReference;
        }

        public String getTuningName() {
            return tuningName;
        }

        public double getIntervalRatio() {
            return intervalRatio;
        }

        public float getHz() {
            return hz;
        }

        public float getSourceFrequency() {
            return sourceFrequency;
        }

        public Note getReferenceNote() {
            return referenceNote;
        }

    }


}

