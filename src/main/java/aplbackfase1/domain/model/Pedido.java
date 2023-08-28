package aplbackfase1.domain.model;

import aplbackfase1.domain.enums.StatusPagamento;
import aplbackfase1.domain.enums.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.Date;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    private UUID idPedido;
    private UUID idCliente;
    private List<PedidoProduto> produtos;
    private StatusPedido statusPedido;
    private StatusPagamento statusPagamento;
    private BigDecimal valorPedido;
    private Date dataInclusao;
    private Date dataAtualizacao;

}
