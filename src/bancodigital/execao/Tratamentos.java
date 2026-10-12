package bancodigital.execao;


public class Tratamentos {


    static public class ValorInvalidoException extends RuntimeException {
        public ValorInvalidoException(String message) {
            super(message);
        }
    }

    static public class SaldoInsuficienteExpection extends RuntimeException{
        public SaldoInsuficienteExpection(String mensagem){
            super(mensagem);
        }
    }

    static public class OperacaoNaoAutorizadaExpection extends RuntimeException{
        public OperacaoNaoAutorizadaExpection(String mensagem){
            super(mensagem);
        }
    }

    static public class ContaNaoEncontradaExpection extends RuntimeException{
        public ContaNaoEncontradaExpection(String mensagem){
            super(mensagem);
        }
    }

    static public class ClienteNaoEncontradoExpection extends RuntimeException{
        public ClienteNaoEncontradoExpection(String mensagem){
            super(mensagem);
        }
    }

    static public class CpfNaoEncotradoExpection extends RuntimeException{
        public CpfNaoEncotradoExpection(String mensagem){
            super(mensagem);
        }
    }


}

