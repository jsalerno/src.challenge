package au.com.practica.src.challenge;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2 {
	private static final Logger log = LoggerFactory.getLogger(H2.class);

	@Bean(name = "h2Server", initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
		return Server.createTcpServer(
			"-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}
}
