package io.microsservice.mscartoes.application;

import io.microsservice.mscartoes.domain.Cartao;
import io.microsservice.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;
    @Transactional
    public Cartao salvarCartao(Cartao cartao){
        return repository.save(cartao);
    }

    public List<Cartao> getCartaoRendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return repository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
