package com.udacity.sandwichclub;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import org.json.JSONException;
//region Description of abreviations
/*
p==pic/image	t==array/tensor
k==aka			u==URL
n==name			m==mainName
g==ingredients	v==value
o==placeOfOrigin e==error
d==description	s==sandwich
				x==data
*/
//endregion

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView KTv=null;
    private TextView OTv=null;
    private TextView DTv=null;
    private TextView GTv=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        KTv = (TextView) findViewById(R.id.also_known_tv);
        OTv = (TextView) findViewById(R.id.origin_tv);
        DTv = (TextView) findViewById(R.id.description_tv);
        GTv = (TextView) findViewById(R.id.ingredients_tv);
//        ImageView GIv = findViewById(R.id.ingredients_iv);
        ImageView ingredientsthmIv = findViewById(R.id.image_thm_iv);
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try{ sandwich = JsonUtils.parseSandwichJson(json);
        }catch(JSONException e){
            e.printStackTrace();}
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsthmIv);
        setTitle(sandwich.getMainName());
    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
    private void populateUI(Sandwich S) {
        //K... AKAs / ,'s added
        if (S.getAlsoKnownAs().size() > 0) {
            KTv.setText(TextUtils.join(", ", S.getAlsoKnownAs()));
        }else{ KTv.setText("NA");}
        //O... origin / NA
        OTv.setText(S.getPlaceOfOrigin().isEmpty() ? "NA" : S.getPlaceOfOrigin());
        //D... description
        DTv.setText(S.getDescription());
        //G... ingredients / \n's added
        GTv.setText(TextUtils.join("\n", S.getIngredients()));
    }}
