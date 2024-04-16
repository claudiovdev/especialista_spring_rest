set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from restaurante_usuario_responsavel;
delete from usuario;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);


insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salmão grelhado ao molho de maracujá', 'Filé de salmão grelhado com molho de maracujá e acompanhamentos', 95.50, 0, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Risoto de camarão', 'Risoto cremoso com camarões frescos e temperos especiais', 65.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sobremesa do chef', 'Surpreenda-se com a sobremesa especial preparada pelo nosso chef', 25.00, 0, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Feijoada completa', 'Tradicional feijoada brasileira com todos os acompanhamentos', 68.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Caipirinha de limão', 'Bebida refrescante com cachaça, limão e açúcar', 15.00, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Pudim de leite', 'Sobremesa cremosa à base de leite, ovos e açúcar', 22.50, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Pizza Margherita', 'Pizza clássica com molho de tomate, muçarela fresca e manjericão', 39.90, 0, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Massa carbonara', 'Espaguete ao molho cremoso de queijo parmesão, pancetta e ovos', 55.00, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Tiramisu', 'Clássica sobremesa italiana à base de café e queijo mascarpone', 18.50, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Hambúrguer artesanal', 'Hambúrguer suculento com queijo cheddar e bacon', 29.90, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Batata frita com cheddar e bacon', 'Porção generosa de batata frita coberta com queijo cheddar e bacon', 18.50, 0, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Milkshake de chocolate', 'Milkshake cremoso de chocolate com chantilly', 12.00, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Paneer Tikka Masala', 'Cubos de paneer grelhados com molho cremoso de tomate e especiarias', 36.50, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Biryani de cordeiro', 'Arroz basmati cozido com carne de cordeiro e temperos indianos', 68.00, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Gulab Jamun', 'Bolinhas de massa frita embebidas em calda de açúcar e cardamomo', 22.00, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Tikka Masala Vegetariano', 'Cubos de legumes grelhados com molho cremoso de tomate e especiarias', 32.00, 0, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Samosa', 'Pastel indiano recheado com batata e ervilhas, frito até dourar', 14.90, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Lassi de manga', 'Bebida indiana refrescante à base de iogurte e manga', 10.50, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Fettuccine Alfredo', 'Massa fettuccine com molho cremoso de queijo parmesão', 42.90, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Lasanha à bolonhesa', 'Camadas de massa intercaladas com molho bolonhesa e queijo gratinado', 49.90, 0, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Torta de maçã', 'Fatias de maçã envoltas em massa crocante e canela, assadas até dourar', 20.00, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sushi misto', 'Seleção de sushis e sashimis frescos e variados', 59.90, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Yakissoba de frango', 'Macarrão chinês frito com tiras de frango e vegetais', 45.00, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Tempurá de legumes', 'Vegetais empanados e fritos acompanhados de molho especial', 24.50, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche de frango grelhado', 'Pão artesanal recheado com peito de frango grelhado e vegetais frescos', 16.50, 0, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada Caesar', 'Mix de folhas verdes com tiras de frango grelhado, croutons e molho Caesar', 18.90, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Batata frita crocante', 'Porção generosa de batatas fritas crocantes e saborosas', 10.00, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sushi vegetariano', 'Seleção de sushis vegetarianos frescos e coloridos', 34.90, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Rolinho primavera', 'Rolinhos crocantes recheados com vegetais frescos e macios', 16.00, 0, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Chá verde gelado', 'Bebida refrescante e saudável à base de chá verde gelado', 8.90, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Picanha na chapa', 'Suculenta picanha grelhada na chapa com temperos especiais', 75.00, 1, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Feijoada completa', 'Feijoada tradicional com todos os acompanhamentos: arroz, couve, farofa e laranja', 55.00, 0, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Creme de papaya com licor de cassis', 'Sobremesa refrescante com creme de mamão papaya e licor de cassis', 28.00, 1, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Coxinha de frango', 'Salgado brasileiro recheado com frango desfiado e temperos', 5.50, 1, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Pastel de queijo', 'Pastel frito com recheio de queijo derretido', 4.00, 1, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Suco de abacaxi com hortelã', 'Suco natural e refrescante de abacaxi com folhas de hortelã', 7.50, 0, 6);



insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 5), (3, 5);


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
        'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', utc_timestamp, 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');