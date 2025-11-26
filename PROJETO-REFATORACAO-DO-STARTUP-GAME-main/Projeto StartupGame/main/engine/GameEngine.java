package engine;

import actions.DecisaoStrategy;
import model.Startup;
import model.Deltas;
import model.vo.Dinheiro;
import model.vo.RodadaResumo;
import persistencia.StartupRepository;
import persistencia.DecisaoAplicadaRepository;
import persistencia.RodadaRepository;
import exception.DomainException;
import exception.PersistenceException;

public class GameEngine {

    private final StartupRepository startupRepository;
    private final DecisaoAplicadaRepository decisaoAplicadaRepository;
    private final RodadaRepository rodadaRepository;

    public GameEngine(
            StartupRepository startupRepository,
            DecisaoAplicadaRepository decisaoAplicadaRepository,
            RodadaRepository rodadaRepository
    ) {
        this.startupRepository = startupRepository;
        this.decisaoAplicadaRepository = decisaoAplicadaRepository;
        this.rodadaRepository = rodadaRepository;
    }

    public Deltas executarDecisao(long startupId, DecisaoStrategy acao) {
        return executarDecisao(startupId, acao, new Dinheiro(0.0));
    }

    public Deltas executarDecisao(long startupId, DecisaoStrategy acao, Dinheiro valorInput) {
        Startup startup;
        try {
            startup = startupRepository.buscarPorId(startupId);
        } catch (PersistenceException e) {
            throw new RuntimeException("Erro ao buscar a Startup no banco de dados.", e);
        }

        if (startup == null) {
            throw new DomainException("Startup com ID " + startupId + " não encontrada.");
        }

        Deltas deltasDecisao;

        try {
            deltasDecisao = acao.aplicar(startup, valorInput);

            decisaoAplicadaRepository.salvar(
                    startupId,
                    startup.getRodadaAtual(),
                    acao.getClass().getSimpleName(),
                    deltasDecisao
            );

            startup.aplicarDeltas(deltasDecisao);
            startupRepository.atualizar(startup);

        } catch (DomainException e) {
            throw e;
        } catch (PersistenceException e) {
            throw new RuntimeException("Falha ao salvar o estado da Startup após a decisão.", e);
        }

        return deltasDecisao;
    }

    public void finalizarRodada(long startupId) {
        Startup startup;
        try {
            startup = startupRepository.buscarPorId(startupId);
        } catch (PersistenceException e) {
            throw new RuntimeException("Erro ao buscar a Startup no banco de dados para finalizar a rodada.", e);
        }

        if (startup == null) return;

        Dinheiro caixaAntes = startup.getCaixa();

        Dinheiro receita = startup.receitaRodada();

        Dinheiro novoCaixa = startup.getCaixa().somar(receita);
        startup.setCaixa(novoCaixa);

        startup.registrar("Receita gerada: " + receita.toString());

        try {
            RodadaResumo resumo = new RodadaResumo(startup, receita, caixaAntes);
            rodadaRepository.salvar(resumo);

            startup.setRodadaAtual(startup.getRodadaAtual() + 1);
            startupRepository.atualizar(startup);
        } catch (PersistenceException e) {
            throw new RuntimeException("Falha ao atualizar o estado da Startup na finalização da rodada.", e);
        }
    }
}