  -- ----------------------------------------------------------------------------------------
create table ajuda(
codigo int not null auto_increment primary key,
titulo varchar(200),
descricao varchar(500)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- ----------------------------------------------------------------------------------------
create table clientes (
codigo int not null primary key,
nome_completo varchar(100),
sexo varchar(100),
cpf varchar(200),
rg varchar(200),
orgao_emissor varchar(100),
data_de_emissao varchar(200),
nacionalidade varchar(100),
rua varchar(100),
numero varchar(200),
complemento varchar(200),
bairro varchar(100),
cidade varchar(200),
estado varchar(200),
cep varchar(100),
telefone_01 varchar(100),
telefone_02 varchar(200),
email varchar(200),
data_de_nascimento varchar(100),
estado_civil varchar(200),
conjugue varchar(100),
pai varchar(100),
mae varchar(200),
trabalho varchar(200),
cargo varchar(100),
tempo_servico varchar(200),
sobre_trabalho varchar(200),
endereco_trabalho varchar(100),
numero_trabalho varchar(200),
complemento_trabalho varchar(200),
bairro_trabalho varchar(100),
cidade_trabalho varchar(200),
estado_trabalho varchar(100),
cep_trabalho varchar(200),
telefone_trabalho varchar(200),
fax_trabalho varchar(100),
email_trabalho varchar(200),
observacoes_adicionais varchar(200),
data_de_cadastro varchar(200)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- ---------------------------------------------------------------------------------------
create table pagar_contas(
codigo int not null auto_increment primary key,
tipo varchar(100),
numero_documento varchar(100),
data_entrada varchar(200),
data_vencimento varchar(200),
valor varchar(100),
origem varchar(200)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- --------------------------------------------------------------------------------------
create table fornecedor (
codigo int not null auto_increment primary key,
empresa varchar(100),
cep varchar(100),
rua varchar(200),
bairro varchar(200),
cidade varchar(100),
estado varchar(200),
telefone varchar(100),
fax varchar(100),
email varchar(200),
site varchar(200),
observacoes varchar(100),
data_de_cadastro varchar(200),
urlFoto varchar(200)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- ---------------------------------------------------------------------------------------

create table lembretes(
codigo int not null auto_increment primary key,
titulo varchar(100),
descricao varchar(100),
data_de_cadastro varchar(200),
data_de_aviso varchar(200)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- ----------------------------------------------------------------------------------------
create table produtos (
codigo int not null auto_increment primary key,
descricao varchar(100),
categoria varchar(100),
data_de_cadastro varchar(200),
preco_de_compra varchar(200),
preco_de_venda varchar(100),
quantidade varchar(200),
marca varchar(100),
observacoes varchar(100),
modelo varchar(200),
numero_de_serie varchar(200),
cor varchar(100),
urlFoto varchar(200)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- -----------------------------------------------------------------------------------------
create table usuarios (
codigo int not null auto_increment primary key,
nome_completo varchar(100),
data_de_cadastro varchar(100),
apelido varchar(200),
senha varchar(200),
pergunta_secreta varchar(100),
resposta_secreta varchar(200),
sobre_min varchar(100)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- ------------------------------------------------------------------------------------------

create table vendas (
codigo int not null auto_increment primary key,
data_venda varchar(100),
desconto varchar(100),
valor_parcela varchar(200),
valor_total varchar(200),
forma_de_pagamento varchar(100),
vezes varchar(200),
permissao_para_entrega varchar(100),
preco_entrega varchar(100),
data_entrega varchar(200),
codigo_cliente varchar(200),
nome_cliente varchar(100),
rua_cliente varchar(200),
numero_cliente varchar(200),
bairro_cliente varchar(200),
complemento_cliente varchar(200),
cidade_cliente varchar(200),
estado_cliente varchar(200),
cep varchar(200),
telefone_01 varchar(200),
telefone_02 varchar(200),
email varchar(200)
)engine = InnoDB DEFAULT CHARSET=utf8;
  -- ------------------------------------------------------------------------------------------
create table produtos_venda (
codigo int,
descricao varchar(100),
categoria varchar(100),
preco_de_venda varchar(100),
quantidade varchar(200),
marca varchar(100),
modelo varchar(200),
numero_de_serie varchar(200),
cor varchar(100),
codigo_compra int not null auto_increment primary key
)engine = InnoDB DEFAULT CHARSET=utf8;

  -- ------------------------------------------------------------------------------------------
 create table categorias (
codigo int not null auto_increment primary key,
nome varchar(100)
)engine = InnoDB DEFAULT CHARSET=utf8;

















