package io.microsservice.mscartoes.application;

import io.microsservice.mscartoes.domain.ClienteCartao;
import io.microsservice.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf (String cpf){
        return repository.findByCpf(cpf);
    }

}
