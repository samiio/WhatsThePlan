package uk.ac.reading.student.zj018597.whatstheplan.db;

import androidx.room.Entity;

import uk.ac.reading.student.zj018597.whatstheplan.model.AnEntity;

/**
 * Table 'restaurants' with 'id' and 'name' attributes.
 */
@Entity(tableName = "restaurants")
public class RestaurantEntity extends AnEntity {
    public RestaurantEntity(String name) {
        super(name);
    }
}
