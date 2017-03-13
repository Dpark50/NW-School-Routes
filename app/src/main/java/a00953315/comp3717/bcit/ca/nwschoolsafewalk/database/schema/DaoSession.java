package a00953315.comp3717.bcit.ca.nwschoolsafewalk.database.schema;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import a00953315.comp3717.bcit.ca.nwschoolsafewalk.database.schema.School;
import a00953315.comp3717.bcit.ca.nwschoolsafewalk.database.schema.Marker;

import a00953315.comp3717.bcit.ca.nwschoolsafewalk.database.schema.SchoolDao;
import a00953315.comp3717.bcit.ca.nwschoolsafewalk.database.schema.MarkerDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig schoolDaoConfig;
    private final DaoConfig markerDaoConfig;

    private final SchoolDao schoolDao;
    private final MarkerDao markerDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        schoolDaoConfig = daoConfigMap.get(SchoolDao.class).clone();
        schoolDaoConfig.initIdentityScope(type);

        markerDaoConfig = daoConfigMap.get(MarkerDao.class).clone();
        markerDaoConfig.initIdentityScope(type);

        schoolDao = new SchoolDao(schoolDaoConfig, this);
        markerDao = new MarkerDao(markerDaoConfig, this);

        registerDao(School.class, schoolDao);
        registerDao(Marker.class, markerDao);
    }
    
    public void clear() {
        schoolDaoConfig.clearIdentityScope();
        markerDaoConfig.clearIdentityScope();
    }

    public SchoolDao getSchoolDao() {
        return schoolDao;
    }

    public MarkerDao getMarkerDao() {
        return markerDao;
    }

}
