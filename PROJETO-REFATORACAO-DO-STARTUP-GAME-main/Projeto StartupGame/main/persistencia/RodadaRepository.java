package persistencia;

import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.RodadaResumo;
import exception.PersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RodadaRepository {

    public void salvar(RodadaResumo resumo) {

        String sql = """
            INSERT INTO rodada 
            (startup_id, numero, caixa_antes, caixa_depois, receita, moral, reputacao)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection c = DataSourceProvider.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, resumo.startupId());
            ps.setInt(2, resumo.numeroRodada());
            ps.setDouble(3, resumo.caixaAntes().valor());
            ps.setDouble(4, resumo.caixaDepois().valor());
            ps.setDouble(5, resumo.receitaGerada().valor());
            ps.setInt(6, resumo.moral().valor());
            ps.setInt(7, resumo.reputacao());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Falha ao persistir o resumo da rodada no DB.", e);
        }
    }

    public List<RodadaResumo> buscarHistorico(long startupId) {
        List<RodadaResumo> historico = new ArrayList<>();
        String sql = "SELECT * FROM rodada WHERE startup_id = ? ORDER BY numero";

        try (Connection c = DataSourceProvider.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, startupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RodadaResumo resumo = new RodadaResumo(
                            rs.getLong("startup_id"),
                            rs.getInt("numero"),
                            new Dinheiro(rs.getDouble("caixa_antes")),
                            new Dinheiro(rs.getDouble("caixa_depois")),
                            new Dinheiro(rs.getDouble("receita")),
                            new Humor(rs.getInt("moral")),
                            rs.getInt("reputacao")
                    );
                    historico.add(resumo);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Falha ao buscar o histórico de rodadas no DB.", e);
        }
        return historico;
    }
}