package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLOutput;

import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.R;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.OneFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.utils.MySQLiteHelper;

public class NewArticleActivity extends AppCompatActivity {

    private Button addButton;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner iconSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        addButton = (Button) findViewById(R.id.new_item_add);
        titleEditText = (EditText) findViewById(R.id.new_item_title);
        descriptionEditText = (EditText) findViewById(R.id.new_item_description);
        iconSpinner = (Spinner) findViewById(R.id.new_item_icon);

        String[] spinnerItems = {"What are Nootropics?", "How do Nootropics work?", "Considerations", "The lowest hanging fruit", "Generic article"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerItems);

        iconSpinner.setAdapter(spinnerAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(titleEditText.getText()) && !TextUtils.isEmpty(descriptionEditText.getText())) {

                    int imageResource = 0;
                    switch (iconSpinner.getSelectedItemPosition()) {
                        case 0:
                            imageResource = R.mipmap.ic_what_are_nootropics;
                            break;
                        case 1:
                            imageResource = R.mipmap.ic_how_do_nootropics_work;
                            break;
                        case 2:
                            imageResource = R.mipmap.ic_the_lowest_hanging_fruit;
                            break;
                        case 3:
                            imageResource = R.mipmap.ic_considerations;
                            break;
                        case 4:
                            imageResource = R.mipmap.ic_article;
                            break;
                        default:
                            imageResource = R.mipmap.ic_article;
                            break;
                    }

//                    Intent data = new Intent();
//                    data.putExtra("title", titleEditText.getText().toString());
//                    data.putExtra("description", descriptionEditText.getText().toString());
//                    data.putExtra("image-resource", imageResource);
//
//                    setResult(Activity.RESULT_OK, data);
//

//                    OneFragment frg = (OneFragment) getFragmentManager().findFragmentByTag("General");
//                    frg.getArguments().putBundle("key", data);

//                    Fragment fragment = getFragmentManager().findFragmentByTag("General");
//                    fragment.getArguments().putBundle("title");


                    SharedPreferences newData = getSharedPreferences("AddedArticle", 0);

                    SharedPreferences.Editor editor = newData.edit();

                    editor.putString("newTitle", titleEditText.getText().toString());
                    editor.putString("newDescription", descriptionEditText.getText().toString());
                    editor.putInt("newImage-resource", imageResource);

                    editor.commit();

//                    fragment.setArguments(bundle);
                    finish();


//                    Bundle bundle = new Bundle();
//
////                    Bundle data = new Bundle();
//
//                    FragmentManager fragmentManager = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentOneLayout, mFeedFragment);
////                    fragmentTransaction.addToBackStack(null);
////                    fragmentTransaction.commit();
//
//                    //Add data to this bundle and pass it in getInstance() of OneFragment
////                    fragmentTransaction.replace(R.id.fragmentOneLayout, OneFragment.getInstance(data));
//
//                    bundle.putString("title", titleEditText.getText().toString());
//                    bundle.putString("description", descriptionEditText.getText().toString());
//                    bundle.putInt("image-resource", imageResource);
//
//                    OneFragment fragment = new OneFragment();
//
//                    fragment.setArguments(bundle);
//
//                    finish();

                } else {
                    Toast.makeText(NewArticleActivity.this, "Please enter some text in the title and description fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
