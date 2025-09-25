package au.com.practica.src.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import au.com.practica.src.challenge.bean.Identifiable;

public abstract class UpdateService<ID, T extends Identifiable<ID>, R extends JpaRepository<T, ID>> {
	@Autowired
	protected R repo;

	public T create(T actor) {
		return repo.save(actor);
	}

	public T update(T actor) {
		return repo.save(actor);
	}

	public void delete(T actor) {
		repo.delete(actor);
	}
}
