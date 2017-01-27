package a00953315.comp3717.bcit.ca.nwschoolsafewalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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

    public void logOut(View view){
        finish();
    }
}
