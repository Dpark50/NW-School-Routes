package dtsquared.nwschoolsafewalk;

import android.app.LoaderManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import dtsquared.nwschoolsafewalk.database.DatabaseHelper;

public class Menu extends AppCompatActivity {
    int school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DatabaseHelper helper;
        //final LoaderManager manager;
        Intent intent;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForReading(this);
        //manager = getLoaderManager();
        //manager.initLoader(0, null, new CategoryLoaderCallbacks());
        init();

        intent = getIntent();
        //school = intent.getIntExtra("destination", -1);
        //Toast.makeText(this, school, Toast.LENGTH_LONG).show();
    }

    /*private void init() {
        final DatabaseHelper helper;
        final long numEntries;

        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForWriting(this);
        numEntries = helper.getNumberOfCategoryNames();

        if (numEntries == 0) {
            helper.createCategory("Statistics");
            helper.createCategory("Buildings");
            helper.createCategory("Land");
            helper.createCategory("Transportation");
            helper.createCategory("Parks/Recreations");
            helper.createCategory("School");
            helper.createCategory("Water");
            helper.createCategory("Community");
            helper.createCategory("Business");
        }

        helper.close();
    }*/

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
