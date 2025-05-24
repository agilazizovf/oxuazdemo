package com.project.oxuaz.repository;

import com.project.oxuaz.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

//    @Query(value = "INSERT INTO user_roles (user_id, role_id) select ?1,id from roles where client=1", nativeQuery = true)
//    @Modifying
//    void assignClientRoles(Long userId);
}
