package co.com.fibonacci_api.repositories;

import org.springframework.data.repository.CrudRepository;

import co.com.fibonacci_api.models.FibonacciSequenceModel;



public interface FibonacciRepository extends CrudRepository<FibonacciSequenceModel, Long> {

}
