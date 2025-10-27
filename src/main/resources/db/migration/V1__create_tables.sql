CREATE TABLE cliente (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_cliente VARCHAR(100) NOT NULL,
    telefone_cliente VARCHAR(20),
    cpf VARCHAR(14) UNIQUE NOT NULL
);

CREATE TABLE produto (
    id_produto BIGINT AUTO_INCREMENT PRIMARY KEY,
    imagem VARCHAR(255),
    descricao TEXT,
    nome VARCHAR(100) NOT NULL,
    estoque INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE cupom (
    id_cupom BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    percentual_desconto DECIMAL(5,2) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE pedido (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_pedido DATE NOT NULL,
    quantidade INT NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_cupom BIGINT,
    CONSTRAINT pedido_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT pedido_cupom FOREIGN KEY (id_cupom) REFERENCES cupom(id_cupom)
);

CREATE TABLE item_pedido (
    id_item_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT item_pedido_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    CONSTRAINT item_pedido_produto FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);