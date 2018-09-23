package peddler.coms309.petercody.peddler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        Button imageBtn = findViewById(R.id.image_btn);
        imageBtn.setOnClickListener(this);

        imageview = findViewById(R.id.image_view);
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.image_btn) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , 1);
        }
        return;
    }
}
