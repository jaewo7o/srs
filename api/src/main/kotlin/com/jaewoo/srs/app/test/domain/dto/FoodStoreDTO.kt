package com.jaewoo.srs.app.test.domain.dto

import com.querydsl.core.annotations.QueryProjection

data class SearchFoodStoreResponse @QueryProjection constructor(
    val storeName: String,
    val rate: Int,
    val ownerName: String,
    val foodTypeName: String,
    val foodOrder: Int
)