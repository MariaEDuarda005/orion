-- INSERIR CLIENTE
INSERT INTO cliente (nome_cliente, telefone_cliente, cpf)
VALUES ('João da Silva', '(11) 98765-4321', '123.456.789-00');

-- INSERIR PRODUTOS
INSERT INTO produto (imagem, descricao, nome, estoque, preco, categoria)
VALUES
('creatina.jpg', 'Creatina monohidratada 300g', 'Creatina Max', 50, 119.90, 'SUPLEMENTO'),
('camisa_fitness.jpg', 'Camiseta dry-fit preta', 'Camisa Fitness Pro', 100, 89.90, 'LIFESTYLE_FITNESS'),
('garrafinha.jpg', 'Garrafa térmica 1L', 'Garrafa Fit 1L', 80, 59.90, 'ACESSORIOS_FITNESS');

-- INSERIR CUPOM
INSERT INTO cupom (codigo, percentual_desconto, validade_inicio, validade_fim, ativo)
VALUES ('FIT10', 10, '2025-10-01', '2025-12-31', TRUE);

-- INSERIR PEDIDO 1 (com cupom)
INSERT INTO pedido (data_pedido, valor_total, id_cliente, id_cupom, status)
VALUES ('2025-10-30', 233.73, 1, 1, 'PENDENTE');

-- INSERIR ITENS DO PEDIDO 1
INSERT INTO carrinho (id_pedido, id_produto, quantidade, preco_unitario)
VALUES
(1, 1, 1, 119.90),   -- 1 Creatina Max
(1, 3, 2, 59.90);    -- 2 Garrafas Fit 1L

-- INSERIR PEDIDO 2 (sem cupom)
INSERT INTO pedido (data_pedido, valor_total, id_cliente, id_cupom, status)
VALUES ('2025-10-29', 179.80, 1, NULL, 'ENVIADO');

-- INSERIR ITENS DO PEDIDO 2
INSERT INTO carrinho (id_pedido, id_produto, quantidade, preco_unitario)
VALUES
(2, 2, 2, 89.90);    -- 2 Camisas Fitness Pro
