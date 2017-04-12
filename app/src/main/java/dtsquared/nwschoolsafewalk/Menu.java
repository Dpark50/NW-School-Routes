package dtsquared.nwschoolsafewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dtsquared.nwschoolsafewalk.database.DatabaseHelper;

public class Menu extends AppCompatActivity {
    private static final String RICHARDMcBRIDE = "Richard McBride Elementary";
    private static final String JIBC = "JIBC";
    private static final String FWHOWAY = "FW Howay Elementary";
    private static final String QUEEN_ELIZABETH = "Queen Elizabeth Elementary";
    private static final String QUEENSBOROUGH = "Queensborough Middle";
    private static final String CONNAUGHT_HEIGHTS = "Connaught Heights Elementary";
    private static final String LORD_TWEEDSMUIR = "Lord Tweedsmuir Elementary";
    private static final String FRASER_RIVER = "Ecole Fraser River Middle";
    private static final String DOUGLAS = "Douglas College";
    private static final String LORD_KELVIN = "Lord Kelvin Elementary";
    private static final String GLENBROOK = "Glenbrook Middle";
    private static final String HUME_PARK = "Hume Park Elementary";
    private static final String NW = "New Westminster Secondary";
    private static final String HERBERT_SPENCER = "Herbert Spencer Elementary";
    private static final String QAYQAYT = "Qayqayt Elementary";
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
    }

    private void init() {
        final DatabaseHelper helper;
        final long numSchoolEntries, numMarkerEntries;

        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForWriting(this);
        numSchoolEntries = helper.getNumberOfSchools();
        numMarkerEntries = helper.getNumberOfMarkers();

        if (numSchoolEntries == 0) {
            helper.createSchool(1, RICHARDMcBRIDE);
            helper.createSchool(2, JIBC);
            helper.createSchool(3, FWHOWAY);
            helper.createSchool(4, QUEEN_ELIZABETH);
            helper.createSchool(5, QUEENSBOROUGH);
            helper.createSchool(6, CONNAUGHT_HEIGHTS);
            helper.createSchool(7, LORD_TWEEDSMUIR);
            helper.createSchool(8, FRASER_RIVER);
            helper.createSchool(9, DOUGLAS);
            helper.createSchool(10, LORD_KELVIN);
            helper.createSchool(11, GLENBROOK);
            helper.createSchool(12, HUME_PARK);
            helper.createSchool(13, NW);
            helper.createSchool(14, HERBERT_SPENCER);
            helper.createSchool(15, QAYQAYT);
        }

        if (numMarkerEntries == 0) {
            //ok
            helper.createMarker(1, RICHARDMcBRIDE, "49.226546", "-122.899537", "70.0", true);
            //ok
            helper.createMarker(2, JIBC, "49.222264", "-122.910140", "115.0", false);
            //ok
            helper.createMarker(3, FWHOWAY, "49.226056", "-122.912369", "100.0", false);
            //ok
            helper.createMarker(4, QUEEN_ELIZABETH, "49.185000", "-122.944000", "90.0", false);
            //ok
            helper.createMarker(5, QUEENSBOROUGH, "49.186194", "-122.940822", "90.0", false);
            //ok
            helper.createMarker(6, CONNAUGHT_HEIGHTS, "49.202610", "-122.955700", "65.0", false);
            //ok
            helper.createMarker(7, LORD_TWEEDSMUIR, "49.205450", "-122.942310", "95.0", false);
            //ok
            helper.createMarker(8, FRASER_RIVER, "49.204190", "-122.916600", "80.0", false);
            //ok
            helper.createMarker(9, DOUGLAS, "49.203568", "-122.912689", "120.0", false);
            helper.createMarker(10, LORD_KELVIN, "49.210900", "-122.930000", "75.0", false);
            helper.createMarker(11, GLENBROOK, "49.220770", " -122.911280", "55.0", false);
            //ok
            helper.createMarker(12, HUME_PARK, "49.232900", "-122.890490", "30.0", false);
            //ok
            helper.createMarker(13, NW, "49.216300", "-122.927800", "185.0", false);
            //ok
            helper.createMarker(14, HERBERT_SPENCER, "49.217382", "-122.913592", "90.0", false);
            //ok
            helper.createMarker(15, QAYQAYT, "49.208260 ", "-122.905000", "80.0", false);
        }

        helper.close();
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
    }
}
