package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.models.Article;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ArticlesDB";

    private static final String TABLE_ARTICLES = "articles";

    protected static final String KEY_ID = "id";
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_DESCRIPTION = "description";
    protected static final String KEY_IMAGE = "image";

    private static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_IMAGE};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLES_TABLE = "CREATE TABLE articles ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "description TEXT, " +
                "image INTEGER )";
        db.execSQL(CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS articles");
        this.onCreate(db);
    }

    public void addArticle(Article article) {
        Log.d("addArticle", article.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, article.getTitle());
        values.put(KEY_DESCRIPTION, article.getDescription());
        values.put(KEY_IMAGE, article.getImage());

        db.insert(TABLE_ARTICLES,
                null,
                values);

        db.close();
    }

    public Article getArticle(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARTICLES,
                COLUMNS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Article article = new Article();
        article.setId(Integer.parseInt(cursor.getString(0)));
        article.setTitle(cursor.getString(1));
        article.setDescription(cursor.getString(2));
        article.setImage(cursor.getInt(3));

        Log.d("getArticle(" + id + ")", article.toString());

        return article;
    }

    public List<Article> getAllArticles() {
        List<Article> articles = new LinkedList<Article>();

        String query = "SELECT * FROM " + TABLE_ARTICLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Article article = null;

        if (cursor.moveToFirst()) {
            do {
                article = new Article();
                article.setId(Integer.parseInt(cursor.getString(0)));
                article.setTitle(cursor.getString(1));
                article.setDescription(cursor.getString(2));
                article.setImage(cursor.getInt(3));

                articles.add(article);

            } while (cursor.moveToNext());
        }

        Log.d("getAllArticles()", articles.toString());

        return articles;
    }

    //alt
    public Cursor getAllArticlesCursor() {
        List<Article> articles = new LinkedList<Article>();

        String query = "SELECT " + KEY_ID + " AS _id, " +
                KEY_TITLE + ", " +
                KEY_DESCRIPTION + ", " +
                KEY_IMAGE + " " +
                " FROM " + TABLE_ARTICLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Article article = null;

        if (cursor.moveToFirst()) {
            do {
                article = new Article();
                article.setId(Integer.parseInt(cursor.getString(0)));
                article.setTitle(cursor.getString(1));
                article.setDescription(cursor.getString(2));
                article.setImage(cursor.getInt(3));

                articles.add(article);

            } while (cursor.moveToNext());
        }

        Log.d("getAllArticles()", articles.toString());

        return cursor;
    }

    public int updateArticle(Article article) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", article.getTitle());
        values.put("description", article.getDescription());
        values.put("image", article.getImage());

        int i = db.update(TABLE_ARTICLES,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(article.getId())});

        db.close();

        return i;
    }

    public void deleteArticle(long id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ARTICLES,
                KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();

    }

    public void deleteAllArticles() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ARTICLES, null, null);

        db.close();

//        Log.d("deleteArticle", article.toString());
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static String getTableArticles() {
        return TABLE_ARTICLES;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyTitle() {
        return KEY_TITLE;
    }

    public static String getKeyDescription() {
        return KEY_DESCRIPTION;
    }

    public static String getKeyImage() {
        return KEY_IMAGE;
    }

    public static String[] getCOLUMNS() {
        return COLUMNS;
    }
}
