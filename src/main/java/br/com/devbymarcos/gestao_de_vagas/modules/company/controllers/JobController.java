package br.com.devbymarcos.gestao_de_vagas.modules.company.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devbymarcos.gestao_de_vagas.modules.company.dto.CreateJobDTO;
import br.com.devbymarcos.gestao_de_vagas.modules.company.entities.JobEntity;
import br.com.devbymarcos.gestao_de_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/job")
public class JobController {
    
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create (@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){
      var companyId = request.getAttribute("company_id");

      //jobEntity.setCompanyId(UUID.fromString(companyId.toString()));
      var jobEntity = JobEntity.builder()
      .benefits(createJobDTO.getBenefits())
      .companyId(UUID.fromString(companyId.toString()))
      .description(createJobDTO.getDescription())
      .level(createJobDTO.getLevel())
      .build();
      
      return  this.createJobUseCase.execute(jobEntity);
    }
}
