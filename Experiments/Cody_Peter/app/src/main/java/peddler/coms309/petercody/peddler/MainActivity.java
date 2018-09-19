package peddler.coms309.petercody.peddler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listItem = findViewById(R.id.list_item);
        listItem.setOnClickListener(this);
    }


    public void onClick(View v)
    {
        if (v.getId() == R.id.list_item) {
            startActivity(new Intent(MainActivity.this, ListItemActivity.class));
        }
        return;
    }
}
