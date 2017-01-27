package a00953315.comp3717.bcit.ca.nwschoolsafewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Route extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final Intent intent;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        //intent = getIntent();
    }

    public void back(View view) {
        finish();
    }
}
