package io.microsservice.msavaliadorcredito.application;

import feign.FeignException;
import io.microsservice.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.microsservice.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import io.microsservice.msavaliadorcredito.domain.model.CartaoCliente;
import io.microsservice.msavaliadorcredito.domain.model.DadosCliente;
import io.microsservice.msavaliadorcredito.domain.model.SituacaoCliente;
import io.microsservice.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.microsservice.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;

    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {

            ResponseEntity<DadosCliente> dadosClienteResponseEntity = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByClientes(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponseEntity.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }
}
