package com.sinavtime.aks.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sinavtime.aks.model.quiz.QuestionDTO;

import java.util.ArrayList;

public class FavouriteQuestionSQLHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "favourite_question_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "FavouriteQuestions";
    private static final String QUESTION_ID = "_id";
    private static final String QUESTION_UNIQUE_ID = "question_unique_id";
    private static final String QUESTION_TEXT = "question_text";
    private static final String CHOISE_A = "choice_a";
    private static final String CHOISE_B = "choice_b";
    private static final String CHOISE_C = "choice_c";
    private static final String CHOISE_D = "choice_d";
    private static final String CHOISE_E = "choice_e";
    private static final String CORRECT_ANSWER = "correctAnswer";

    public FavouriteQuestionSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME +
                " (" + QUESTION_ID + " INTEGER, " +
                QUESTION_UNIQUE_ID + " VARCHAR(100) , " +
                QUESTION_TEXT + " TEXT NOT NULL, " +
                CHOISE_A + " VARCHAR(200) , " +
                CHOISE_B + " VARCHAR(200) , " +
                CHOISE_C + " VARCHAR(200) , " +
                CHOISE_D + " VARCHAR(200) , " +
                CHOISE_E + " VARCHAR(200) , " +
                CORRECT_ANSWER + " VARCHAR(10) );";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long create(QuestionDTO questionDTO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QUESTION_ID, questionDTO.getQuestionId());
        cv.put(QUESTION_UNIQUE_ID, questionDTO.getQuestionUniqueId());
        cv.put(QUESTION_TEXT, questionDTO.getQuestionText());
        cv.put(CHOISE_A, questionDTO.getChoice_a());
        cv.put(CHOISE_B, questionDTO.getChoice_b());
        cv.put(CHOISE_C, questionDTO.getChoice_c());
        cv.put(CHOISE_D, questionDTO.getChoice_d());
        cv.put(CHOISE_E, questionDTO.getChoice_e());
        cv.put(CORRECT_ANSWER, questionDTO.getCorrectAnswer());
        long id = db.insert(TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public QuestionDTO findById(String questionUniqueId) {
        SQLiteDatabase db = this.getReadableDatabase();
        QuestionDTO questionDTO = new QuestionDTO();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + QUESTION_UNIQUE_ID + "='" + questionUniqueId + "'", null);
        if (c.moveToFirst()) {
            do {
                questionDTO = new QuestionDTO();
                questionDTO.setQuestionUniqueId(c.getString(c.getColumnIndex("question_unique_id")));
            }
            while (c.moveToNext());
        }
        c.close();
        return questionDTO;
    }

    public ArrayList<QuestionDTO> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] sutunlar = new String[]{QUESTION_ID, QUESTION_UNIQUE_ID, QUESTION_TEXT, CHOISE_A, CHOISE_B, CHOISE_C, CHOISE_D, CHOISE_E, CORRECT_ANSWER};
        Cursor c = db.query(TABLE_NAME, sutunlar, null, null, null, null, QUESTION_ID + " asc");
        ArrayList<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        while (c.moveToNext()) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(c.getString(0));
            questionDTO.setQuestionUniqueId(c.getString(1));
            questionDTO.setQuestionText(c.getString(2));
            questionDTO.setChoice_a(c.getString(3));
            questionDTO.setChoice_b(c.getString(4));
            questionDTO.setChoice_c(c.getString(5));
            questionDTO.setChoice_d(c.getString(6));
            questionDTO.setChoice_e(c.getString(7));
            questionDTO.setCorrectAnswer(c.getString(8));

            questionDTOList.add(questionDTO);
        }
        db.close();
        return questionDTOList;
    }


    public void delete(String questionUniqueId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, QUESTION_UNIQUE_ID + "='" + questionUniqueId + "'", null);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
