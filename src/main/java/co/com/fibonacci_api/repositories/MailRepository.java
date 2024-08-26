package co.com.fibonacci_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import co.com.fibonacci_api.models.MailAddressModel;


public interface MailRepository extends CrudRepository<MailAddressModel, Long> {

	List<MailAddressModel> findAllByDisabled(Boolean disabled);
	
	@Query("SELECT m.email FROM MailAddressModel m WHERE disabled = false")
	List<String> findEnabledEmailAddresses();
}
