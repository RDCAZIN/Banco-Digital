package bancodigital.models.Contas;

import bancodigital.execao.Tratamentos;
import bancodigital.models.Cliente;

public class ContaPoupanca extends Conta {
    private double taxaRendimento;

    public ContaPoupanca(String numero, Cliente titular) {
        super(numero, titular);
        taxaRendimento = 0.0002;
    }

    public void setTaxaRendimento(double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    @Override
    protected void debitar(double valor) {
        if(valor <= 0)
            throw new Tratamentos.ValorInvalidoException("VALOR INVALIDO! TEM QUE SER MAIOR QUE 0");
        else if (valor > saldo) {
            throw new Tratamentos.SaldoInsuficienteExpection("OPERAÇÃO CANCELADA POR SALDO INSUFICENTE ! ");
        }
    }

    public void aplicarRendimento(double valor){
        if(valor<=0){
            throw  new Tratamentos.ValorInvalidoException("O VALOR DA APLICAÇÃO DEVE SER MAIOR QUE 0");
        }
        System.out.println("RENDIENTO APLICADO COM SUCESSO!");
        System.out.println("TAXA DE RENDIMENTO DA APLICAÇÃO: "+taxaRendimento+ "ao mês");
        double montante = valor * Math.pow((1 + taxaRendimento),12);
        System.out.println("PREVISÃO DO VALOR EM 12 MESES: "+montante);
    }


}
