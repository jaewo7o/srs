package com.jaewoo.srs.app.test.dao

import com.jaewoo.srs.app.test.domain.entity.FoodStore
import org.springframework.data.jpa.repository.JpaRepository

interface FoodStoreRepository : JpaRepository<FoodStore, Int> {

}