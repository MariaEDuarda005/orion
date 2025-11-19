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
    preco DECIMAL(10,2) NOT NULL,
    categoria ENUM('SUPLEMENTO', 'LIFESTYLE_FITNESS', 'ACESSORIOS_FITNESS') NOT NULL
);

CREATE TABLE cupom (
    id_cupom BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    percentual_desconto INTEGER NOT NULL,
    validade_inicio DATE NOT NULL,
    validade_fim DATE NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE pedido (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_pedido DATE NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_cupom BIGINT,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT pedido_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT pedido_cupom FOREIGN KEY (id_cupom) REFERENCES cupom(id_cupom)
);

CREATE TABLE pedido_item (
    id_item BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade INT NOT NULL,
    CONSTRAINT pedido_item_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
);

CREATE TABLE carrinho (
    id_carrinho BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade INT NOT NULL,
    CONSTRAINT carrinho_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);