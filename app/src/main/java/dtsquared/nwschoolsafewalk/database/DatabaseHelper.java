package dtsquared.nwschoolsafewalk.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import dtsquared.nwschoolsafewalk.database.schema.DaoMaster;
import dtsquared.nwschoolsafewalk.database.schema.DaoSession;
import dtsquared.nwschoolsafewalk.database.schema.Marker;
import dtsquared.nwschoolsafewalk.database.schema.MarkerDao;
import dtsquared.nwschoolsafewalk.database.schema.School;
import dtsquared.nwschoolsafewalk.database.schema.SchoolDao;


public class DatabaseHelper {
    private final static String TAG = DatabaseHelper.class.getName();
    private static  DatabaseHelper          instance;
    private         SQLiteDatabase          db;
    private         DaoMaster               daoMaster;
    private         DaoSession              daoSession;
    private         SchoolDao               schoolDao;
    private         MarkerDao               markerDao;
    private         DaoMaster.DevOpenHelper helper;

    private DatabaseHelper(final Context context)
    {
        openDatabaseForWriting(context);
    }

    public synchronized static DatabaseHelper getInstance(final Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseHelper(context);
        }

        return (instance);
    }

    public static DatabaseHelper getInstance()
    {
        if(instance == null)
        {
            throw new Error();
        }

        return (instance);
    }

    private void openDatabase()
    {
        daoMaster  = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        schoolDao  = daoSession.getSchoolDao();
        markerDao = daoSession.getMarkerDao();
    }

    public void openDatabaseForWriting(final Context context) {
        helper = new DaoMaster.DevOpenHelper(context,
                "schools.db",
                null);

        db = helper.getWritableDatabase();
        openDatabase();
    }

    public void openDatabaseForReading(final Context context)
    {
        final DaoMaster.DevOpenHelper helper;

        helper = new DaoMaster.DevOpenHelper(context,
                "schools.db",
                null);
        db = helper.getReadableDatabase();
        openDatabase();
    }

    public void close()
    {
        helper.close();
    }

    public School createSchool(final long id, final String ct)
    {
        final School school;

        school = new School(id,
                ct);
        schoolDao.insert(school);

        return (school);
    }

    public School getSchoolFromCursor(final Cursor cursor)
    {
        final School school;

        school = schoolDao.readEntity(cursor,
                0);

        return (school);
    }

    public School getSchoolByObjectId(final long id)
    {
        final List<School> schools;
        final School       school;

        schools = schoolDao.queryBuilder().where(SchoolDao.Properties.Id.eq(id)).limit(1).list();

        if(schools.isEmpty())
        {
            school = null;
        }
        else
        {
            school = schools.get(0);
        }

        return (school);
    }

    public List<School> getSchools() {
        return (schoolDao.loadAll());
    }

    public Cursor getSchoolsCursor() {
        final Cursor cursor;

        String orderBy = SchoolDao.Properties.Id.columnName + " ASC";
        cursor = db.query(schoolDao.getTablename(),
                schoolDao.getAllColumns(),
                null,
                null,
                null,
                null,
                orderBy);
        return (cursor);
    }

    public static void upgrade(final Database db,
                               final int      oldVersion,
                               final int      newVersion)
    {
    }

    public long getNumberOfSchools()
    {
        return (schoolDao.count());
    }

    public Marker createMarker(final long id, final String name, final String latitude,
                                 final String longitude, final boolean geofence)
    {
        final Marker marker;

        marker = new Marker(id,
                name, latitude, longitude, geofence);
        markerDao.insert(marker);

        return (marker);
    }

    public Marker getMarkerFromCursor(final Cursor cursor)
    {
        final Marker marker;

        marker = markerDao.readEntity(cursor,
                0);

        return (marker);
    }

    public Marker getMarkerByObjectId(final long id)
    {
        final List<Marker> markers;
        final Marker       marker;

        markers = markerDao.queryBuilder().where(MarkerDao.Properties.Id.eq(id)).limit(1).list();

        if(markers.isEmpty())
        {
            marker = null;
        }
        else
        {
            marker = markers.get(0);
        }

        return (marker);
    }

    public Marker getMarkerByGeofence(final boolean geofencemarker)
    {
        final List<Marker> markers;
        final Marker       marker;

        markers = markerDao.queryBuilder().where(MarkerDao.Properties.Geofencemarker.eq(geofencemarker)).limit(1).list();

        if(markers.isEmpty())
        {
            marker = null;
        }
        else
        {
            marker = markers.get(0);
        }

        return (marker);
    }

    public List<Marker> getMarkers() {
        return (markerDao.loadAll());
    }

    public Cursor getMarkersCursor() {
        final Cursor cursor;

        String orderBy = MarkerDao.Properties.Name.columnName + " ASC";
        cursor = db.query(markerDao.getTablename(),
                markerDao.getAllColumns(),
                null,
                null,
                null,
                null,
                orderBy);
        return (cursor);
    }

    public int update(final ContentValues values,
                      final String selection,
                      final String[] selectionArgs) {
        int rowsUpdated = 0;

        rowsUpdated = db.update(markerDao.getTablename(), values, selection, selectionArgs);

        return rowsUpdated;
    }

    public long getNumberOfMarkers()
    {
        return (markerDao.count());
    }
}


