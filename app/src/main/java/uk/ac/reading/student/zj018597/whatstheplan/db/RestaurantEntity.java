package uk.ac.reading.student.zj018597.whatstheplan.db;

import androidx.room.Entity;

import uk.ac.reading.student.zj018597.whatstheplan.model.AnEntity;

@Entity(tableName = "restaurants")
public class RestaurantEntity extends AnEntity {
    public RestaurantEntity(String name) {
        super(name);
    }
}
