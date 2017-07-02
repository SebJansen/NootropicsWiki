package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.R;

public class UpdateArticleActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Spinner iconSpinner;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_article);

        title = (EditText) findViewById(R.id.update_article_name);
        description = (EditText) findViewById(R.id.update_article_excerpt);
        iconSpinner = (Spinner) findViewById(R.id.update_article_icon);
        updateButton = (Button) findViewById(R.id.edit_item_save);

        String titleString = getIntent().getStringExtra("title");
        String descriptionString = getIntent().getStringExtra("description");

        title.setText(titleString);
        description.setText(descriptionString);

        String[] spinnerItems = {"What are Nootropics?", "How do Nootropics work?", "Considerations", "The lowest hanging fruit", "Generic article"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerItems);

        iconSpinner.setAdapter(spinnerAdapter);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(description.getText())) {

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

                    SharedPreferences mUpdatedArticle = getSharedPreferences("UpdatedArticle", 0);

                    SharedPreferences.Editor editor = mUpdatedArticle.edit();

                    editor.putLong("id", getIntent().getLongExtra("id", 0));
                    editor.putString("title", title.getText().toString());
                    editor.putString("description", description.getText().toString());
                    editor.putInt("image-resource", imageResource);

                    editor.commit();

//                                SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences();


//                    long id = getIntent().getLongExtra("id", 0);
//                    String title = title.getText().toString();
//                    String description = description.getText().toString();
//                    int image = imageResource;
//
//                    editor.putLong("id", id);
//                    editor.putString("title", title);
//                    editor.putString("description", description.getText().toString());
//                    data.putExtra("description", description.getText().toString());
//                    editor.putInt("image", image);


//                    System.out.println("--EXTRAS--");
//                    System.out.println("Title: " + title.getText().toString());
//                    System.out.println("Description: " + description.getText().toString());
//                    System.out.println("Image: " + imageResource);
//                    System.out.println("ID: " + getIntent().getStringExtra("id"));

//                    setResult(Activity.RESULT_OK, data);

                    finish();
                } else {
                    Toast.makeText(UpdateArticleActivity.this, "Please enter some text in the title and description fields", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
