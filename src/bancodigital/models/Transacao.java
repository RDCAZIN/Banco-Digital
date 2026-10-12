package bancodigital.models;

import bancodigital.tipo.TipoTransacao;

import java.time.LocalDateTime;

public class Transacao {
    private TipoTransacao tipoTransacao;
    private double valor;
    private LocalDateTime dateTime;
    private double saldoApos;

    public Transacao(TipoTransacao tipoTransacao, double valor, double saldoApos){
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.saldoApos = saldoApos;
        dateTime = LocalDateTime.now();
    }

}
