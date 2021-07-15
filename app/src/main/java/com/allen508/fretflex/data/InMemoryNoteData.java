package com.allen508.fretflex.data;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNoteData {

    private List<Note> notes;

    public Note getPitchStandard() {
        return new Note(440, "A", 4, 0, 57, "", "PitchStandard");
    }

    public Note getC0(){
        return new Note(261.6f, "C", 3, -9, 48, "", "MiddleC");
    }

    public List<Note> getStandardTuning(){
        List notes = new ArrayList();

        notes.add(new Note(82.41f, "E", 2, -29, 28, "", ""));
        notes.add(new Note(110.0f, "A", 2, -24, 33, "", ""));
        notes.add(new Note(146.8f, "D", 3, -19, 38, "", ""));
        notes.add(new Note(196.0f, "G", 3, -14, 43, "", ""));
        notes.add(new Note(246.9f, "B", 3, -10, 47, "", ""));
        notes.add(new Note(329.6f, "E", 4, -5,  52, "", ""));

        return notes;
    }

    public List<Note> getAllNotes(){
        return notes;
    }

    public void initData() {
        notes = new ArrayList<>();
        notes.add(new Note(16.35f, "C", 0, -58, 0, "", ""));
        notes.add(new Note(17.32f, "C", 0, -57, 1, "#",""));
        notes.add(new Note(18.35f, "D", 0, -56, 2, "", ""));
        notes.add(new Note(19.45f, "D", 0, -55, 3, "#",""));
        notes.add(new Note(20.60f, "E", 0, -54, 4, "", ""));
        notes.add(new Note(21.83f, "F", 0, -53, 5, "", ""));
        notes.add(new Note(23.12f, "F", 0, -52, 6, "#",""));
        notes.add(new Note(24.50f, "G", 0, -51, 7, "", ""));
        notes.add(new Note(25.96f, "G", 0, -50, 8, "#",""));
        notes.add(new Note(27.50f, "A", 0, -48, 9, "", ""));
        notes.add(new Note(29.14f, "A", 0, -47, 10, "#",""));
        notes.add(new Note(30.87f, "B", 0, -46, 11, "", ""));
        notes.add(new Note(32.70f, "C", 1, -45, 12, "", ""));
        notes.add(new Note(34.65f, "C", 1, -44, 13, "#",""));
        notes.add(new Note(36.71f, "D", 1, -43, 14, "", ""));
        notes.add(new Note(38.89f, "D", 1, -42, 15, "#",""));
        notes.add(new Note(41.20f, "E", 1, -41, 16, "", ""));
        notes.add(new Note(43.65f, "F", 1, -40, 17, "", ""));
        notes.add(new Note(46.25f, "F", 1, -39, 18, "#",""));
        notes.add(new Note(49.00f, "G", 1, -38, 19, "", ""));
        notes.add(new Note(51.91f, "G", 1, -37, 20, "#",""));
        notes.add(new Note(55.00f, "A", 1, -36, 21, "", ""));
        notes.add(new Note(58.27f, "A", 1, -35, 22, "#",""));
        notes.add(new Note(61.74f, "B", 1, -34, 23, "", ""));
        notes.add(new Note(65.41f, "C", 2, -33, 24, "", ""));
        notes.add(new Note(69.30f, "C", 2, -32, 25, "#",""));
        notes.add(new Note(73.42f, "D", 2, -31, 26, "", ""));
        notes.add(new Note(77.78f, "D", 2, -30, 27, "#",""));
        notes.add(new Note(82.41f, "E", 2, -29, 28, "", ""));
        notes.add(new Note(87.31f, "F", 2, -28, 29, "", ""));
        notes.add(new Note(92.50f, "F", 2, -27, 30, "#",""));
        notes.add(new Note(98.00f, "G", 2, -26, 31, "", ""));
        notes.add(new Note(103.8f, "G", 2, -25, 32, "#",""));
        notes.add(new Note(110.0f, "A", 2, -24, 33, "", ""));
        notes.add(new Note(116.5f, "A", 2, -23, 34, "#",""));
        notes.add(new Note(123.5f, "B", 2, -22, 35, "", ""));
        notes.add(new Note(130.8f, "C", 3, -21, 36, "", ""));
        notes.add(new Note(138.6f, "C", 3, -20, 37, "#",""));
        notes.add(new Note(146.8f, "D", 3, -19, 38, "", ""));
        notes.add(new Note(155.6f, "D", 3, -18, 39, "#",""));
        notes.add(new Note(164.8f, "E", 3, -17, 40, "", ""));
        notes.add(new Note(174.6f, "F", 3, -16, 41, "", ""));
        notes.add(new Note(185.0f, "F", 3, -15, 42, "#",""));
        notes.add(new Note(196.0f, "G", 3, -14, 43, "", ""));
        notes.add(new Note(207.7f, "G", 3, -13, 44, "#",""));
        notes.add(new Note(220.0f, "A", 3, -12, 45, "", ""));
        notes.add(new Note(233.1f, "A", 3, -11, 46, "#",""));
        notes.add(new Note(246.9f, "B", 3, -10, 47, "", ""));
        notes.add(new Note(261.6f, "C", 4, -9,  48, "", "C0_PITCH"));
        notes.add(new Note(277.2f, "C", 4, -8,  49, "#",""));
        notes.add(new Note(293.7f, "D", 4, -7,  50, "", ""));
        notes.add(new Note(311.1f, "D", 4, -6,  51, "#",""));
        notes.add(new Note(329.6f, "E", 4, -5,  52, "", ""));
        notes.add(new Note(349.2f, "F", 4, -4,  53, "", ""));
        notes.add(new Note(370.0f, "F", 4, -3,  54, "#",""));
        notes.add(new Note(392.0f, "G", 4, -2,  55, "", ""));
        notes.add(new Note(415.3f, "G", 4, -1,  56, "#",""));
        notes.add(new Note(440.0f, "A", 4, 0,   57, "", "PITCH_STANDARD"));
        notes.add(new Note(466.2f, "A", 4, 1,   58, "#",""));
        notes.add(new Note(493.9f, "B", 4, 2,   59, "", ""));
        notes.add(new Note(523.3f, "C", 5, 3,   60, "", ""));
        notes.add(new Note(554.4f, "C", 5, 4,   61, "#",""));
        notes.add(new Note(587.3f, "D", 5, 5,   62, "", ""));
        notes.add(new Note(622.3f, "D", 5, 6,   63, "#",""));
        notes.add(new Note(659.3f, "E", 5, 7,   64, "", ""));
        notes.add(new Note(698.5f, "F", 5, 8,   65, "", ""));
        notes.add(new Note(740.0f, "F", 5, 9,   66, "#",""));
        notes.add(new Note(784.0f, "G", 5, 10,  67, "", ""));
        notes.add(new Note(830.6f, "G", 5, 11,  68, "#",""));
        notes.add(new Note(880.0f, "A", 5, 12,  69, "", ""));
        notes.add(new Note(932.3f, "A", 5, 13,  70, "#",""));
        notes.add(new Note(987.8f, "B", 5, 14,  71, "", ""));
        notes.add(new Note(1047f,  "C", 6, 15,  72, "", ""));
        notes.add(new Note(1109f,  "C", 6, 16,  73, "#",""));
        notes.add(new Note(1175f,  "D", 6, 17,  74, "", ""));
        notes.add(new Note(1245f,  "D", 6, 18,  75, "#",""));
        notes.add(new Note(1319f,  "E", 6, 19,  76, "", ""));
        notes.add(new Note(1397f,  "F", 6, 20,  77, "", ""));
        notes.add(new Note(1480f,  "F", 6, 21,  78, "#",""));
        notes.add(new Note(1568f,  "G", 6, 22,  79, "", ""));
        notes.add(new Note(1661f,  "G", 6, 23,  80, "#",""));
        notes.add(new Note(1760f,  "A", 6, 24,  81, "", ""));
        notes.add(new Note(1865f,  "A", 6, 25,  82, "#",""));
        notes.add(new Note(1976f,  "B", 6, 26,  83, "", ""));
    }

}
