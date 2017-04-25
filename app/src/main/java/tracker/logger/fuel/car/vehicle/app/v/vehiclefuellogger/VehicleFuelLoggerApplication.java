package tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Main Application!
 * Realm Setting!
 */

public class VehicleFuelLoggerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        RealmConfiguration config = new
//                RealmConfiguration.Builder(getApplicationContext()).build();
//        Realm.setDefaultConfiguration(config);

//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .schemaVersion(0) // Must be bumped when the schema changes
//                .migration(new MyMigration()) // Migration to run instead of throwing an exception
//                .build();

        Realm.init(this);

        final RealmConfiguration configuration = new RealmConfiguration.Builder().name("key_chain_database.realm").schemaVersion(0).build();
        Realm.setDefaultConfiguration(configuration);
        Realm.getInstance(configuration);

//        https://medium.com/@budioktaviyans/android-realm-migration-schema-4fcef6c61e82#.7jyspkp29

//        After Update
//        final RealmConfiguration configuration = new RealmConfiguration.Builder().name("database.realm").schemaVersion(1).migration(new MyMigration()).build();
//        Realm.setDefaultConfiguration(configuration);
//        Realm.getInstance(configuration);

    }



    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }

}
