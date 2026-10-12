package bancodigital.app;

import bancodigital.models.Contas.Conta;
import bancodigital.models.Transacao;
import bancodigital.servico.Banco;
import bancodigital.tipo.TipoConta;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Banco banco = new Banco();

    public static void main(String[] args) {
        System.out.println("SEJA BEM-VINDO AO BANCO DIGITAL RDMASTER !");
        boolean executando = true;

        while (executando) {
            try {
                System.out.println("\nDIGITE UMA OPÇÃO:");
                System.out.println("1 - ENTRAR NA CONTA");
                System.out.println("2 - CADASTRAR CLIENTE");
                System.out.println("3 - ABRIR CONTA");
                System.out.println("0 - SAIR");

                switch (lerInt("> ")) {
                    case 1 -> entrarNaConta();
                    case 2 -> cadastrarCliente();
                    case 3 -> abrirConta();
                    case 0 -> {
                        executando = false;
                        System.out.println("ATÉ LOGO !");
                    }
                    default -> System.out.println("OPÇÃO INVÁLIDA, DIGITE NOVAMENTE");
                }
            } catch (RuntimeException e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
    }

    // ---------- TELA INICIAL ----------

    private static void cadastrarCliente() {
        String nome = lerTexto("NOME: ");
        String cpf = lerTexto("CPF: ");
        banco.cadastrarCliente(nome, cpf);
        System.out.println("CLIENTE CADASTRADO COM SUCESSO !");
    }

    private static void abrirConta() {
        String nome = lerTexto("NOME DO CLIENTE: ");
        String cpf = lerTexto("CPF DO CLIENTE: ");
        System.out.println("TIPO DE CONTA:\n1 - CORRENTE\n2 - POUPANÇA");

        TipoConta tipo = switch (lerInt("> ")) {
            case 1 -> TipoConta.CORRENTE;
            case 2 -> TipoConta.POUPANCA;
            default -> throw new IllegalArgumentException("TIPO DE CONTA INVÁLIDO");
        };

        Conta conta = banco.abrirConta(nome, cpf, tipo); // precisa retornar Conta (veja ajustes)
        System.out.println("CONTA CRIADA COM SUCESSO !");
        System.out.println("NÚMERO DA CONTA: " + conta.getNumero()); // precisa do getter
    }

    // ---------- DENTRO DA CONTA ----------

    private static void entrarNaConta() {
        String numero = lerTexto("INFORME O NUMERO DA CONTA: ");
        Conta conta = banco.buscarConta(numero);
        System.out.println("CARREGANDO CONTA...");

        boolean naConta = true;
        while (naConta) {
            try {
                System.out.println("\n------------ CONTA " + numero + " ------------");
                System.out.println("SALDO ATUAL: " + conta.getSaldo());
                System.out.println("DIGITE UMA OPÇÃO:");
                System.out.println("1 - DEPOSITAR");
                System.out.println("2 - SACAR");
                System.out.println("3 - TRANSFERIR");
                System.out.println("4 - EXTRATO");
                System.out.println("5 - APLICAR RENDIMENTO (POUPANÇA)");
                System.out.println("6 - ENCERRAR CONTA");
                System.out.println("0 - SAIR DA CONTA");

                switch (lerInt("> ")) {
                    case 1 -> depositar(conta);
                    case 2 -> sacar(conta);
                    case 3 -> transferir(numero);
                    case 4 -> mostrarExtrato(conta);
                    case 5 -> aplicarRendimento(numero);
                    case 6 -> {
                        banco.encerrarConta(numero);
                        System.out.println("CONTA ENCERRADA COM SUCESSO !");
                        naConta = false;
                    }
                    case 0 -> naConta = false;
                    default -> System.out.println("OPÇÃO INVÁLIDA, DIGITE NOVAMENTE");
                }
            } catch (RuntimeException e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
    }

    private static void depositar(Conta conta) {
        conta.depositar(lerDouble("VALOR DO DEPÓSITO: "));
        System.out.println("DEPÓSITO REALIZADO COM SUCESSO !");
    }

    private static void sacar(Conta conta) {
        conta.sacar(lerDouble("VALOR DO SAQUE: "));
        System.out.println("SAQUE REALIZADO COM SUCESSO !");
    }

    private static void transferir(String numeroOrigem) {
        String destino = lerTexto("NÚMERO DA CONTA DE DESTINO: ");
        double valor = lerDouble("VALOR DA TRANSFERÊNCIA: ");
        banco.transferir(numeroOrigem, destino, valor);
        System.out.println("TRANSFERÊNCIA REALIZADA COM SUCESSO !");
    }

    private static void mostrarExtrato(Conta conta) {
        System.out.println("---------- EXTRATO ----------");
        if (conta.getExtrato().isEmpty()) {
            System.out.println("NENHUMA TRANSAÇÃO REGISTRADA");
            return;
        }
        for (Transacao transacao : conta.getExtrato()) {
            System.out.println(transacao); // precisa de toString() na Transacao
        }
    }

    private static void aplicarRendimento(String numero) {
        double valor = lerDouble("VALOR DO RENDIMENTO: ");
        banco.aplicarRendimento(numero, valor); // só poupança; corrente cai no catch com a sua mensagem
    }

    // ---------- LEITURA SEGURA DO TECLADO ----------

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        try {
            return Integer.parseInt(lerTexto(mensagem));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("DIGITE UM NÚMERO INTEIRO VÁLIDO");
        }
    }

    private static double lerDouble(String mensagem) {
        try {
            return Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("DIGITE UM VALOR NUMÉRICO VÁLIDO");
        }
    }
}