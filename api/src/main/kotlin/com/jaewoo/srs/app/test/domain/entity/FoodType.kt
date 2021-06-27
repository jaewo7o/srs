package com.jaewoo.srs.app.test.domain.entity

import javax.persistence.*

@Entity
@Table(name = "food_type")
class FoodType(
    @Column(name = "food_type_name")
    private val foodTypeName: String,

    @Column(name = "food_order")
    private val foodOrder: Int
) {
    @Id
    @GeneratedValue
    @Column(name = "food_type_id")
    var id: Int = 0

    @OneToMany(mappedBy = "foodType")
    val foodStores: MutableList<FoodStore> = mutableListOf()
}