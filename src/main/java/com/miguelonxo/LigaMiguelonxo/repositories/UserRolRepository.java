package com.miguelonxo.LigaMiguelonxo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miguelonxo.LigaMiguelonxo.entities.UserRol;

public interface UserRolRepository extends JpaRepository<UserRol, Long>{

    List<UserRol> findByIdUser(Long idUser);

    @Query("SELECT ur.rol FROM UserRol ur WHERE ur.idUser = :userId")
    List<Long> findRoleIdsByIdUser(@Param("userId") Long userId);

    @Query("SELECT r.nombre FROM Rol r WHERE r.id IN :roleIds")
    List<String> findRoleNamesByIds(@Param("roleIds") List<Long> roleIds);

    @Query("SELECT ur FROM UserRol ur WHERE ur.idUser = :idUser")
    List<UserRol> findAllByIdUsuario(@Param("idUser") Long idUser);

    @Query("DELETE FROM UserRol ur WHERE ur.idUser = :idUser")
    void deleteAllByIdUser(Long idUser);
}
