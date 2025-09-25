package au.com.practica.src.challenge.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import au.com.practica.src.challenge.bean.Actor;

@Repository("actorRepository")
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
