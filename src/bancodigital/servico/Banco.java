package bancodigital.servico;

import bancodigital.execao.Tratamentos;
import bancodigital.models.Cliente;
import bancodigital.models.Contas.Conta;
import bancodigital.models.Contas.ContaCorrente;
import bancodigital.models.Contas.ContaPoupanca;
import bancodigital.tipo.TipoConta;

import java.util.HashMap;
import java.util.Map;

public class Banco {
    private final Map<String, Cliente> clientes;
    private final Map<String, Conta> contas;

    public Banco() {
        this.clientes = new HashMap<>();
        this.contas = new HashMap<>();
    }

    public void cadastrarCliente(String nome, String cpf) {
        if (vazio(nome) || vazio(cpf))
            throw new Tratamentos.OperacaoNaoAutorizadaExpection("NOME E CPF SÃO OBRIGATORIOS");

        String chave = cpf.trim();
        if (clientes.containsKey(chave))
            throw new Tratamentos.OperacaoNaoAutorizadaExpection("JÁ EXISTE UM CLIENTE CADASTRADO COM ESSE CPF");

        clientes.put(chave, new Cliente(nome.trim(), chave));
    }

    public Conta abrirConta(String nome, String cpf, TipoConta tipo) {
        if (vazio(cpf) || vazio(nome))
            throw new Tratamentos.CpfNaoEncotradoExpection("O SEU CPF OU NOME ESTA INVALIDO, INFORME NOVAMENTE!");
        if (tipo == null)
            throw new Tratamentos.ValorInvalidoException("É OBRIGATORIO INFORMAR O TIPO DA CONTA DO CLIENTE");

        Cliente cliente = buscarCliente(cpf);
        String numero = gerarNumeroConta();

        Conta conta = switch (tipo) {
            case CORRENTE -> new ContaCorrente(numero, cliente);
            case POUPANCA -> new ContaPoupanca(numero, cliente);
        };

        contas.put(numero, conta);
        cliente.adicionarConta(conta);
        return conta;
    }

    public Conta buscarConta(String numero) {
        if (vazio(numero))
            throw new Tratamentos.OperacaoNaoAutorizadaExpection("NUMERO INVALIDO DIGITE NOVAMENTE !");

        Conta conta = contas.get(numero.trim());
        if (conta == null)
            throw new Tratamentos.ContaNaoEncontradaExpection("NENHUMA CONTA FOI ENCONTRADA COM ESSE NUMERO");
        return conta;
    }

    public Cliente buscarCliente(String cpf) {
        if (vazio(cpf))
            throw new Tratamentos.OperacaoNaoAutorizadaExpection("CPF INVALIDO, DIGITE NOVAMENTE");

        Cliente cliente = clientes.get(cpf.trim());
        if (cliente == null)
            throw new Tratamentos.ClienteNaoEncontradoExpection("NENHUM CLIENTE COM ESSE CPF FOI ENCONTRADO");
        return cliente;
    }

    public void transferir(String numeroOrigem, String numeroDestino, double valor) {
        Conta origem = buscarConta(numeroOrigem);
        Conta destino = buscarConta(numeroDestino);
        origem.transferir(destino, valor); // a Conta valida valor e transferência para si mesma
    }

    public void aplicarRendimento(String numero, double valor) {
        Conta conta = buscarConta(numero);
        if (!(conta instanceof ContaPoupanca poupanca))
            throw new Tratamentos.OperacaoNaoAutorizadaExpection("SÓ CONTAS POUPANÇA RECEBEM RENDIMENTO");
        poupanca.aplicarRendimento(valor);
    }

    public void encerrarConta(String numero) {
        Conta conta = buscarConta(numero);


        if (Math.abs(conta.getSaldo()) > 0.005)
            throw new Tratamentos.OperacaoNaoAutorizadaExpection(
                    "A CONTA SÓ PODE SER ENCERRADA COM SALDO ZERADO");

        conta.getTitular().removerConta(conta);
        contas.remove(conta.getNumero());
    }

    // ---------- AUXILIARES ----------

    private String gerarNumeroConta() {
        String numero;
        do {
            numero = String.valueOf((int) (Math.random() * 9000) + 1000);
        } while (contas.containsKey(numero));
        return numero;
    }

    private boolean vazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}