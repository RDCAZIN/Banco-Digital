package bancodigital.tipo;

import bancodigital.models.Contas.Conta;

public interface Transferivel {
    void transferir(Conta destino, double valor);
}
