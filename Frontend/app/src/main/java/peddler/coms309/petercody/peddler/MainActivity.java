package peddler.coms309.petercody.peddler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static java.lang.Math.E;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listItem = findViewById(R.id.list_item);
        listItem.setOnClickListener(this);

        Button signupBtn = findViewById(R.id.sign_up);
        signupBtn.setOnClickListener(this);
    }


    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.list_item:
                startActivity(new Intent(MainActivity.this, ListItemActivity.class));
            case R.id.sign_up:
                Log.d("poop", "onClick: sign up");
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
        }
        return;
    }
}
