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

class CodeKey (
    private val groupCode:String,
    private val code:String
) : Serializable {

}