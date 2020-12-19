package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import uk.ac.reading.student.zj018597.whatstheplan.model.Item;

@Entity(tableName = "plans")
public class PlanEntity implements Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public PlanEntity(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

}
