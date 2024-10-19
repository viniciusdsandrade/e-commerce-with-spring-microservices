package com.restful.config.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import static org.springframework.boot.SpringApplication.run;

/**
 * A classe {@code ConfigServerApplication} configura e inicializa o servidor de configuração
 * centralizado usando o Spring Cloud Config Server.
 *
 * <p>A anotação {@link EnableConfigServer} ativa a funcionalidade de servidor de configuração,
 * permitindo que a aplicação sirva arquivos de configuração externos para outros microserviços
 * em uma arquitetura distribuída.
 *
 * <p>Esse servidor centraliza e fornece configurações para microserviços, que podem buscar suas
 * respectivas configurações remotamente, seja a partir de um repositório Git, diretório local
 * ou outro backend de configuração.
 *
 * <p>Isso possibilita o gerenciamento centralizado de configurações, facilitando mudanças sem
 * a necessidade de reiniciar os microserviços individualmente.
 *
 * <p>Fluxo típico:
 * <ul>
 *   <li>O servidor lê as configurações de um backend (ex.: Git).</li>
 *   <li>Microserviços buscam suas configurações a partir do Config Server.</li>
 *   <li>As configurações são entregues em tempo de execução.</li>
 * </ul>
 *
 * @see EnableConfigServer
 * @see SpringBootApplication
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
	public static void main(String[] args) {
		run(ConfigServerApplication.class, args);
	}
}
