package dtsquared.nwschoolsafewalk;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import dtsquared.nwschoolsafewalk.database.DatabaseHelper;
import dtsquared.nwschoolsafewalk.database.schema.Marker;
import dtsquared.nwschoolsafewalk.database.schema.MarkerDao;
import dtsquared.nwschoolsafewalk.database.schema.SchoolDao;

public class Route extends AppCompatActivity {
    private Spinner spinner;
    private SimpleCursorAdapter adapter;
    private Long selectedItem;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DatabaseHelper helper;
        final LoaderManager manager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        spinner = (Spinner) findViewById(R.id.school_input);
        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForReading(this);
        manager = getLoaderManager();
        manager.initLoader(0, null, new SchoolLoaderCallbacks());

        adapter = new SimpleCursorAdapter(getBaseContext(), android.R.layout.simple_spinner_item,
                null,//helper.getSchoolsCursor(),
                new String[] {
                        SchoolDao.Properties.Name.columnName
                },
                new int[] {
                        android.R.id.text1
                },
                0
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedItem = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        helper.close();
    }

    private class SchoolLoaderCallbacks
            implements LoaderManager.LoaderCallbacks<Cursor>
    {
        @Override
        public Loader<Cursor> onCreateLoader(final int    id,
                                             final Bundle args)
        {
            final Uri uri;
            final CursorLoader loader;

            uri    = SchoolContentProvider.SCHOOL_URI;
            loader = new CursorLoader(Route.this, uri, null, null, null, null);

            return (loader);
        }

        @Override
        public void onLoadFinished(final Loader<Cursor> loader,
                                   final Cursor         data)
        {
            adapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(final Loader<Cursor> loader)
        {
            adapter.swapCursor(null);
        }
    }

    public void back(View view) {
        final DatabaseHelper helper;
        final Uri uri;
        ContentValues setValues, value;
        int rowsUpdated = 0;

        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForReading(this);
        marker = helper.getMarkerByObjectId(selectedItem);

        // Set all values values to false
        uri = SchoolContentProvider.MARKER_URI;
        setValues = new ContentValues();
        setValues.put(MarkerDao.Properties.Geofencemarker.columnName, false);
        rowsUpdated = getContentResolver().update(uri, setValues, null, null);

        // Set selected school to true
        value = new ContentValues();
        value.put(MarkerDao.Properties.Geofencemarker.columnName, true);
        rowsUpdated = getContentResolver().update(uri, value, MarkerDao.Properties.Id.columnName +
                " = '" + marker.getId() + "'", null);

        helper.close();
        finish();
    }
}
