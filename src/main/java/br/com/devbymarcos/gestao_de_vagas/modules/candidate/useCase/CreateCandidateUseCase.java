package br.com.devbymarcos.gestao_de_vagas.modules.candidate.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.devbymarcos.gestao_de_vagas.exceptions.UserFoundException;
import br.com.devbymarcos.gestao_de_vagas.modules.candidate.CandidateEntity;
import br.com.devbymarcos.gestao_de_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public  CandidateEntity execute( CandidateEntity candidateEntity) {
        this.candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent(candidate -> {
                throw new UserFoundException();
            });

            var password  = passwordEncoder.encode(candidateEntity.getPassword());
            candidateEntity.setPassword(password);
        return  this.candidateRepository.save(candidateEntity);
    }
}
