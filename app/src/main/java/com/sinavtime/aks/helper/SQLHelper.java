package com.sinavtime.aks.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.sinavtime.aks.model.quiz.QestionClobDTO;
import com.sinavtime.aks.model.quiz.QuizDTO;

import java.util.ArrayList;

public class SQLHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "quiz_db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Quiz";

    private static final String QUIZ_ID = "_id";
    private static final String QUIZ_NAME = "quiz_name";
    private static final String QUESTION_CLOB = "qestionClobDTO";
    private static final String COMMENT = "comment";
    private static final String TOTAL_QUESTION = "totalQuestion";
    private static final String TOTAL_SCORE = "totalScore";
    private static final String TOTAL_TRUE_COUNT = "totalTrueCount";
    private static final String TOTAL_FALSE_COUNT = "totalFalseCount";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME +
                " (" + QUIZ_ID + " INTEGER, " +
                QUIZ_NAME + " VARCHAR(100) NOT NULL, " +
                QUESTION_CLOB + " TEXT NOT NULL, " +
                COMMENT + " VARCHAR(100) NOT NULL, " +
                TOTAL_QUESTION + " INTEGER, " +
                TOTAL_SCORE + " INTEGER, " +
                TOTAL_TRUE_COUNT + " INTEGER, " +
                TOTAL_FALSE_COUNT + " INTEGER );";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long create(QuizDTO quizDTO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QUIZ_ID, quizDTO.getQuizId());
        cv.put(QUIZ_NAME, quizDTO.getQuiz_name());
        Gson gson = new Gson();
        cv.put(QUESTION_CLOB, gson.toJson(quizDTO.getQestionClobDTO()));
        cv.put(COMMENT, quizDTO.getComment());
        cv.put(TOTAL_QUESTION, quizDTO.getQestionClobDTO().getQestionDTOList().size());
        cv.put(TOTAL_SCORE, quizDTO.getTotalScore());
        cv.put(TOTAL_TRUE_COUNT, quizDTO.getTotalTrueCount());
        cv.put(TOTAL_FALSE_COUNT, quizDTO.getTotalFalseCount());
        long id = db.insert(TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public ArrayList<QuizDTO> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] sutunlar = new String[]{QUIZ_ID, QUIZ_NAME, QUESTION_CLOB, COMMENT, TOTAL_QUESTION,TOTAL_SCORE,TOTAL_TRUE_COUNT,TOTAL_FALSE_COUNT};
        Cursor c = db.query(TABLE_NAME, sutunlar, null, null, null, null, QUIZ_ID + " asc");
        ArrayList<QuizDTO> quizDTOList = new ArrayList<QuizDTO>();
        while (c.moveToNext()) {
            QuizDTO quizDTO = new QuizDTO();
            quizDTO.setQuizId(c.getLong(0));
            quizDTO.setQuiz_name(c.getString(1));
            Gson gson = new Gson();
            quizDTO.setQestionClobDTO(gson.fromJson(c.getString(2), QestionClobDTO.class));
            quizDTO.setComment(c.getString(3));
            quizDTO.setTotalQuestion(c.getInt(4));
            quizDTO.setTotalScore(c.getInt(5));
            quizDTO.setTotalTrueCount(c.getInt(6));
            quizDTO.setTotalFalseCount(c.getInt(7));
            quizDTOList.add(quizDTO);
        }
        db.close();
        return quizDTOList;
    }


    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, QUIZ_ID + "=" + id, null);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void update(QuizDTO quizDTO){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TOTAL_SCORE,quizDTO.getTotalScore());
        cv.put(TOTAL_TRUE_COUNT,quizDTO.getTotalTrueCount());
        cv.put(TOTAL_FALSE_COUNT,quizDTO.getTotalFalseCount());
        db.update(TABLE_NAME,cv,QUIZ_ID+"="+quizDTO.getQuizId(),null);
        db.close();
    }

    public boolean checkDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
}
