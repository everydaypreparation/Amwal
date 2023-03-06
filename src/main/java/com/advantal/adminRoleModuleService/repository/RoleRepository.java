package com.advantal.adminRoleModuleService.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.advantal.adminRoleModuleService.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRoleNameAndStatus(String roleName, Short one);

	Role findByIdAndStatus(Long id, Short one);

	Role findByRoleNameAndIdAndStatus(String roleName, Long id, Short one);

//	Page<Role> findByRoleNameContainingAndStatus(String roleName, Short one, Pageable pageable);

//	Page<Role> findByStatus(Pageable pageable, Short one);

	Page<Role> findByRoleNameContaining(String searchText, Pageable pageable);

	Page<Role> findAllByStatus(Pageable pageable, Short one);


}
