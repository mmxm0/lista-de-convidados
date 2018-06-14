package br.com.comercial.festa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.comercial.festa.model.Convidado;

public interface ConvidadoRepository extends JpaRepository<Convidado, Long> {

	@Query(value= "SELECT * FROM convidado C WHERE id > 2", nativeQuery = true)
	public List<Convidado> findConvidadoCustom();
}
