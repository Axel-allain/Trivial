package com.example.trivial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.trivial.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Trivial.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDataBaseHelper instance;

    private SQLiteDatabase db;

    private QuizDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    // création databases
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME +  " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NB + " INTEGER, " +
                QuestionsTable.COLUMN_TIPS + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    // mise a jour des databases
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // repmlissage des tables des databases
    private void fillCategoriesTable() {
        Category c1 = new Category("Générale");
        addCategory(c1);
        Category c2 = new Category("Tri");
        addCategory(c2);
        Category c3 = new Category("Consommation");
        addCategory(c3);
        Category c4 = new Category("Pollution");
        addCategory(c4);
    }

    //ajout catégories
    private void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    // remplissage database
    private void fillQuestionsTable() {
        Question q1 = new Question("Il existe dans l'océan Pacifique une énorme nappe de déchets de la taille de ... ?", "17 terrains de foot","3 fois la France","L'Europe",2,"Soit un peu moins de 2 millions de km²",Category.POLLUTION);
        addQuestion(q1);
        Question q2 = new Question("Où finissent la plupart de nos déchets ?", "Dans l'océan","Sous terre","Dans l'espace",1,"Et ils sont extrêmement dévastateurs",Category.POLLUTION);
        addQuestion(q2);
        Question q3 = new Question("Parmis ces déchets, lequel n'est pas recyclable ?", "Un sac plastique","Une boite à chaussures en carton","Un pot de confiture en verre",1,"Le plastique n'est évidemment pas recyclable",Category.TRI);
        addQuestion(q3);
        Question q4 = new Question("Parmis ces appareils électro-ménagers, lequel consomme le plus ?", "Le réfrigérateur","Le four","Le sèche linge",2,"Pensez à débranchez les appareils que vous n'utiliserez pas pendant longtemps",Category.CONSOMMATION);
        addQuestion(q4);
        Question q5 = new Question("Combien faut-il de temps à une canette en acier pour se dégrader ?", "1 à 2 ans","10 à 20 ans","80 à 100 ans",3,"Et c'est bien plus quand il s'agit du verre",Category.GENERALE);
        addQuestion(q5);
        Question q6 = new Question("Depuis quand existe-t-il des lois en France qui protège l'environnement ?", "1945","1975","2000",2,"Oui, seulement depuis 1975",Category.GENERALE);
        addQuestion(q6);
        Question q7 = new Question("Dans quelle poubelle jette-t-on le plastique et carton ?", "Verte","Bleue","Jaune",3,"Vous pouvez trouver des indications sur les poubelles",Category.TRI);
        addQuestion(q7);
        Question q8 = new Question("Quel est le type de transport de marchandises le plus économique ?", "Fluvial","Routier","Ferroviaire",1,"Les porte-conteneurs consomment beaucoup, mais transportent aussi beaucoup",Category.GENERALE);
        addQuestion(q8);
        Question q9 = new Question("Qu'est ce que la symbiose ?", "Une algue","Une maladie due a un champignon","Une association à bénéfice réciproque",3,"Certains animaux dépendent très fortement de ces symbioses",Category.GENERALE);
        addQuestion(q9);
        Question q10 = new Question("Dans lequels de ces objets trouve-t-on des métaux lourds ?", "Une brique de jus de fruits","Les boîtes de conserves","Les piles",3,"Dans ce cas, il s'agit du manganèse et du zinc contenu dans la pile",Category.GENERALE);
        addQuestion(q10);
        Question q11 = new Question("Quelle est la consommation moyenne d'eau par personne et par jour en France ?", "80 L","160 L","240 L",2,"Sachant que bains et douches prennent 40%",Category.CONSOMMATION);
        addQuestion(q11);
        Question q12 = new Question("Combien de litres d'eau gaspille un robinet qui goutte toutes les 4 secondes ?", "160 L/an","1 600 L/an","16 000 L/an",2,"Soit à peu près 500€ en un an",Category.CONSOMMATION);
        addQuestion(q12);
        Question q13 = new Question("Diminuer son chauffage de 1 degré permet de réaliser une economie de... ?", "3%","5%","7%",3,"C'est pas négligeable",Category.GENERALE);
        addQuestion(q13);
        Question q14 = new Question("En 80 ans, la population française a multiplié sa production de déchets par... ?", "3","6","9",2,"On est certes plus nombreux, mais pas 6 fois plus",Category.GENERALE);
        addQuestion(q14);
        Question q15 = new Question("Quel polluant cause le décès de plus de 400 000 personnes par an en Europe ?", "l'ozone","Le dioxyde d'azote","Les particules fines",3,"Pour éviter ce problème, pensez à aérer votre habitation tôt le matin ou tard le soir",Category.POLLUTION);
        addQuestion(q15);
        Question q16 = new Question("Dans quelle poubelle jette-t-on le verre ?", "Verte","Bleue","Jaune",1,"Vous pouvez trouver des indications sur les poubelles",Category.TRI);
        addQuestion(q16);
        Question q17 = new Question("Dans quelle poubelle jette-t-on les papiers, journaux, magazines ?", "Verte","Bleue","Jaune",2,"Vous pouvez trouver des indications sur les poubelles",Category.TRI);
        addQuestion(q17);
    }


    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NB, question.getAnswerNb());
        cv.put(QuestionsTable.COLUMN_TIPS, question.getTips());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
        if (question.getCategoryID()!=Category.GENERALE)
        {   cv.remove(QuestionsTable.COLUMN_CATEGORY_ID);
            cv.put(QuestionsTable.COLUMN_CATEGORY_ID, Category.GENERALE);
            db.insert(QuestionsTable.TABLE_NAME, null, cv);
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    public ArrayList<Question> getQuestions(int categoryID) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID)};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNb(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NB)));
                question.setTips(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_TIPS)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}



