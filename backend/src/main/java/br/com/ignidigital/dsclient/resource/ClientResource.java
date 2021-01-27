package br.com.ignidigital.dsclient.resource;

import br.com.ignidigital.dsclient.dto.ClientDTO;
import br.com.ignidigital.dsclient.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService client;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll (

            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<ClientDTO> clientList = client.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(clientList);

    }

}