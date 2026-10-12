package bancodigital.models.Contas;

import bancodigital.execao.Tratamentos;
import bancodigital.models.Cliente;

public class ContaCorrente  extends  Conta {
    private double limiteChequeEspecial;

    public ContaCorrente(String numero, Cliente titular) {
        super(numero, titular);
        limiteChequeEspecial = 3315;
    }


    @Override
    protected void debitar(double valor) {
        if(valor <= 0)
            throw new Tratamentos.ValorInvalidoException("VALOR TEM QUE SER MAIOR QUE 0");
        else if (valor > saldo + limiteChequeEspecial) {
            throw new Tratamentos.OperacaoNaoAutorizadaExpection("O VALOR DA OPERAÇÃO ULTRAPASSA O SEU CHEQUE ESPECIAL");
        }
        else if (valor > saldo) {
            throw new Tratamentos.SaldoInsuficienteExpection("VALOR ULTRAPASSA O SALDO DA SUA CONTA!");
        }

    }
}
