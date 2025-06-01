package br.com.devbymarcos.gestao_de_vagas.modules.company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devbymarcos.gestao_de_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity,UUID> {
    
}
