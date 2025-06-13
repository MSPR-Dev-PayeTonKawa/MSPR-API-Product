package com.payetonkawa.client.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.payetonkawa.client.entity.Client;
import com.payetonkawa.client.exception.NotAnInsertException;
import com.payetonkawa.client.repository.ClientRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findall() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    public Client insert(Client client) throws NotAnInsertException {
        if (client.getIdClient() != null && clientRepository.existsById(client.getIdClient())) {
            throw new IllegalStateException("Entity already exists. Use update.");
        }
        // TODO actions sur le messages broker pour synchroniser les autres bdd
        return clientRepository.save(client);
    }

    public Client update(Client client) throws NotAnInsertException {
        if (client.getIdClient() == null || !clientRepository.existsById(client.getIdClient())) {
            throw new IllegalStateException("Entity doesn't exist. Use insert.");
        }
        // TODO actions sur le messages broker pour synchroniser les autres bdd
        return clientRepository.save(client);
    }

    public void delete(Integer id){
        // TODO actions sur le messages broker pour synchroniser les autres bdd
        clientRepository.deleteById(id);
    }
}
