package com.mine.chapter12.service;

import org.springframework.stereotype.Service;

import com.mine.chapter12.dao.PermissionDao;
import com.mine.chapter12.entity.Permission;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao;

    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
