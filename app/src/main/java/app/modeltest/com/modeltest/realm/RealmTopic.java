package app.modeltest.com.modeltest.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmTopic extends RealmObject{


    @PrimaryKey
    private String key;
    private boolean isNew;
    private String id;
    private RealmList<RealmLevel> listLevels;

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public RealmList<RealmLevel> getListLevels() {
        return listLevels;
    }

    public void setListLevels(RealmList<RealmLevel> listLevels) {
        this.listLevels = listLevels;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
