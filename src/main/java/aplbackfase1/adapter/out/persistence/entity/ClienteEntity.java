package aplbackfase1.adapter.out.persistence.entity;

import aplbackfase1.domain.model.Cliente;
import aplbackfase1.domain.model.valueObject.Cpf;
import aplbackfase1.domain.model.valueObject.Email;
import aplbackfase1.domain.model.valueObject.Nome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clientes")
public class ClienteEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Embedded
    private Nome nome;
    @Embedded
    private Cpf cpf;
    @Embedded
    private Email email;

    public Cliente to(ClienteEntity clienteEntity) {
        return Cliente.builder()
                .id(clienteEntity.getId())
                .nome(clienteEntity.getNome())
                .cpf(clienteEntity.getCpf())
                .email(clienteEntity.getEmail())
                .build();

    }

    public ClienteEntity from(Cliente cliente) {
        return ClienteEntity.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .build();
    }

}
