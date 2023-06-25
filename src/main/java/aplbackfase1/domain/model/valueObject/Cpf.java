package aplbackfase1.domain.model.valueObject;

import aplbackfase1.domain.model.exceptions.CpfInvalidoException;

import java.util.InputMismatchException;
import java.util.Objects;

public class Cpf {

    private String valor;
    Cpf() {}

    public Cpf(String cpf) {
        this.valor = Objects.nonNull(cpf) ? cpf : "";
        if (isValid()) {
            throw new CpfInvalidoException();
        }
    }

    public String getValor() {
        return this.valor;
    }

    @Override
    public String toString() {
        return getValor();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cpf other = (Cpf) obj;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        return true;
    }

    public boolean isInvalid() {
        return !isValid();
    }

    public boolean isValid() {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (getValor().equals("00000000000") ||
                getValor().equals("11111111111") ||
                getValor().equals("22222222222") || getValor().equals("33333333333") ||
                getValor().equals("44444444444") || getValor().equals("55555555555") ||
                getValor().equals("66666666666") || getValor().equals("77777777777") ||
                getValor().equals("88888888888") || getValor().equals("99999999999") ||
                (getValor().length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(getValor().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(getValor().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == getValor().charAt(9)) && (dig11 == getValor().charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

}