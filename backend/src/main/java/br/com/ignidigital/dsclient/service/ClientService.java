package br.com.ignidigital.dsclient.service;

import br.com.ignidigital.dsclient.dto.ClientDTO;
import br.com.ignidigital.dsclient.entities.Client;
import br.com.ignidigital.dsclient.repository.ClientRepository;
import br.com.ignidigital.dsclient.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@Service
public class ClientService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged (PageRequest pageRequest){
        Page<Client> listClient = repository.findAll(pageRequest);
        return listClient.map(x -> new ClientDTO(x));
    }

    @Transactional (readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> objClient = repository.findById(id);
        Client client = objClient.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
        return new ClientDTO(client);
    }
}
