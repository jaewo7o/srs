package com.jaewoo.srs.core.test.jpa

import com.jaewoo.srs.SpringTestSupport
import com.jaewoo.srs.app.test.dao.FoodStoreRepository
import com.jaewoo.srs.app.test.dao.FoodTypeRepository
import com.jaewoo.srs.app.test.domain.entity.FoodStore
import com.jaewoo.srs.app.test.domain.entity.FoodType
import com.jaewoo.srs.app.test.domain.entity.QFoodStore.foodStore
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class QueryDslTest(
    val foodTypeRepository: FoodTypeRepository,
    val foodStoreRepository: FoodStoreRepository
) : SpringTestSupport() {
    init {
        val korean = FoodType("한식", 1)
        val western = FoodType("양식", 2)
        val chinese = FoodType("중식", 3)

        foodTypeRepository.saveAll(listOf(korean, western, chinese))

        val foodStore1 = FoodStore("삼겹살", 9, "messi", korean)
        val foodStore2 = FoodStore("닭갈비", 2, "messi", korean)
        val foodStore3 = FoodStore("부대찌개", 3, "lake", korean)
        val foodStore4 = FoodStore("순대국밥", 4, "lake", korean)
        val foodStore5 = FoodStore("소고기", 5, "messi", korean)
        val foodStore6 = FoodStore("스파게티", 6, "messi", western)
        val foodStore7 = FoodStore("피자", 7, "messi", western)
        val foodStore8 = FoodStore("중국집1", 8, "hong", chinese)
        val foodStore9 = FoodStore("중국집2", 9, "hong", chinese)
        val foodStore10 = FoodStore("중국집3", 10, "hong", chinese)

        foodStoreRepository.saveAll(
            listOf(
                foodStore1, foodStore2, foodStore3, foodStore4, foodStore5,
                foodStore6, foodStore7, foodStore8, foodStore9, foodStore10
            )
        )
    }

    @Test
    fun `기본쿼리`() {
        // given & when
        val foodStores = query.selectFrom(foodStore)
            .fetch()

        // then
        Assertions.assertThat(foodStores.size).isEqualTo(10)
    }

}