package au.com.practica.src.challenge.service;

import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.Actor;
import au.com.practica.src.challenge.repo.ActorRepository;

@Component
public class ActorUpdateService extends UpdateService<Long, Actor, ActorRepository> {
}
