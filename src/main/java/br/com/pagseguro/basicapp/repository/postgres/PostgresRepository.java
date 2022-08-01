package br.com.pagseguro.basicapp.repository.postgres;


import br.com.pagseguro.basicapp.model.postgres.PostgresModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresRepository extends JpaRepository<PostgresModel, Long> {
}
