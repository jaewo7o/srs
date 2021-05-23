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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    //    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
//    @JoinTable(
//        name = "user_role",
//        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
//        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
//    )
//    var roles: Set<Role>? = null
    var roles = ""
}