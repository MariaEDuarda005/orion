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

-- INSERIR ITENS NO CARRINHO (antes da finalização)
INSERT INTO carrinho (id_cliente, id_produto, nome_produto, preco_unitario, quantidade)
VALUES
(1, 1, 'Creatina Max', 119.90, 1),
(1, 3, 'Garrafa Fit 1L', 59.90, 2);

-- FINALIZANDO O PEDIDO (carrinho convertido em pedido)
INSERT INTO pedido (data_pedido, valor_total, id_cliente, id_cupom)
VALUES ('2025-11-16', 233.70, 1, 1);

-- INSERIR ITENS DO PEDIDO (pedido_item)
INSERT INTO pedido_item (id_pedido, id_produto, nome_produto, preco_unitario, quantidade, subtotal)
VALUES
(1, 1, 'Creatina Max', 119.90, 1, 119.90),
(1, 3, 'Garrafa Fit 1L', 59.90, 2, 119.80);