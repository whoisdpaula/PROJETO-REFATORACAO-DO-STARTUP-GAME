package persistencia;

import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;
import exception.PersistenceException;

import java.sql.*;

public class StartupRepository {

    public long salvar(Startup s) {

        String sql = """
            INSERT INTO startup
            (nome, caixa, receita_base, reputacao, moral, rodada_atual, bonus_percent_receita)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection c = DataSourceProvider.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, s.getNome());
            ps.setDouble(2, s.getCaixa().valor());
            ps.setDouble(3, s.getReceitaBase().valor());
            ps.setInt(4, s.getReputacao());
            ps.setInt(5, s.getMoral().valor());
            ps.setInt(6, s.getRodadaAtual());
            ps.setDouble(7, s.getBonusPercentReceitaProx().valor());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new PersistenceException("Falha ao salvar a Startup no DB.", e);
        }

        return -1;
    }

    public Startup buscarPorId(long id) {

        String sql = """
            SELECT nome, caixa, receita_base, reputacao, moral, rodada_atual, bonus_percent_receita
            FROM startup
            WHERE id = ?
            """;

        try (Connection c = DataSourceProvider.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) return null;

                Startup s = new Startup(
                        rs.getString("nome"),
                        new Dinheiro(rs.getDouble("caixa")),
                        new Dinheiro(rs.getDouble("receita_base")),
                        rs.getInt("reputacao"),
                        new Humor(rs.getInt("moral"))
                );

                s.setRodadaAtual(rs.getInt("rodada_atual"));
                s.setBonusPercentReceitaProx(new Percentual(rs.getDouble("bonus_percent_receita"))); // NOVO: Setar o Percentual VO
                s.setId(id);

                return s;
            }

        } catch (SQLException e) {
            throw new PersistenceException("Falha ao buscar a Startup no DB.", e);
        }
    }

    public void atualizar(Startup s) {

        String sql = """
            UPDATE startup
            SET nome = ?, caixa = ?, receita_base = ?, reputacao = ?, moral = ?, rodada_atual = ?, bonus_percent_receita = ?
            WHERE id = ?
            """;

        try (Connection c = DataSourceProvider.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, s.getNome());
            ps.setDouble(2, s.getCaixa().valor());
            ps.setDouble(3, s.getReceitaBase().valor());
            ps.setInt(4, s.getReputacao());
            ps.setInt(5, s.getMoral().valor());
            ps.setInt(6, s.getRodadaAtual());
            ps.setDouble(7, s.getBonusPercentReceitaProx().valor());
            ps.setLong(8, s.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Falha ao atualizar a Startup no DB.", e);
        }
    }
}