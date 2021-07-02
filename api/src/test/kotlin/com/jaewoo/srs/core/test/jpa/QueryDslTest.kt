package com.jaewoo.srs.core.test.jpa

import com.jaewoo.srs.SpringTestSupport
import com.jaewoo.srs.app.test.dao.FoodStoreRepository
import com.jaewoo.srs.app.test.dao.FoodTypeRepository
import com.jaewoo.srs.app.test.domain.dto.QSearchFoodStoreResponse
import com.jaewoo.srs.app.test.domain.entity.FoodStore
import com.jaewoo.srs.app.test.domain.entity.FoodType
import com.jaewoo.srs.app.test.domain.entity.QFoodStore.foodStore
import com.jaewoo.srs.app.test.domain.entity.QFoodType.foodType
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.jpa.JPAExpressions
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class QueryDslTest(
    foodTypeRepository: FoodTypeRepository,
    foodStoreRepository: FoodStoreRepository
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

    @Test
    fun `기본쿼리_조건절`() {
        // given & when
        val foodStores =
            query.selectFrom(foodStore)
                .where(
                    foodStore.rate.goe(5)
                        .and(foodStore.storeName.startsWith("삼"))
                )
                .fetch()

        // then
        Assertions.assertThat(foodStores.size).isEqualTo(1)
    }

    @Test
    fun `기본쿼리_정렬`() {
        // given & when
        val foodStores =
            query.selectFrom(foodStore)
                .orderBy(foodStore.rate.desc())
                .fetch()

        // then
        Assertions.assertThat(foodStores.size).isEqualTo(10)
        Assertions.assertThat(foodStores.get(0).rate).isEqualTo(10)
    }

    @Test
    fun `기본쿼리_페이징`() {
        // given
        val pageNo = 1L
        val pageSize = 5L

        // given & when
        val fetchResults =
            query.selectFrom(foodStore)
                .offset(pageNo * pageSize)
                .limit((pageNo + 1) * pageSize)
                .fetchResults()

        // then
        Assertions.assertThat(fetchResults.total).isEqualTo(10)
        Assertions.assertThat(fetchResults.results.size).isLessThanOrEqualTo(pageSize.toInt())
    }

    @Test
    fun `inner join`() {
        // given & when
        val fetch = query
            .selectFrom(foodStore)
            .join(foodStore.foodType, foodType)
            .fetch()

        // then
        fetch.forEach { println(it) }
        Assertions.assertThat(fetch.size).isEqualTo(10)
    }

    @Test
    fun `연관관계 없는 inner join on`() {
        // given & when
        val fetch = query
            .select(foodStore)
            .from(foodStore)
            .join(foodType).on(foodStore.rate.eq(foodType.foodOrder))
            .fetch()

        // then
        fetch.forEach { println(it) }
        Assertions.assertThat(fetch.size).isEqualTo(2)
    }

    @Test
    fun `left join on`() {
        // given & when
        val fetch = query
            .select(foodStore, foodType)
            .from(foodStore)
            .leftJoin(foodType).on(foodStore.rate.eq(foodType.foodOrder))
            .fetch()

        // then
        fetch.forEach { println(it) }
        Assertions.assertThat(fetch.size).isEqualTo(10)
    }

    @Test
    fun `subquery`() {
        // given & when
        val fetch = query
            .select(foodStore)
            .from(foodStore)
            .where(
                foodStore.rate.`in`(
                    JPAExpressions
                        .select(foodType.foodOrder.max())
                        .from(foodType)
                )
            )
            .fetch()

        // then
        fetch.forEach { println(it) }
        Assertions.assertThat(fetch.size).isEqualTo(1)
    }

    @Test
    fun `case when`() {
        // given & when
        val fetch = query
            .select(
                foodStore.rate
                    .`when`(10).then("최고의 맛")
                    .`when`(9).then("좋은 맛")
                    .otherwise("보통")
            )
            .from(foodStore)
            .orderBy(foodStore.rate.desc())
            .fetch()

        // then
        fetch.forEach { println(it) }
        Assertions.assertThat(fetch.get(0)).isEqualTo("최고의 맛")
    }

    @Test
    fun `case builder`() {
        // given & when
        val fetch = query
            .select(
                CaseBuilder()
                    .`when`(foodStore.rate.goe(8)).then("최고의 맛")
                    .`when`(foodStore.rate.goe(4)).then("좋은 맛")
                    .otherwise("보통")
            )
            .from(foodStore)
            .orderBy(foodStore.rate.desc())
            .fetch()

        // then
        fetch.forEach { println(it) }
        Assertions.assertThat(fetch.get(0)).isEqualTo("최고의 맛")
    }

    @Test
    fun `DTO로 결과 받기`() {
        // given & when
        val fetch = query
            .select(
                QSearchFoodStoreResponse(
                    foodStore.storeName,
                    foodStore.rate,
                    foodStore.ownerName,
                    foodType.foodTypeName,
                    foodType.foodOrder
                )
            )
            .from(foodStore)
            .join(foodType).on(foodType.eq(foodStore.foodType))
            .fetch()

        // then
        fetch.forEach { println(it) }
    }
}