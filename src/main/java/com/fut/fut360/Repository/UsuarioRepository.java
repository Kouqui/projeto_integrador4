//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.Repository;

import com.fut.fut360.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O Spring cria o SQL automaticamente só pelo nome do método!
    Usuario findByEmail(String email);
}