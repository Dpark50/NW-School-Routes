package dtsquared.nwschoolsafewalk;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import dtsquared.nwschoolsafewalk.database.DatabaseHelper;

public class SchoolContentProvider extends ContentProvider{
    private static final UriMatcher uriMatcher;
    private static final int SCHOOLS_URI = 1;
    private static final int MARKERS_URI = 2;
    public static final Uri SCHOOL_URI;
    public static final Uri MARKER_URI;
    private DatabaseHelper helper;

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("a00953315.comp3717.bcit.ca.dtsquared.nwschoolsafewalk", "schools", SCHOOLS_URI);
        uriMatcher.addURI("a00953315.comp3717.bcit.ca.dtsquared.nwschoolsafewalk", "markers", MARKERS_URI);
    }

    static
    {
        SCHOOL_URI = Uri.parse("content://a00953315.comp3717.bcit.ca.dtsquared.nwschoolsafewalk/schools");
        MARKER_URI = Uri.parse("content://a00953315.comp3717.bcit.ca.dtsquared.nwschoolsafewalk/markers");
    }

    @Override
    public boolean onCreate()
    {
        helper = DatabaseHelper.getInstance(getContext());

        return true;
    }

    @Override
    public Cursor query(final Uri uri,
                        final String[] projection,
                        final String selection,
                        final String[] selectionArgs,
                        final String sortOrder)
    {
        final Cursor cursor;

        switch (uriMatcher.match(uri))
        {
            case SCHOOLS_URI:
            {
                final SQLiteDatabase db;

                helper.openDatabaseForReading(getContext());
                cursor = helper.getSchoolsCursor();
                helper.close();
                break;
            }
            case MARKERS_URI:
            {
                final SQLiteDatabase db;

                helper.openDatabaseForReading(getContext());
                cursor = helper.getMarkersCursor();
                helper.close();
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }

        return (cursor);
    }

    @Override
    public String getType(final Uri uri)
    {
        final String type;

        switch(uriMatcher.match(uri))
        {
            case SCHOOLS_URI:
                type = "vnd.android.cursor.dir/vnd.a00953315.comp3717.bcit.ca.dtsquared.nwschoolsafewalk.schools";
                break;
            case MARKERS_URI:
                type = "vnd.android.cursor.dir/vnd.a00953315.comp3717.bcit.ca.dtsquared.nwschoolsafewalk.markers";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return (type);
    }

    @Override
    public int delete(final Uri uri,
                      final String selection,
                      final String[] selectionArgs)
    {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(final Uri uri,
                      final ContentValues values)
    {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(final Uri uri,
                      final ContentValues values,
                      final String selection,
                      final String[]      selectionArgs)
    {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
