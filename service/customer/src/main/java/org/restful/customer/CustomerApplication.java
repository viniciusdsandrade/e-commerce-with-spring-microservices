package org.restful.customer;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

/**
 * A classe {@code CustomerApplication} é a classe principal do microserviço
 * 'customer-service', responsável por gerenciar as informações dos clientes.
 *
 * <p>Este microserviço faz parte de uma arquitetura de microsserviços e tem
 * como função principal executar operações de CRUD (Create, Read, Update,
 * Delete) em dados de clientes, armazenados em um banco de dados MongoDB.
 *
 * <p>O microserviço se conecta a um banco de dados MongoDB e utiliza o banco
 * 'customer' para armazenar as informações dos clientes. Ele roda na porta 8888,
 * conforme configurado no arquivo 'application.yml'.
 *
 * <p>Este microserviço pode ser integrado a outros serviços em uma arquitetura
 * distribuída, fornecendo dados e interagindo com outros componentes, como
 * serviços de pedidos e pagamentos.
 *
 * @see SpringBootApplication
 */
@SpringBootApplication
public class CustomerApplication {

	/**
	 * Metodo principal que inicializa o microserviço 'customer-service'.
	 *
	 * <p>Este metodo executa a aplicação Spring Boot, configurando o serviço
	 * para rodar com todas as dependências e configurações necessárias, como
	 * a conexão com o MongoDB e a porta de execução.
	 *
	 * @param args argumentos de linha de comando (opcionais)
	 */
	public static void main(String[] args) {
		run(CustomerApplication.class, args);
	}
}
