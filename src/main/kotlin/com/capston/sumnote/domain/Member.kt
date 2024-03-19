package com.capston.sumnote.domain

import com.capston.sumnote.util.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MEMBERS")
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null,

    @Column(name = "member_email", nullable = false, unique = true)
    var email: String? = null,

    @Column(name = "member_name")
    var name: String? = null
) : BaseEntity()