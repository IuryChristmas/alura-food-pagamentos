package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.enums.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDTO> findAll(Pageable pagination) {
        return repository
                .findAll(pagination)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO findById(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO createPayment(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO updatePayment(Long id, PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }
}
