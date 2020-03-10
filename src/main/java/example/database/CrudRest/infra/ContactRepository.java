package example.database.CrudRest.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import example.database.CrudRest.models.ContactModel;

public interface ContactRepository extends JpaRepository<ContactModel, Long> {

}
