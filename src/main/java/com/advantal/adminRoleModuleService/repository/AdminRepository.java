package com.advantal.adminRoleModuleService.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.advantal.adminRoleModuleService.models.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {


	Admin findByEmailAndStatus(String email, Short one);

	Admin findByMobileAndStatus(String mobile, Short one);	
	
	Admin findByEmailAndPassword(String email,String password);

	Admin findByIdAndStatus(Long id, Short one);

	Admin findByEmail(String email);

	Admin findByMobileAndIdAndStatus(String mobile, Long id, Short one);

	Page<Admin> findAllByStatus(Short one, Pageable pageable);

	Page<Admin> findByNameContainingOrEmailContainingOrMobileContaining(String searchText, String searchText2,
			String searchText3, Pageable pageable);

//	Admin findAllByRole(Long id);
	
}
