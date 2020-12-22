package uk.ac.reading.student.zj018597.whatstheplan.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;

/**
 * Model for {@link PlanEntity} and {@link RestaurantEntity}
 */
public class AnEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public AnEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
