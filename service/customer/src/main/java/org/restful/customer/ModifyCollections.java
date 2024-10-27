package org.restful.customer;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.slf4j.Logger;

import static com.mongodb.MongoClientSettings.builder;
import static com.mongodb.client.MongoClients.create;
import static io.github.cdimascio.dotenv.Dotenv.configure;
import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;
import static com.mongodb.client.model.ValidationLevel.STRICT;
import static com.mongodb.client.model.ValidationAction.ERROR;

public class ModifyCollections {

    private static final String MONGODB_URI = "MONGODB_URI";
    private static final String DB_NAME = "DB_NAME";
    private static final String ENVOLROLMENT = "mongo.env";

    private static final Logger logger = getLogger(ModifyCollections.class);

    public static void main(String[] args) {
        // Carregar variáveis de ambiente do arquivo mongo.env
        Dotenv dotenv = configure()
                .filename(ENVOLROLMENT) // Especifica o nome do arquivo
                .load();
        String uri = dotenv.get(MONGODB_URI);
        String dbName = dotenv.get(DB_NAME);

        if (uri == null || dbName == null) {
            logger.error("As variáveis de ambiente MONGODB_URI e DB_NAME devem estar definidas no arquivo mongo.env.");
            return;
        }

        try {
            // Configurar o MongoClient sem configurações SSL personalizadas
            MongoClientSettings settings = builder()
                    .applyConnectionString(new ConnectionString(uri))
                    .build();

            try (MongoClient mongoClient = create(settings)) {
                MongoDatabase db = mongoClient.getDatabase(dbName); // Utiliza o dbName do env

                // Executar um ping para verificar a conexão
                db.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

                // Verificar e criar coleções
                criarColecaoComValidador(db, "tb_address", getAddressValidator());
                criarColecaoComValidador(db, "tb_customer", getCustomerValidator());
                criarColecaoComValidador(db, "tb_product", getProductValidator());
                criarColecaoComValidador(db, "tb_order", getOrderValidator());
                criarColecaoComValidador(db, "tb_order_line", getOrderLineValidator());
                criarColecaoComValidador(db, "tb_payment", getPaymentValidator());
                criarColecaoComValidador(db, "tb_notification", getNotificationValidator());

                logger.info("Todas as coleções foram criadas com sucesso!");
            }
        } catch (Exception e) {
            logger.error("Erro ao criar coleções: ", e);
        }
    }

    /**
     * Metodo genérico para criar uma coleção com validação de esquema.
     *
     * @param db          Instância do MongoDatabase
     * @param nomeColecao Nome da coleção a ser criada
     * @param validador   Documento com a validação de esquema
     */
    private static void criarColecaoComValidador(MongoDatabase db, String nomeColecao, Document validador) {
        MongoIterable<String> colecoes = db.listCollectionNames();
        boolean existe = false;
        for (String nome : colecoes) {
            if (nome.equalsIgnoreCase(nomeColecao)) {
                existe = true;
                break;
            }
        }

        if (existe) {
            logger.warn("A coleção '{}' já existe. Pulando criação.", nomeColecao);
        } else {
            try {
                ValidationOptions options = new ValidationOptions()
                        .validator(validador)
                        .validationLevel(STRICT)
                        .validationAction(ERROR);
                db.createCollection(nomeColecao,
                        new CreateCollectionOptions()
                                .validationOptions(options));
                logger.info("Coleção '{}' criada com sucesso!", nomeColecao);
            } catch (Exception e) {
                logger.error("Erro ao criar a coleção '{}': ", nomeColecao, e);
            }
        }
    }

    /**
     * Validador para a coleção tb_address.
     */
    private static Document getAddressValidator() {
        return new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", asList("street", "houseNumber", "zipCode"))
                        .append("properties", new Document()
                                .append("street", new Document("bsonType", "string")
                                        .append("description", "O campo 'street' é obrigatório e deve ser uma string."))
                                .append("houseNumber", new Document("bsonType", "string")
                                        .append("description", "O campo 'houseNumber' é obrigatório e deve ser uma string."))
                                .append("zipCode", new Document("bsonType", "string")
                                        .append("pattern", "^[0-9]{5}-[0-9]{3}$")
                                        .append("description", "O campo 'zipCode' deve estar no formato 12345-678."))
                        )
        );
    }

    /**
     * Validador para a coleção tb_customer.
     */
    private static Document getCustomerValidator() {
        return new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", asList("firstname", "lastname", "email", "address_id"))
                        .append("properties", new Document()
                                .append("firstname", new Document("bsonType", "string")
                                        .append("description", "O campo 'firstname' é obrigatório e deve ser uma string."))
                                .append("lastname", new Document("bsonType", "string")
                                        .append("description", "O campo 'lastname' é obrigatório e deve ser uma string."))
                                .append("email", new Document("bsonType", "string")
                                        .append("pattern", "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                                        .append("description", "O campo 'email' deve ter um formato válido."))
                                .append("address_id", new Document("bsonType", "objectId")
                                        .append("description", "O campo 'address_id' deve referenciar um ObjectId válido."))
                        )
        );
    }

    /**
     * Validador para a coleção tb_product.
     */
    private static Document getProductValidator() {
        return new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", asList("name", "available_quantity", "price"))
                        .append("properties", new Document()
                                .append("name", new Document("bsonType", "string")
                                        .append("description", "O campo 'name' é obrigatório e deve ser uma string."))
                                .append("description", new Document("bsonType", "string")
                                        .append("description", "O campo 'description' é opcional, mas deve ser uma string."))
                                .append("available_quantity", new Document("bsonType", "int")
                                        .append("minimum", 0)
                                        .append("description", "A quantidade deve ser um número inteiro não negativo."))
                                .append("price", new Document("bsonType", "double")
                                        .append("minimum", 0)
                                        .append("description", "O preço deve ser um número positivo."))
                                .append("created_at", new Document("bsonType", "date")
                                        .append("description", "O campo 'created_at' deve ser uma data."))
                                .append("updated_at", new Document("bsonType", "date")
                                        .append("description", "O campo 'updated_at' deve ser uma data."))
                        )
        );
    }

    /**
     * Validador para a coleção tb_order.
     */
    private static Document getOrderValidator() {
        return new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", asList("orderDate", "reference", "customer_id"))
                        .append("properties", new Document()
                                .append("orderDate", new Document("bsonType", "date")
                                        .append("description", "O campo 'orderDate' é obrigatório e deve ser uma data."))
                                .append("reference", new Document("bsonType", "string")
                                        .append("description", "O campo 'reference' é obrigatório e deve ser uma string."))
                                .append("customer_id", new Document("bsonType", "objectId")
                                        .append("description", "O campo 'customer_id' deve referenciar um ObjectId válido."))
                        )
        );
    }

    /**
     * Validador para a coleção tb_order_line.
     */
    private static Document getOrderLineValidator() {
        return new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", asList("quantity", "product_id", "order_id"))
                        .append("properties", new Document()
                                .append("quantity", new Document("bsonType", "int")
                                        .append("minimum", 1)
                                        .append("description", "O campo 'quantity' é obrigatório e deve ser um inteiro positivo."))
                                .append("product_id", new Document("bsonType", "objectId")
                                        .append("description", "O campo 'product_id' deve referenciar um ObjectId válido."))
                                .append("order_id", new Document("bsonType", "objectId")
                                        .append("description", "O campo 'order_id' deve referenciar um ObjectId válido."))
                        )
        );
    }

    /**
     * Validador para a coleção tb_payment.
     */
    private static Document getPaymentValidator() {
        return new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", asList("reference", "amount", "status", "order_id"))
                        .append("properties", new Document()
                                .append("reference", new Document("bsonType", "string")
                                        .append("description", "O campo 'reference' é obrigatório e deve ser uma string."))
                                .append("amount", new Document("bsonType", "double")
                                        .append("minimum", 0)
                                        .append("description", "O campo 'amount' deve ser um número positivo."))
                                .append("status", new Document("bsonType", "string")
                                        .append("enum", asList("pending", "completed", "failed"))
                                        .append("description", "O campo 'status' deve ser um dos valores permitidos."))
                                .append("order_id", new Document("bsonType", "objectId")
                                        .append("description", "O campo 'order_id' deve referenciar um ObjectId válido."))
                        )
        );
    }

    /**
     * Validador para a coleção tb_notification.
     */
    private static Document getNotificationValidator() {
        return new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", asList("sender", "recipient", "content", "date", "payment_id"))
                        .append("properties", new Document()
                                .append("sender", new Document("bsonType", "string")
                                        .append("description", "O campo 'sender' é obrigatório e deve ser uma string."))
                                .append("recipient", new Document("bsonType", "string")
                                        .append("description", "O campo 'recipient' é obrigatório e deve ser uma string."))
                                .append("content", new Document("bsonType", "string")
                                        .append("description", "O campo 'content' é obrigatório e deve ser uma string."))
                                .append("date", new Document("bsonType", "date")
                                        .append("description", "O campo 'date' é obrigatório e deve ser uma data."))
                                .append("payment_id", new Document("bsonType", "objectId")
                                        .append("description", "O campo 'payment_id' deve referenciar um ObjectId válido."))
                        )
        );
    }
}
