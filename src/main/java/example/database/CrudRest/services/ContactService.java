package example.database.CrudRest.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.database.CrudRest.infra.ContactRepository;
import example.database.CrudRest.models.ContactModel;

@RestController
@RequestMapping({ "/contacts" })
public class ContactService {

	private ContactRepository repository;

	ContactService(ContactRepository contactRepository) {
		this.repository = contactRepository;
	}

	// métodos crud aqui.

	// Listando todos os contatos (GET /contacts)
	@SuppressWarnings("rawtypes")
	@GetMapping
	public List findAll() {
		return repository.findAll();
	}

	// Obtendo um contato especídifo pelo ID (GET /contacts/{id})
	@SuppressWarnings("rawtypes")
	@GetMapping(path = { "/{id}" })
	public ResponseEntity findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Criando um novo contato (POST /contacts)

	@PostMapping
	public ContactModel create(@RequestBody ContactModel contact) {
		return repository.save(contact);
	}
	
	// Atualizando um contato (PUT /contacts)
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/{id}")
	public ResponseEntity update(@PathVariable("id") long id,
	                                      @RequestBody ContactModel contact) {
	   return repository.findById(id)
	           .map(record -> {
	               record.setName(contact.getName());
	               record.setEmail(contact.getEmail());
	               record.setPhone(contact.getPhone());
	               ContactModel updated = repository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	// Removendo um contato pelo ID (DELETE /contacts/{id})
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity<?> delete(@PathVariable long id) {
	   return repository.findById(id)
	           .map(record -> {
	               repository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
}
