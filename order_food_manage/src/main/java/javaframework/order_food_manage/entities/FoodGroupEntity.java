package javaframework.order_food_manage.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Data
@Table(name = "group_food")
public class FoodGroupEntity extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "foodGroupEntity")
    private Collection<FoodEntity> foodEntities;
}
