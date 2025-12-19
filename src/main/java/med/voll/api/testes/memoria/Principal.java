package med.voll.api.testes.memoria;

import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

public class Principal {
    public static void main(String[] args) {
        Paciente p1 = new Paciente(
                1L, "João", null, null, null, null, true, null
        );

        Paciente p2 = new Paciente(
                1L, "Maria", null, null, null, null, true, null
        );

        System.out.println(p1.equals(p2));
    }
}
