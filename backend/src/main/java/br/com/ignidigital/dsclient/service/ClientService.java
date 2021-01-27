package br.com.ignidigital.dsclient.service;

import br.com.ignidigital.dsclient.dto.ClientDTO;
import br.com.ignidigital.dsclient.entities.Client;
import br.com.ignidigital.dsclient.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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

}
