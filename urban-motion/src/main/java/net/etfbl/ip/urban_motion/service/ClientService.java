package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.model.Client;
import net.etfbl.ip.urban_motion.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            return clientRepository.save(client);
        }
        return null;
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }
    public boolean blockClient(Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setBlocked(true);
            clientRepository.save(client);
            return true;
        }
        return false;
    }
    public boolean unblockClient(Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setBlocked(false);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

}
