package app.modeltest.com.modeltest;

import android.app.Application;
import android.content.Context;

import app.modeltest.com.modeltest.retrofit.ApiInterface;
import app.modeltest.com.modeltest.retrofit.NetworkInterface;
import app.modeltest.com.modeltest.retrofit.RetrofitApiClient;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;

/**
 * Created by RR on 27-Dec-17.
 */

public class MyApplication extends Application {
    //DatabaseHandler databaseHandler;
    //private SQLiteDatabase mDatabase;
    private static MyApplication mInstance;
    private static Context context;
    ApiInterface apiInterface;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static void setmInstance(MyApplication mInstance) {
        MyApplication.mInstance = mInstance;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //DBManager.initializeInstance();
        mInstance = this;
        context = this;
    }

    public void dbInitialize(){
        //DBManager.initializeInstance();
        // databaseHandler = new DatabaseHandler();
        //mDatabase = databaseHandler.getWritableDatabase();
    }

    public Realm getRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();

        return realm;
    }


//    public NetworkInterface getNetworkCallInterface(){
//        Retrofit retrofit = RetrofitApiClient.getInstance(this);
//        apiInterface = retrofit.create(ApiInterface.class);
//
//        return networkCallInterface;
//    }
}
