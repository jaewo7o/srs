package com.jaewoo.srs.app.user.domain.entity

import com.jaewoo.srs.core.jpa.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "mobile_no", nullable = true)
    var mobileNo: String,

    @Column(name = "login_id", nullable = false)
    var loginId: String,

    @Column(name = "password", nullable = false)
    var password: String

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: Long = 0L
}