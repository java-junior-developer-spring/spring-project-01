package com.itmo.springproject01.info.front;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
