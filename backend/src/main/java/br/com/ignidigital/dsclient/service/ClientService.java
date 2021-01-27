package br.com.ignidigital.dsclient.service;

import br.com.ignidigital.dsclient.dto.ClientDTO;
import br.com.ignidigital.dsclient.entities.Client;
import br.com.ignidigital.dsclient.repository.ClientRepository;
import br.com.ignidigital.dsclient.service.exceptions.DatabaseException;
import br.com.ignidigital.dsclient.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        Client client = objClient.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found: "+id));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
    try {

        Client entity = repository.getOne(id);
        copyDtoToEntity(entity, dto);
        return new ClientDTO(repository.save(entity));

        } catch ( EntityNotFoundException e) {
        throw new ResourceNotFoundException("Error! Entity not found: "+id);
     }
    }

    public void delete(Long id) {
    try {
        repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
        throw new ResourceNotFoundException("Error! Resource not found: "+id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Database Integrity violation");
        }
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        copyDtoToEntity(entity,dto);
        return new ClientDTO(repository.save(entity));
    }

    private void copyDtoToEntity(Client entity, ClientDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
