# BancoDigital

Sistema bancário de terminal desenvolvido em Java para praticar Programação Orientada a Objetos. Projeto final do curso de POO da DIO.

## Funcionalidades

- Cadastro de clientes (com CPF único)
- Abertura de conta corrente ou poupança
- Depósito, saque e transferência entre contas
- Extrato com data, tipo, valor e saldo após cada operação
- Rendimento em conta poupança
- Encerramento de conta (somente com saldo zerado)

## Conceitos praticados

- **Abstração:** classe abstrata `Conta`, com o método `debitar()` implementado por cada tipo de conta
- **Herança:** `ContaCorrente` e `ContaPoupanca` estendem `Conta`
- **Polimorfismo:** o `Banco` trabalha com `Conta`, sem precisar saber o tipo
- **Encapsulamento:** atributos privados e listas devolvidas como somente leitura
- **Coleções:** `Map` para busca por número de conta e CPF, `List` para extrato e contas do cliente
- **Exceções customizadas:** regras de negócio validadas com mensagens claras, sem derrubar o menu

## Estrutura

```
bancodigital
├── app        → Main e menu de terminal
├── models     → Conta, ContaCorrente, ContaPoupanca, Cliente, Transacao
├── servico    → Banco
├── execao     → exceções customizadas
└── tipo       → enums (TipoConta, TipoTransacao)
```

## Como executar

Clone o repositório, abra em uma IDE (IntelliJ, Eclipse ou VS Code) e execute a classe `bancodigital.app.Main`.

## Autor

Ronald
