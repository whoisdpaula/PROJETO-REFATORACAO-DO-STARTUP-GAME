package persistencia;

import model.Deltas;
import model.vo.Dinheiro;
import exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DecisaoAplicadaRepository {

    public long salvar(long startupId, int rodada, String tipo, Deltas deltas) {

        String sql = "INSERT INTO decisao_aplicada (startup_id, rodada, tipo, caixa_delta, reputacao_delta, moral_delta, bonus_delta) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataSourceProvider.get();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, startupId);
            ps.setInt(2, rodada);
            ps.setString(3, tipo);

            ps.setDouble(4, deltas.caixaDelta().valor());

            ps.setInt(5, deltas.reputacaoDelta());


            ps.setInt(6, deltas.moralDelta().valor());

            ps.setDouble(7, deltas.bonusDelta().valor());

            ps.executeUpdate();

            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return 0;

        } catch (SQLException e) {

            throw new PersistenceException("Falha ao registrar decisão aplicada no DB.", e);
        }
    }
}