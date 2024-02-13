package com.itmo.springproject01.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public enum RoleType {
        ROLE_USER, ROLE_ADMIN
    }
}
