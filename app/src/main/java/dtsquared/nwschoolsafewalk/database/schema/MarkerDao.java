package dtsquared.nwschoolsafewalk.database.schema;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MARKER".
*/
public class MarkerDao extends AbstractDao<Marker, Long> {

    public static final String TABLENAME = "MARKER";

    /**
     * Properties of entity Marker.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Latitude = new Property(2, String.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(3, String.class, "longitude", false, "LONGITUDE");
        public final static Property Radius = new Property(4, String.class, "radius", false, "RADIUS");
        public final static Property Geofencemarker = new Property(5, Boolean.class, "geofencemarker", false, "GEOFENCEMARKER");
    }


    public MarkerDao(DaoConfig config) {
        super(config);
    }
    
    public MarkerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MARKER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"LATITUDE\" TEXT," + // 2: latitude
                "\"LONGITUDE\" TEXT," + // 3: longitude
                "\"RADIUS\" TEXT," + // 4: radius
                "\"GEOFENCEMARKER\" INTEGER);"); // 5: geofencemarker
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MARKER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Marker entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(3, latitude);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(4, longitude);
        }
 
        String radius = entity.getRadius();
        if (radius != null) {
            stmt.bindString(5, radius);
        }
 
        Boolean geofencemarker = entity.getGeofencemarker();
        if (geofencemarker != null) {
            stmt.bindLong(6, geofencemarker ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Marker entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(3, latitude);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(4, longitude);
        }
 
        String radius = entity.getRadius();
        if (radius != null) {
            stmt.bindString(5, radius);
        }
 
        Boolean geofencemarker = entity.getGeofencemarker();
        if (geofencemarker != null) {
            stmt.bindLong(6, geofencemarker ? 1L: 0L);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Marker readEntity(Cursor cursor, int offset) {
        Marker entity = new Marker( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // latitude
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // longitude
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // radius
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0 // geofencemarker
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Marker entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setLatitude(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLongitude(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRadius(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGeofencemarker(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Marker entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Marker entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Marker entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
