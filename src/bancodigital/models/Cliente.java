package bancodigital.models;

import bancodigital.models.Contas.Conta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private List<Conta> contas;


    public Cliente(String nome, String cpf){
        this.nome = nome.toLowerCase();
        this.cpf = cpf;
        contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta){
        contas.add(conta);
    }

    public void removerConta(Conta conta) {
        contas.remove(conta);
    }

    public List<Conta> listarConta() {
        return Collections.unmodifiableList(contas); // import java.util.Collections;
    }


}
