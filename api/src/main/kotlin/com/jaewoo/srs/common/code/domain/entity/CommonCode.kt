package com.jaewoo.srs.common.code.domain.entity

import com.jaewoo.srs.core.jpa.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "common_code")
@Entity
class CommonCode(
    @Id
    @Column(name = "groupCode", nullable = false)
    val groupCode: String,

    @Id
    @Column(name = "code", nullable = false)
    val code: String,

    @Column(name = "codeNameKo", nullable = false)
    val codeNameKo: String,

    @Column(name = "codeNameEn")
    val codeNameEn: String
) : BaseEntity() {
    @Column(name = "sort_rank")
    val sortRank: Int? = 0
}