package br.com.devbymarcos.gestao_de_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "company")
@Data
public class CompanyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Pattern(regexp = "^\\S+$", message = "Não pode conter espaços em branco")
    private String username;
    @Email(message = "Email deve ser válido")
    private String email;
    @Length(min = 6, message = "Password deve ter no mínimo 6 caracteres")
    private String password;
    private String website;
    private String name;
    private String descritpion;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
