package com.appointment.management.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.management.iListDto.IListRoleDto;
import com.appointment.management.repositories.UserRoleRepository;
import com.appointment.management.serviceIntf.UserRoleServiceInterface;

@Service
public class UserRoleServiceImpl implements UserRoleServiceInterface {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public ArrayList<String> getRoleByUserId(Long roleId) {
		ArrayList<String> roles = new ArrayList();
		ArrayList<IListRoleDto> iListRoles;

		iListRoles = this.userRoleRepository.findRoleByUserId(roleId);

		for (int i = 0; i < iListRoles.size(); i++) {
			roles.add(iListRoles.get(i).getRoleName());
		}

		return roles;
	}

}
