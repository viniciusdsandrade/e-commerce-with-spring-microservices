// Definir variáveis para armazenar os ObjectIds
const addressId = ObjectId();
const customerId = ObjectId();
const productId = ObjectId();
const categoryId = ObjectId();
const orderId = ObjectId();
const paymentId = ObjectId();

// Coleção de Endereços (Address)
db.tb_address.insertMany([
    {
        _id: addressId,
        street: "Rua Exemplo",
        houseNumber: "123",
        zipCode: "12345-678"
    }
]);

// Coleção de Clientes (Customer) com referência a Address
db.tb_customer.insertMany([
    {
        _id: customerId,
        firstname: "João",
        lastname: "Silva",
        email: "joao@email.com",
        address_id: addressId  // Referência ao documento na coleção tb_address
    }
]);

// Coleção de Produtos (Product)
db.tb_product.insertMany([
    {
        _id: productId,
        name: "Produto A",
        description: "Descrição do Produto A",
        available_quantity: 50,
        price: 99.99,
        created_at: new Date(),
        updated_at: new Date()
    }
]);

// Coleção de Categorias (Category)
db.tb_category.insertMany([
    {
        _id: categoryId,
        name: "Eletrônicos",
        description: "Produtos eletrônicos em geral"
    }
]);

// Coleção de Pedidos (Order) com referência a Customer
db.tb_order.insertMany([
    {
        _id: orderId,
        orderDate: new Date(),
        reference: "REF12345",
        customer_id: customerId  // Referência ao documento na coleção tb_customer
    }
]);

// Coleção de Linhas de Pedido (OrderLine) com referências a Order e Product
db.tb_order_line.insertMany([
    {
        _id: ObjectId(),
        quantity: 2,
        product_id: productId,  // Referência ao produto
        order_id: orderId       // Referência ao pedido
    }
]);

// Coleção de Pagamentos (Payment) com referência a Order
db.tb_payment.insertMany([
    {
        _id: paymentId,
        reference: "PAY123",
        amount: 199.98,
        status: "completed",
        order_id: orderId  // Referência ao pedido
    }
]);

// Coleção de Notificações (Notification) com referência a Payment
db.tb_notification.insertMany([
    {
        _id: ObjectId(),
        sender: "Sistema",
        recipient: "João Silva",
        content: "Pagamento confirmado",
        date: new Date(),
        payment_id: paymentId  // Referência ao pagamento
    }
]);
