package bancodigital.models.Contas;

import bancodigital.execao.Tratamentos;
import bancodigital.models.Cliente;
import bancodigital.models.Transacao;
import bancodigital.tipo.TipoTransacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Conta {
    private final String numero;
    private final Cliente titular;
    protected double saldo;
    private final List<Transacao> extrato;

    public Conta(String numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0;
        this.extrato = new ArrayList<>();
    }

    public void depositar(double valor) {
        validarValor(valor);
        saldo += valor;
        registrar(TipoTransacao.DEPOSITO, valor, saldo);
    }

    public void sacar(double valor) {
        validarValor(valor);
        debitar(valor);
        saldo -= valor;
        registrar(TipoTransacao.SAQUE, valor, saldo);
    }

    public void transferir(Conta destino, double valor) {
        if (destino == null)
            throw new Tratamentos.ContaNaoEncontradaExpection("CONTA DE DESTINO NÃO ENCONTRADA");
        if (destino == this)
            throw new Tratamentos.OperacaoNaoAutorizadaExpection("NÃO É POSSÍVEL TRANSFERIR PARA A PRÓPRIA CONTA");
        validarValor(valor);

        debitar(valor);
        saldo -= valor;
        destino.receberTransferencia(valor);
        registrar(TipoTransacao.TRANSFERENCIA_ENVIADA, valor, saldo);
    }

    private void receberTransferencia(double valor) {
        saldo += valor;
        registrar(TipoTransacao.TRANSFERENCIA_RECEBIDA, valor, saldo);
    }

    private void validarValor(double valor) {
        if (!Double.isFinite(valor) || valor <= 0)
            throw new Tratamentos.ValorInvalidoException("O valor da operação deve ser maior que 0");
    }

    protected void registrar(TipoTransacao tipo, double valor, double saldoApos) {
        extrato.add(new Transacao(tipo, valor, saldoApos));
    }

    protected abstract void debitar(double valor);

    public String getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transacao> getExtrato() {
        return Collections.unmodifiableList(extrato);
    }
}