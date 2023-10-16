package br.com.webank.webank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.webank.webank.dto.contaBancaria.ContaBancariaRequestDTO;
import br.com.webank.webank.dto.contaBancaria.ContaBancariaResponseDTO;
import br.com.webank.webank.model.ContaBancaria;
import br.com.webank.webank.repository.ContaBancariaRepository;

@Service
public class ContaBancariaService {
     
    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ContaBancariaResponseDTO> obterTodos(){
       List<ContaBancaria> contasBancaria = contaBancariaRepository.findAll();

        List<ContaBancariaResponseDTO> contasBancariaResponse = new ArrayList<>();

        for (ContaBancaria contaBancaria : contasBancaria) {
           
            contasBancariaResponse.add(mapper.map(contaBancaria, ContaBancariaResponseDTO.class));
        }

        return  contasBancariaResponse;

    }

    public ContaBancariaResponseDTO obterPorId(long id){
        Optional<ContaBancaria> optContaBancaria = contaBancariaRepository.findById(id);

        if(optContaBancaria.isEmpty()){
            throw new RuntimeException("Nenhum registro encontrado para o ID: " + id);
        }

        return mapper.map(optContaBancaria.get(), ContaBancariaResponseDTO.class);
 }

    // O save serve tanto para adicionar quanto para atualizar.
    // se tiver id, ele atualiza, s enão tiver id ele adiciona.
    public ContaBancariaResponseDTO adicionar(ContaBancariaRequestDTO contaBancariaRequest){

        ContaBancaria contaBancaria = mapper.map(contaBancariaRequest, ContaBancaria.class);
        contaBancaria = contaBancariaRepository.save(contaBancaria);
        // return new EnderecoResponseDTO(enderecoModel);
        return mapper.map(contaBancaria, ContaBancariaResponseDTO.class);
    }

    public ContaBancariaResponseDTO atualizar(long id, ContaBancariaRequestDTO contaBancariaRequest){

        obterPorId(id);

        ContaBancaria contaBancaria = mapper.map(contaBancariaRequest, ContaBancaria.class);
        contaBancaria.setId(id);
        contaBancariaRepository.save(contaBancaria);

        return mapper.map(contaBancaria, ContaBancariaResponseDTO.class);
    }

    public void deletar(Long id){
        obterPorId(id);

        contaBancariaRepository.deleteById(id);
    }

}
