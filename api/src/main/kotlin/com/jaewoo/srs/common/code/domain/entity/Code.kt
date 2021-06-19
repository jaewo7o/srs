package com.jaewoo.srs.common.code.domain.entity

import com.jaewoo.srs.core.jpa.entity.BaseEntity
import java.io.Serializable
import javax.persistence.*

@IdClass(CodeKey::class)
@Table(name = "code")
@Entity
class Code(
    @Id
    @Column(name = "groupCode", nullable = false)
    var groupCode: String,

    @Id
    @Column(name = "code", nullable = false)
    var code: String,

    @Column(name = "codeNameKo", nullable = false)
    var codeNameKo: String,

    @Column(name = "codeNameEn")
    var codeNameEn: String,

    @Column(name = "sort_rank")
    var sortRank: Int? = 0
) : BaseEntity()

class CodeKey(
    val groupCode: String = "",
    val code: String = ""
) : Serializable