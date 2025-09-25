package au.com.practica.src.challenge.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import au.com.practica.src.challenge.bean.Identifiable;
import lombok.NonNull;

public abstract class ReadService<ID, T extends Identifiable<ID>, R extends JpaRepository<T, ID>> {
	@Autowired
	protected R repo;

	public List<T> findByCriteria(@NonNull T item) {
		return repo.findAll(Example.of(item)).stream().collect(Collectors.toList());
	}

	public T findById(@NonNull T item) {
		if (item.getId() == null) {
			throw new RuntimeException("Entity " + item.toString() + " does not have a valid ID");
		}
		return findById(item.getId());
	}

	public T findById(@NonNull ID id) {
		return repo.findById(id).map(a -> a).orElse(null);
	}

	public Page<T> all(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
