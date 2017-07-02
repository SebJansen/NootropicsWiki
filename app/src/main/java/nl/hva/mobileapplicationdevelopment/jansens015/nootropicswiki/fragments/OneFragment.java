package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments;

import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.R;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities.ExcerptActivity;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities.NewArticleActivity;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities.UpdateArticleActivity;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.models.Article;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.utils.MySQLiteHelper;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.utils.CoverPhotoHelper;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;


public class OneFragment extends Fragment {

    private ListView listView;
    private List<Article> items;
    private List<Article> articles;
    private SimpleCursorAdapter simpleCursorAdapter;
    private MySQLiteHelper db;


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NewArticleActivity.class);
                getActivity().startActivity(myIntent);
            }
        });

        listView = (ListView) view.findViewById(R.id.listView);

        db = new MySQLiteHelper(view.getContext());
        articles = db.getAllArticles();

        String[] columns = new String[]{db.getKeyTitle(), db.getKeyDescription()};
        int[] to = new int[]{R.id.title, R.id.description};
//        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        db.deleteAllArticles();
        db.addArticle(new Article("What are Nootropics?", "The term nootropics refers to a wide range of artificial and natural compounds which are thought to enhance cognitive function.", R.mipmap.ic_what_are_nootropics));
        db.addArticle(new Article("How do Nootropics work?", "Nootropics are thought to work by modulating neuronal metabolism, cerebral oxygenation, neurotransmitter availability, increasing neurotrophic factors and by affecting other cellular processes. The exact mechanism of action will depend on the compound.", R.mipmap.ic_how_do_nootropics_work));
        db.addArticle(new Article("Considerations", "Our bodies are complex and unique, as such you can't predict with certainty that you won't have an adverse reaction to any particular compound. Many new and exotic compounds are not known to be safe or well-tolerated, their use confers unknown but significant risk.", R.mipmap.ic_considerations));
        db.addArticle(new Article("The lowest hanging fruit", "Sleep is essential for good cognitive function. Yet, many of us don't sleep enough. Trouble getting to sleep on time can often be remedied by simply taking 0.5mg of melatonin, the sleep hormone, thirty minutes before you should go to bed or by reducing the intensity of light you're exposed to at night. Exposure to high intensity light is known to suppress natural melatonin production.", R.mipmap.ic_the_lowest_hanging_fruit));

        simpleCursorAdapter = new SimpleCursorAdapter(view.getContext(), R.layout.row_item, db.getAllArticlesCursor(), columns, to, 0);

        listView.setAdapter(simpleCursorAdapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ExcerptActivity.class);
                intent.putExtra("EVERYTHING", simpleCursorAdapter.getItemId(position));
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // Retrieve info about the long pressed list item
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.context_menu_delete_article) {

            Toast.makeText(getContext(), "Article deleted", Toast.LENGTH_LONG).show();
            db.deleteArticle(simpleCursorAdapter.getItemId(itemInfo.position));
            simpleCursorAdapter.changeCursor(db.getAllArticlesCursor());

            return true;
        }

        if (item.getItemId() == R.id.context_menu_update_article) {

            long articleId = simpleCursorAdapter.getItemId(itemInfo.position);
            Article clickedItem = (Article) db.getArticle(articleId);

            Intent intent = new Intent(getContext(), UpdateArticleActivity.class);

//            SharedPreferences mUpdatedArticle = getActivity().getSharedPreferences("UpdatedArticle", 0);
//
////            SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//            SharedPreferences.Editor editor = mUpdatedArticle.edit();
//
//            long id = clickedItem.getId();
//            String title = clickedItem.getTitle();
//            String description = clickedItem.getDescription();
//            int image = clickedItem.getImage();
//
//            editor.putLong("id", id);
//            editor.putString("title", title);
//            editor.putString("description", description);
//            editor.putInt("image", image);
//
//            editor.commit();

            intent.putExtra("id", clickedItem.getId());
            intent.putExtra("title", clickedItem.getTitle());
            intent.putExtra("description", clickedItem.getDescription());
            intent.putExtra("image-resource", clickedItem.getImage());

//            System.out.println("Clicked item: " + clickedItem.toString());

//            startActivityForResult(intent, 24816);
            startActivityForResult(intent, CoverPhotoHelper.getRequestCodeFor("UpdateArticle", getContext()));

            return true;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        Toast.makeText(getContext(), "Fragment attached", Toast.LENGTH_LONG).show();
//    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();

        // UPDATE
        SharedPreferences updatedData = getActivity().getSharedPreferences("UpdatedArticle", 0);

        long id = updatedData.getLong("id", -1);
        String updatedTitle = updatedData.getString("title", "-1");
        String updatedDescription = updatedData.getString("description", "-1");
        int updatedImage = updatedData.getInt("image-resource", -1);

        Article updatedArticle = new Article(id, updatedTitle, updatedDescription, updatedImage);

        db.updateArticle(updatedArticle);
        simpleCursorAdapter.changeCursor(db.getAllArticlesCursor());


        // ADD
        SharedPreferences newData = getActivity().getSharedPreferences("AddedArticle", 0);

        if(newData.contains("newTitle")){

            String newTitle = newData.getString("newTitle", "-1");
            String newDescription = newData.getString("newDescription", "-1");
            int newImage = newData.getInt("newImage-resource", -1);

            Article newArticle = new Article(newTitle, newDescription, newImage);

            db.addArticle(newArticle);
            simpleCursorAdapter.changeCursor(db.getAllArticlesCursor());

            newData.edit()
                    .remove("newTitle")
                    .remove("newDescription")
                    .remove("newImage-resource")
                    .apply();
        }
    }
}
