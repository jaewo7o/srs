package com.jaewoo.srs.app.test.dao

import com.jaewoo.srs.app.test.domain.entity.FoodType
import org.springframework.data.jpa.repository.JpaRepository

interface FoodTypeRepository : JpaRepository<FoodType, Int> {

}