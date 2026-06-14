package com.ngonzano.springcloud.msvc.users.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleName) {
        super("Rol no encontrado: " + roleName);
    }

}
