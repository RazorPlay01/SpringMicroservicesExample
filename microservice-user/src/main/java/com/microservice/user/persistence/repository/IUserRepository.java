package com.microservice.user.persistence.repository;

import com.microservice.user.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findAllByCourseId(Long courseId);
}
