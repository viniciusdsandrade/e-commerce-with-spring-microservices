package org.restful.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * A classe {@code DiscoveryApplication} configura e inicializa o Eureka Server
 * utilizando o Spring Cloud Netflix Eureka.
 *
 * <p>A anotação {@link EnableEurekaServer} transforma esta aplicação Spring Boot
 * em um servidor de descoberta de microsserviços, o que significa que outros
 * serviços (Eureka Clients) podem se registrar e descobrir reciprocamente
 * dinamicamente.
 *
 * <p>O Eureka Server atua como um ponto central onde microsserviços se registram
 * para poderem ser descobertos por outros serviços. Isso remove a necessidade
 * de os serviços conhecerem as localizações (IP/porta) uns dos outros
 * previamente, facilitando a escalabilidade e flexibilidade da arquitetura de
 * microsserviços.
 *
 * <p>Fluxo típico:
 * <ul>
 *   <li>Os microsserviços-clientes se registram automaticamente no Eureka Server.</li>
 *   <li>Os microsserviços podem consultar o Eureka Server para descobrir outros serviços disponíveis.</li>
 *   <li>O Eureka Server monitora a saúde dos serviços e gerencia sua lista de instâncias ativas.</li>
 * </ul>
 *
 * @see EnableEurekaServer
 * @see SpringBootApplication
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class, args);
    }
}