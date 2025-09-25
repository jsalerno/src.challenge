package au.com.practica.src.challenge;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.practica.src.challenge.bean.ActorAttributes;
import au.com.practica.src.challenge.model.hateoas.HRef;
import au.com.practica.src.challenge.model.hateoas.RestCollectionQueryResult;
import au.com.practica.src.challenge.model.json.IgnoreInheritedIntrospector;
import au.com.practica.src.challenge.repo.ActorRepository;
import au.com.practica.src.challenge.repo.MovieRepository;
import au.com.practica.src.challenge.security.UserService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = ManageApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class ChallengeTest {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Execution x = new Execution();

	private static class Execution {
		/* test class dynamic fields */
		private UserDetails _user;
		private RestCollectionQueryResult _index;

		private String _actorsUri;
		private RestCollectionQueryResult _actors;
		private RestCollectionQueryResult _actor1;
		private RestCollectionQueryResult _actor2;
		private RestCollectionQueryResult _actorMovies;

		private String _moviesUri;
		private RestCollectionQueryResult _movies;
		private RestCollectionQueryResult _movie;

		private String _accessToken;
	}

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setAnnotationIntrospector(new IgnoreInheritedIntrospector());
	}

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	private MockMvc mockMvc;

	@Autowired
	private UserService users;

	@Autowired
	private ActorRepository actorRepo;
	@Autowired
	private MovieRepository movieRepo;

	private RestCollectionQueryResult readResult(String json) {
		try {
			return mapper.readValue(json, RestCollectionQueryResult.class);
		} catch (JsonProcessingException e) {
			log.error("Pasre error {}", e.getMessage());
			return null;
		}
	}

	@BeforeEach
	public void setup() throws Exception {
		if (x._user == null) {
			try {
				x._user = users.loadUserByUsername("admin");
			} catch (UsernameNotFoundException e) {
				x._user = users.registerUser("admin", "password");
			}
		}
		if (mockMvc == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
				.addFilter(springSecurityFilterChain).build();
		}
	}

	@After
	public void tearDown() {
		/* movies must be deleted before actors for referential integrity */
		movieRepo.deleteAll();
		actorRepo.deleteAll();
	}

	@Test
	@Order(1)
	public void browseIndex() throws Exception {
		ResultActions result = mockMvc.perform(get("/browse"));
		result.andExpect(status().isOk());
		x._index = readResult(result.andReturn().getResponse().getContentAsString());
	}

	@Test
	@Order(2)
	public void browseActors() throws Exception {
		x._actorsUri = new URL(x._index.getLinks().getActors().getHref()).getPath();
		ResultActions result = mockMvc.perform(get(x._actorsUri));
		result.andExpect(status().isOk());
		x._actors = readResult(result.andReturn().getResponse().getContentAsString());
	}

	@Test
	@Order(3)
	public void browseMovies() throws Exception {
		x._moviesUri = new URL(x._index.getLinks().getMovies().getHref()).getPath();
		ResultActions result = mockMvc.perform(get(x._moviesUri));
		result.andExpect(status().isOk());
		x._movies = readResult(result.andReturn().getResponse().getContentAsString());
	}

	@Test
	@Order(4)
	public void readActor() throws Exception {
		/* store 2 actors */
		x._actor1 = readResult(readActor(0).andReturn().getResponse().getContentAsString());
		x._actor2 = readResult(readActor(1).andReturn().getResponse().getContentAsString());
	}

	private ResultActions readActor(int index) throws Exception {
		String url = urlFor(x._actors.getEmbedded(), index, "actorList", "self");
		ResultActions result = mockMvc.perform(get(url));
		result.andExpect(status().isOk());
		return result;
	}

	@Test
	@Order(5)
	public void readActorMovies() throws Exception {
		HRef movies = x._actor1.getLinks().getMovies();
		ResultActions result = mockMvc.perform(get(movies.getHref()));
		result.andExpect(status().isOk());
		x._actorMovies = readResult(result.andReturn().getResponse().getContentAsString());

		/* take the first movie and store it */
	}

	@Test
	// @Disabled
	@Order(6)
	public void obtainToken() throws Exception {
		x._accessToken = obtainAccessToken(x._user.getUsername(), x._user.getPassword());
		mockMvc.perform(get("/browse")
			.header("Authorization", "Bearer " + x._accessToken)
			.param("email", "jim@yahoo.com"))
			.andExpect(status().isForbidden());
	}

	@SuppressWarnings("removal")
	@Test
	@Order(7)
	public void patchActor() throws Exception {
		ActorAttributes actor = new ActorAttributes();
		actor.setActorId(new Long((Integer) x._actor1.getUnknown().get("actorId")));
		actor.setFirstName((@NonNull String) x._actor1.getUnknown().get("firstName"));
		actor.setLastName((@NonNull String) x._actor1.getUnknown().get("lastName"));
		/* repair DOB */
		actor.setDob(SampleData.sdf.parse("1945/07/27").getTime());

		String payload = mapper.writeValueAsString(actor);
		String updateUrl = x._actor1.getLinks().getUpdate().getHref();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ResultActions result = mockMvc.perform(patch(updateUrl)
			.with(csrf())
			.content(payload)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk());

		RestCollectionQueryResult updated = readResult(result.andReturn().getResponse().getContentAsString());
	}

	@SuppressWarnings("unchecked")
	private String urlFor(Map<String, List<Map<String, Object>>> embedded, int index, String collectionName, String linkName) {
		List<Map<String, Object>> actorList = embedded.get(collectionName);
		Map<String, Object> actor0 = actorList.get(index);
		Map<String, Object> links = (Map<String, Object>) actor0.get("_links");
		Map<String, Object> self = (Map<String, Object>) links.get(linkName);
		String href = (String) self.get("href");
		return href;
	}

	private String obtainAccessToken(String username, String password) throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		params.add("client_id", "client_id");
		params.add("client_secret", "client_secret");
		params.add("username", username);
		params.add("password", password);

		String payload = mapper.writeValueAsString(params);
		ResultActions result = mockMvc.perform(post("/auth/login")
			.with(csrf())
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.params(params)
			.with(httpBasic("client-id", "client-secret"))
			.accept("application/json;charset=UTF-8"))
			.andExpect(status().isOk());

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}
