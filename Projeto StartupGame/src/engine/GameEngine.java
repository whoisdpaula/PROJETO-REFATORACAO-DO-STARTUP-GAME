package engine;

import actions.DecisaoStrategy;
import  actions.DecisaoFactory;
import config.Config;
import model.Deltas;
import model.Startup;
import persistencia.DecisaoAplicadaRepository;
import persistencia.RodadaRepository;
import persistencia.StartupRepository;

import java.util.Scanner;

public class GameEngine {


    private final Config config = new Config();
    private final Scanner scanner = new Scanner(System.in);

    private final StartupRepository startupRepository = new StartupRepository();
    private final RodadaRepository rodadaRepository = new RodadaRepository();
    private final DecisaoAplicadaRepository decisaoAplicadaRepository = new DecisaoAplicadaRepository();

    private Startup startup;

    public void iniciarJogo(){
        System.out.println("-----STARTUP GAME-----");

        System.out.println("NOME DA STARTUP:");
        String nome = scanner.nextLine();

        startup = new Startup(
                nome,
                new model.vo.Dinheiro(5000) ,
                new model.vo.Dinheiro(1000),
                new model.vo.Humor(50),
                new model.vo.Humor(50)
        );
        startupRepository.salvar(startup);

        loopRodadas();
    }
    private void loopRodadas(){
        for(int r = 1;r<= config.totalRodadas(); r++){
            startup.setRodadaAtual(r);

            System.out.println("\n-----------------------------------");
            System.out.println("RODADA: " + config.totalRodadas());
            System.out.println(startup);

            executarRodada(r);
            aplicarReceitaFinalRodada();

            rodadaRepository.registrar(r,startup);
        }
        encerrarJogo();
    }

    private void executarRodada(int r){
        int maxDecisoes = config.maxDecisoesPorRodada();

        for (int i = 1; i <= maxDecisoes; i++) {
            System.out.println("\nDecisão"+i+"/"+maxDecisoes);

            System.out.println("""
                        ESCOLHA UMA DECISÃO:
                        1 - MARKETING
                        2 - EQUIPE
                        3 - PRODUTO
                        4 - INVESTIDORES
                        5 - CORTAR_CUSTOS
                        0 - PULAR
                        OPÇÃO:""");

            String opt = scanner.nextLine().trim();

            if(opt.equals("0"))return;

            String tipo = switch (opt){
                case "1" -> "MARKETING";
                case "2" -> "EQUIPE";
                case "3" -> "PRODUTO";
                case "4" -> "INVESTIDORES";
                case "5" -> "CORTAR_CUSTOS";
                default -> {
                    System.out.println("OPÇÃO INVÁLIDA");
                    i--;
                    continue;
                }
            };
            aplicarDecisao(tipo);
        }

    }
    private void aplicarDecisao(String tipo){
        DecisaoStrategy strategy = DecisaoFactory.criar(tipo);

        Deltas d = strategy.aplicar(startup);

        if (d==null){
            System.out.println("ERRO DE APLICACAO");
            return;
        }

        startup.setCaixa(new model.vo.Dinheiro(startup.getCaixa().valor+d.caixaDelta()));
        startup.setReputacao(startup.getReputacao().aumentar(d.reputacaoDelta()));
        startup.setMoral(startup.getMoral().aumentar(d.moralDelta()));
        startup.addBonusPercentReceitaProx(d.bonusDelta());

        startup.clamparHumor();

        startup.registrar("APLICOU DECISÂO:"+tipo+"->"+d);

        decisaoRepository.registrar(startup.getRodadaAtual(),tipo,d);

    }
    private void aplicarReceitaFinalRodada(){
        double receita = startup.receitaRodada();

        startup.setCaixa(new model.vo.Dinheiro(startup.getCaixa().valor()+receita));

        startup.registrar("RECEBEU RECEITA DA RODADA:R$");
    }
    private void encerrarJogo(){
        System.out.println("\n----FIM DO JOGO----");
        System.out.println(startup);
        System.out.println("\nSCORE FINAL: " + startup.scoreFinal());


        startupRepository.atualizar(startup);

        System.out.println("\nHISTÓRICO:");
        startup.getHistorico().forEach(System.out::println);

    }
}
