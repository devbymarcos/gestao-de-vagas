package br.com.devbymarcos.gestao_de_vagas.modules.candidate.useCase;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.devbymarcos.gestao_de_vagas.modules.candidate.CandidateRepository;
import br.com.devbymarcos.gestao_de_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.devbymarcos.gestao_de_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(()-> new UsernameNotFoundException("Username/passsword  incorrect"));

        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(),candidate.getPassword());

        if (!passwordMatches) {
                throw new BadCredentialsException("Invalid credentials");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token  =  JWT.create()
        .withIssuer("javagas")
        .withSubject(candidate.getId().toString())
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withClaim("roles",Arrays.asList("candidate"))
        .sign(algorithm);

        var autCandidateResponse = AuthCandidateResponseDTO.builder()
        .access_token(token)
        .build();

        return autCandidateResponse;
    }
}
