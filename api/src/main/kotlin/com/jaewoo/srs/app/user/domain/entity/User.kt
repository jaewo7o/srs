package com.jaewoo.srs.app.user.domain.entity

import com.jaewoo.srs.common.auth.domain.vo.SessionUser
import com.jaewoo.srs.core.jpa.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "mobile_no", nullable = true)
    var mobileNo: String,

    @Column(name = "language_code", nullable = false)
    var languageCode: String,

    @Column(name = "timezone_name", nullable = false)
    var timezoneName: String,

    @Column(name = "login_id", nullable = false)
    var loginId: String,

    @Column(name = "password", nullable = false)
    var password: String

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: Long = 0L

    fun toSessionUser() = SessionUser(
        id = this.id,
        name = this.name,
        mobileNo = this.mobileNo,
        languageCode = this.languageCode,
        timezoneName = this.timezoneName,
        loginId = this.loginId,
        password = this.password
    )
}