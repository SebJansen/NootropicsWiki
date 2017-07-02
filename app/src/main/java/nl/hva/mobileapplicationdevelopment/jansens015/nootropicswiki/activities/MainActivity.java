package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.R;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.utils.MySQLiteHelper;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.models.Article;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Article> items;
    private List<Article> articles;
    private SimpleCursorAdapter simpleCursorAdapter;
    private MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NewArticleActivity.class);
                startActivityForResult(myIntent, 1248);
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        db = new MySQLiteHelper(this);
        articles = db.getAllArticles();

        String[] columns = new String[]{db.getKeyTitle(), db.getKeyDescription()};
        int[] to = new int[]{R.id.title, R.id.description};

        db.deleteAllArticles();
        db.addArticle(new Article("What are Nootropics?", "The term nootropics refers to a wide range of artificial and natural compounds which are thought to enhance cognitive function.", R.mipmap.ic_what_are_nootropics));
        db.addArticle(new Article("How do Nootropics work?", "Nootropics are thought to work by modulating neuronal metabolism, cerebral oxygenation, neurotransmitter availability, increasing neurotrophic factors and by affecting other cellular processes. The exact mechanism of action will depend on the compound.", R.mipmap.ic_how_do_nootropics_work));
        db.addArticle(new Article("Considerations", "Our bodies are complex and unique, as such you can't predict with certainty that you won't have an adverse reaction to any particular compound. Many new and exotic compounds are not known to be safe or well-tolerated, their use confers unknown but significant risk.", R.mipmap.ic_considerations));
        db.addArticle(new Article("The lowest hanging fruit", "Sleep is essential for good cognitive function. Yet, many of us don't sleep enough. Trouble getting to sleep on time can often be remedied by simply taking 0.5mg of melatonin, the sleep hormone, thirty minutes before you should go to bed or by reducing the intensity of light you're exposed to at night. Exposure to high intensity light is known to suppress natural melatonin production.", R.mipmap.ic_the_lowest_hanging_fruit));

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.row_item, db.getAllArticlesCursor(), columns, to, 0);

        listView.setAdapter(simpleCursorAdapter);


        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ExcerptActivity.class);
                intent.putExtra("EVERYTHING", simpleCursorAdapter.getItemId(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

////        if (item.getItemId() == R.id.action_settings) {
//
////            Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
//
////            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//
////            startActivity(intent);
//
//            return true;
//        }
//
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check if the result code is the right one
        if (resultCode == Activity.RESULT_OK) {

            // ADD ARTICLE
            if (requestCode == 1248) {

                // Get values passed along activities
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");
                int thumbnail = data.getIntExtra("image-resource", 0);

                // Create a (new) article
                Article item = new Article(title, description, thumbnail);

                Toast.makeText(getApplicationContext(), "Article deleted", Toast.LENGTH_LONG).show();
                db.addArticle(item);
                simpleCursorAdapter.changeCursor(db.getAllArticlesCursor());
            }

            // UPDATE ARTICLE
            if (requestCode == 24816) {

                // Get values passed along activities
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");
                int thumbnail = data.getIntExtra("image-resource", 0);
                Long location = data.getLongExtra("id", 0);

                // Create a (updated) article
                Article item = new Article(location, title, description, thumbnail);

                Toast.makeText(getApplicationContext(), "Article successfully added", Toast.LENGTH_LONG).show();
                db.updateArticle(item);
                simpleCursorAdapter.changeCursor(db.getAllArticlesCursor());
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // Retrieve info about the long pressed list item
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.context_menu_delete_article) {

            Toast.makeText(getApplicationContext(), "Article deleted", Toast.LENGTH_LONG).show();
            db.deleteArticle(simpleCursorAdapter.getItemId(itemInfo.position));
            simpleCursorAdapter.changeCursor(db.getAllArticlesCursor());

            return true;
        }

        if (item.getItemId() == R.id.context_menu_update_article) {

            long articleId = simpleCursorAdapter.getItemId(itemInfo.position);
            Article clickedItem = (Article) db.getArticle(articleId);

            Intent intent = new Intent(MainActivity.this, UpdateArticleActivity.class);
            intent.putExtra("id", clickedItem.getId());
            intent.putExtra("title", clickedItem.getTitle());
            intent.putExtra("description", clickedItem.getDescription());
            intent.putExtra("image-resource", clickedItem.getImage());

//            System.out.println("Clicked item: " + clickedItem.toString());

            startActivityForResult(intent, 24816);

            return true;
        }

        return super.onContextItemSelected(item);
    }

}
