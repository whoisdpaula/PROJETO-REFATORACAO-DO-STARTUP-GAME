package ui;

import actions.*;
import engine.GameEngine;
import model.Startup;
import model.Deltas;
import model.vo.Dinheiro;
import persistencia.StartupRepository;
import config.Config;
import exception.DomainException;
import exception.SaldoInsuficienteException;
import service.ReportService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final StartupRepository startupRepository;
    private final GameEngine gameEngine;
    private final ReportService reportService;

    private final int TOTAL_RODADAS;
    private final int DECISOES_POR_RODADA;

    private final Map<Integer, DecisaoStrategy> mapaDecisoes = new HashMap<>();

    public ConsoleApp(
            Config config,
            GameEngine gameEngine,
            StartupRepository startupRepository,
            ReportService reportService
    ) {
        this.gameEngine = gameEngine;
        this.startupRepository = startupRepository;
        this.reportService = reportService;

        this.TOTAL_RODADAS = config.totalRodadas();
        this.DECISOES_POR_RODADA = config.maxDecisoesPorRodada();

        mapaDecisoes.put(1, new MarketingStrategy());
        mapaDecisoes.put(2, new EquipeStrategy());
        mapaDecisoes.put(3, new CortarCustosStrategy());
        mapaDecisoes.put(4, new InvestidoresStrategy());

        scanner.useLocale(Locale.US);
    }

    public void start() {
        run();
    }

    private void run() {
        exibirTitulo();

        Startup startup = inicializarStartup();
        long startupId = startup.getId();

        while (startup.getRodadaAtual() <= TOTAL_RODADAS) {
            System.out.println("\n--- Rodada " + startup.getRodadaAtual() + " de " + TOTAL_RODADAS + " ---");

            startup = startupRepository.buscarPorId(startupId);
            if (startup == null) {
                System.out.println("❌ Erro fatal: Startup não encontrada.");
                break;
            }

            exibirEstado(startup);

            System.out.print("\nEscolha o modo de jogo: [1] Manual | [2] Bot (Automático) | [0] Pular Rodada: ");
            int modoEscolha = lerModoJogo();

            if (modoEscolha == 0) {
                System.out.println("Rodada pulada.");
            } else if (modoEscolha == 2) {

                executarRodadaBot(startupId);
            } else {

                int decisoesTomadas = 0;
                while (decisoesTomadas < DECISOES_POR_RODADA) {
                    System.out.println("\nDecisões restantes: " + (DECISOES_POR_RODADA - decisoesTomadas));
                    exibirOpcoesDecisao();

                    System.out.print("Escolha uma decisão (1-4) ou 0 para finalizar decisões: ");
                    int escolha = lerEscolha();

                    if (escolha == 0) {
                        System.out.println("Decisões finalizadas nesta rodada.");
                        break;
                    }

                    try {
                        decisoesTomadas += executarDecisao(startupId, escolha);
                    } catch (RuntimeException e) {
                        System.out.println("❌ Erro fatal do sistema: " + e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                }
            }

            if (startup.getRodadaAtual() <= TOTAL_RODADAS) {
                gameEngine.finalizarRodada(startupId);
            }
        }

        encerrarJogo(startupId);
    }

    private void exibirTitulo() {
        System.out.println("==========================================");
        System.out.println("     🚀 Startup Game - Console 🚀        ");
        System.out.println("==========================================");
        System.out.println("Total de Rodadas: " + TOTAL_RODADAS);
        System.out.println("Decisões por Rodada: " + DECISOES_POR_RODADA);
    }

    private Startup inicializarStartup() {
        System.out.print("\nDigite o nome da sua nova Startup: ");
        String nome = scanner.nextLine();
        Startup startup = new Startup(nome);
        long id = startupRepository.salvar(startup);
        startup.setId(id);
        System.out.println("Startup '" + nome + "' criada com sucesso! ID: " + id);
        return startup;
    }

    private void exibirEstado(Startup s) {
        System.out.println(" --------------------------------------");
        System.out.printf(" [ESTADO] Caixa: %s | Reputação: %d | Moral: %d%n",
                s.getCaixa().toString(), s.getReputacao(), s.getMoral().valor());
        System.out.println(" --------------------------------------");
    }

    private void exibirOpcoesDecisao() {
        mapaDecisoes.forEach((key, strategy) ->
                System.out.printf("  [%d] %s (%s)%n", key,
                        strategy.nome(), strategy.getClass().getSimpleName()));
        System.out.println("  [0] Finalizar decisões.");
    }

    private int lerEscolha() {
        while (true) {
            if (scanner.hasNextInt()) {
                int escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha >= 0 && escolha <= 4) {
                    return escolha;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Opção inválida. Tente novamente.");
            System.out.print("Escolha uma decisão (1-4) ou 0 para finalizar: ");
        }
    }

    private int lerModoJogo() {
        while (true) {
            if (scanner.hasNextInt()) {
                int escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha >= 0 && escolha <= 2) {
                    return escolha;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Opção inválida. Digite 1 (Manual), 2 (Bot) ou 0 (Pular).");
            System.out.print("Escolha o modo de jogo: [1] Manual | [2] Bot (Automático) | [0] Pular Rodada: ");
        }
    }

    private int executarDecisao(long startupId, int escolha) {
        DecisaoStrategy acao = mapaDecisoes.get(escolha);
        if (acao == null) {
            System.out.println("Opção inválida. Tente novamente.");
            return 0;
        }

        Dinheiro valorInput = new Dinheiro(0.0);

        if (acao.requerInput()) {
            System.out.print("Digite o valor que o investidor irá injetar (ex: 2000.00): R$");
            try {
                double valor = scanner.nextDouble();
                scanner.nextLine();

                valorInput = new Dinheiro(valor);

            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida. Digite um valor numérico.");
                scanner.nextLine();
                return 0;
            } catch (DomainException e) {
                System.out.println("❌ Erro de Valor: " + e.getMessage());
                return 0;
            }
        }

        try {
            Deltas deltasDecisao = gameEngine.executarDecisao(startupId, acao, valorInput);

            System.out.printf("✅ Decisão aplicada: %s%n", acao.getClass().getSimpleName());

            if (deltasDecisao.caixaDelta().valor() > 0) {
                System.out.printf("  💰 Recebido: %s%n", deltasDecisao.caixaDelta().toString());
            } else if (deltasDecisao.caixaDelta().valor() < 0) {
                Dinheiro gasto = new Dinheiro(-deltasDecisao.caixaDelta().valor());
                System.out.printf("  📉 Gasto: %s%n", gasto.toString());
            }

            Startup startupAtualizada = startupRepository.buscarPorId(startupId);
            exibirEstado(startupAtualizada);

            return 1;

        } catch (SaldoInsuficienteException e) {
            System.out.println("⚠️ Não foi possível executar: " + e.getMessage());
            return 0;
        } catch (DomainException e) {
            System.out.println("❌ Erro de Domínio: " + e.getMessage());
            return 0;
        }
    }

    private void executarRodadaBot(long startupId) {
        System.out.println("🤖 O Bot está jogando a rodada...");

        for (int i = 0; i < DECISOES_POR_RODADA; i++) {

            DecisaoStrategy botAcao = new BotStrategy();

            try {
                gameEngine.executarDecisao(startupId, botAcao);

                Startup startupAtualizada = startupRepository.buscarPorId(startupId);
                System.out.printf("🤖 Decisão %d/%d: %s. Novo estado:%n",
                        (i + 1), DECISOES_POR_RODADA, botAcao.nome());
                exibirEstado(startupAtualizada);

                Thread.sleep(500);

            } catch (DomainException e) {
                System.out.println("🤖 O Bot falhou em tomar uma decisão: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("❌ Erro inesperado durante o jogo do Bot: " + e.getMessage());
                break;
            }
        }
    }

    private void encerrarJogo(long startupId) {
        Startup startup = startupRepository.buscarPorId(startupId);
        if (startup == null) return;

        System.out.println("\n==========================================");
        System.out.println("     🏆 JOGO ENCERRADO 🏆        ");
        System.out.println("==========================================");
        System.out.println("Startup: " + startup.getNome());
        System.out.printf("Caixa Final: %s%n", startup.getCaixa().toString());
        System.out.println("Reputação Final: " + startup.getReputacao());
        System.out.println("Moral Final: " + startup.getMoral().valor());
        System.out.printf("SCORE FINAL: %.2f%n", startup.scoreFinal());

        // CHAMADA FINAL DO SERVIÇO DE RELATÓRIO
        try {
            String nomeArquivo = "historico_" + startup.getNome().replaceAll("\\s+", "_");
            reportService.exportarHistoricoCSV(startup, nomeArquivo);
            System.out.printf("📄 Relatório CSV gerado com sucesso: %s.csv%n", nomeArquivo);
        } catch (IOException e) {
            System.out.println("❌ Falha ao exportar relatório CSV: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar histórico para relatório: " + e.getMessage());
        }
    }
}