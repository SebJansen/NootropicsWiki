package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.R;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.models.Article;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.utils.MySQLiteHelper;

import static nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.R.*;

public class ExcerptActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    ImageView icon;

    Article article;
    MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(layout.activity_details);

        db = new MySQLiteHelper(this);
        long articleId = getIntent().getLongExtra("EVERYTHING", -1);
        article = db.getArticle(articleId);

        title = (TextView) findViewById(id.activity_details_title);
        description = (TextView) findViewById(id.activity_details_description);
        icon = (ImageView) findViewById(id.activity_details_icon);

        title.setText(article.getTitle());
        description.setText(article.getDescription());
        icon.setImageResource(article.getImage());

    }
}
