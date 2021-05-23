package com.jaewoo.srs.app.user.domain.entity

import com.jaewoo.srs.core.jpa.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

//@Entity
//@Table(name = "role")
class Role : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "role_name")
    var name: String? = null

    @Column(name = "description")
    var description: String? = null
}