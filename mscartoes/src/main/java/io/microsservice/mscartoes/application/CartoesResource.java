package io.microsservice.mscartoes.application;

import io.microsservice.mscartoes.domain.Cartao;
import io.microsservice.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.microsservice.mscartoes.application.represantation.CartaoSaveRequest;
import io.microsservice.mscartoes.application.represantation.CartoesPorClienteResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService service;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        var cartao = request.toModel();
        service.salvarCartao(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = service.getCartaoRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }
    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByClientes(@RequestParam("cpf") String cpf){
      List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
      List<CartoesPorClienteResponse> resultList = lista.stream()
              .map(CartoesPorClienteResponse::fromModel)
              .collect(Collectors.toList());
      return ResponseEntity.ok(resultList);
    }
}
