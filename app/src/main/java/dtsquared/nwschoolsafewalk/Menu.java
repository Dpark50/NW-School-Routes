package dtsquared.nwschoolsafewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Menu extends AppCompatActivity {
    int school;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        intent = getIntent();
        //school = intent.getIntExtra("destination", -1);
        //Toast.makeText(this, school, Toast.LENGTH_LONG).show();
    }

    public void accountInformation(View view) {
        Intent intent = new Intent(this, AccountInformation.class);
        startActivity(intent);
    }

    public void route(View view) {
        Intent intent = new Intent(this, Route.class);
        startActivity(intent);
    }

    public void map(View view) {
        Intent intent = new Intent(this, Maps.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode,
                                    final Intent intent) {
        final int destination;
        //destination = intent.getIntExtra("destination", -1);
        //Toast.makeText(this, destination, Toast.LENGTH_LONG).show();
    }
}
