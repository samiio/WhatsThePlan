package uk.ac.reading.student.zj018597.whatstheplan.db;

import androidx.room.Entity;

import uk.ac.reading.student.zj018597.whatstheplan.model.AnEntity;

/**
 * Table 'plans' with 'id' and 'name' attributes.
 */
@Entity(tableName = "plans")
public class PlanEntity extends AnEntity {
    public PlanEntity(String name) {
        super(name);
    }
}
