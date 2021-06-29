package com.jaewoo.srs.app.test.domain.entity

import javax.persistence.*

@Entity
@Table(name = "food_store")
class FoodStore() {
    @Id
    @GeneratedValue
    @Column(name = "food_store_id")
    var id: Int = 0

    @Column(name = "store_name")
    lateinit var storeName: String

    @Column(name = "rate")
    var rate: Int = 0

    @Column(name = "owner_name")
    lateinit var ownerName: String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_type_id")
    lateinit var foodType: FoodType

    constructor(storeName: String, rate: Int, ownerName: String, foodType: FoodType) : this() {
        this.storeName = storeName
        this.rate = rate
        this.ownerName = ownerName
        changeFoodType(foodType)
    }

    private fun changeFoodType(foodType: FoodType) {
        this.foodType = foodType
        foodType.foodStores.add(this)
    }

    override fun toString(): String {
        return "FoodStore(id=$id, storeName='$storeName', rate=$rate, ownerName='$ownerName')"
    }
}