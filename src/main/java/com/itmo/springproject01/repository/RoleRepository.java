package com.itmo.springproject01.repository;

import com.itmo.springproject01.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
