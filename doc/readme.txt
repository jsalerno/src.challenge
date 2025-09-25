OVERVIEW

There are 2 Springboot deployable services - Browse and Manage, each deployed on Java 17, using plain sockets.

The Browse app (port 8090) is started first and may be left running at all times; http://localhost:8090/browse
It instantiates the shared H2 db (port 9090) .
It enters no items into the collections.

The Manage app (port 8091) is started once Browse is running; http://localhost:8091/browse
It connects to the shared H2 db instance.
It inserts sample items into the collections at startup via class SampleData.

Collections are implemented via JpaRepository extensions.

The Browse app can be shut down at any time (let's say we've had a security breach and we want to stop all Authors from committing changes),
whilst the Browse app can remain running (for data consumers like REST clients and browsers if we add a frontend).

The H2 db developer console is accessible via Browse at http://localhost:8090/h2-console (it does not have security applied for the purpose of this mini project).

All /browse endpoints are not authenticated.
All /manage endpoints (available only in the Manage app under http://localhost:8091/manage) - are authenticated.

REQUEST COUNTING

is achieved via @Aspect TracingAspect which has a Before joinpoint on @RestController calls.
Calls are simply logged to the console.

SERVICE PROTECTION

is achieved using OAuth2 in the Manage application.
An in-memory User repo and backing UserDetailsService with BCrypt password encoding 
and JSON Web Key (JWK) support for JWT tokens is applied to
all endpoint except
"/auth/register", "/auth/login"
"/browse/**"
"/h2-console"

A single sytem user is created at startup in Manage for the purposes of authentication.
Tokens are obtained via http://localhost:8091/auth/login

HTTP Request Load

It is expected that the Browse application would be expected to handle large concurrent GET load
and so it's Tomcat threadpool has been enlarged to 200 with an overhead of 10.

ENTITES

Each entity type has basic pojo support.
Actor has a GeneratedValue Long  @Id.
Movie utilizes a mandatory String IMDB Id (the IMDB id is not verified).

Movie's are the master in the many-to-many Movie-to-Actor relationship
and so Join Table and Cascade setting are implemented in class Movie.
and this means that all software joins must be applied via the Movie objects (not the Actor objects).

REST API

Both Browse (http://localhost:8090/browse) and (Manage) http://localhost:8091/browse services have the same flows.
The Manage http://localhost:8091/browse service returns objects which are Hateos-annotated JSON containing Add, Delete and Update links
which service Browse does not provide in the REST response objects.

Pagination is on for collections.

There are ResponseEntity type responses which wrap Spring Hateoas elements, alongside plain Spring Hateoas element response,
simply to illustrate the interoperability of response types.

Entity renderings are controlled by the choice of Actor and Movie Representations, which are themselves determined by the evaluation of EntityActionsCondition subclasses.

SEARCH API

In addition to the paged walk across collections (e.g http://localhost:8091/browse /actors/paged in the case of Actors)
we have support for 
* find by id [/actors/{actorId}] GET
* find by criteria [/actors/matching] POST
* find all dependent entites [/actors/{actorId}/movies] GET

UPDATE / MANAGE API

In the Manage application we have support for Create, Update and Delete, as show for example, for Movies ()
* Add [/movies/add] POST
* Delete [/movies/delete/{movieId}] DELETE
* Update [/movies/patchAttributes] PATCH
PUT is avoided since we want to never clobber the many-to-many reltionships

And, Actor management in Movies is achieved via
* Add Actor To [/movies/{movieId}/addActor/{actorId}]
* Remove Actor From [/movies/{movieId}/removeActor/{actorId}]

EXCEPTION handling

All runtime exceptions are propagated or explicit Runtime exceptions are thrown (no Exception class hierarchies have been designed)
when contraints are broken or conditions are unmet.

(Integration) TEST CASES via HTTP REST calls:

WIP ....