package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MainGenerator {
    public static void main(String[] args)
            throws Exception
    {
        final Schema schema;
        final Entity schoolEntity;
        final Entity markerEntity;
        final DaoGenerator generator;
        // final Property categoryId;

        schema = new Schema(4, "dtsquared.dtsquared.nwschoolsafewalk.database.schema");

        schoolEntity = schema.addEntity("School");
        schoolEntity.addIdProperty();
        schoolEntity.addStringProperty("name").notNull();

        markerEntity = schema.addEntity("Marker");
        markerEntity.addIdProperty();
        markerEntity.addStringProperty("name").notNull();
        markerEntity.addStringProperty("latitude");
        markerEntity.addStringProperty("longitude");
        markerEntity.addStringProperty("geofencemaker");

        //Adds categoryId from categoryEntity as foreign key to dataset table
        // categoryId = datasetEntity.addLongProperty("categoryId").notNull().getProperty();
        //datasetEntity.addToOne(categoryEntity, categoryId);

        // Adds categoryId from datasetEntity as foreign key to category table to allow
        // bi-directional relations.
        //categoryEntity.addToMany(datasetEntity, categoryId);

        generator = new DaoGenerator();
        generator.generateAll(schema, "./app/src/main/java");
    }
}
