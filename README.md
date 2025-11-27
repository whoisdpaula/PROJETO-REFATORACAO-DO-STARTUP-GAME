# 🎮 PROJETO: REFATORAÇÃO DO STARTUP GAME 🎮 

O Startup Game é uma simulação em turnos (rodadas) onde cada usuário gerencia uma startup. Em cada
rodada, os jogadores podem tomar até N decisões (configurável) dentre opções como Marketing, Equipe,
Produto, Investidores e Cortar Custos. As decisões impactam Caixa, Receita Base, Reputação e Moral da
startup. Após o número total de rodadas, o jogo calcula um score final e apresenta o ranking




---

# 🌳 Arquitetura

```
PROJETO-REFATORACAO-DO-STARTUP-GAME-main/
.
├── .devcontainer/
├── .idea/
├── out/
│
└── Projeto StartupGame/
    ├── main/
    │   ├── actions/
    │   │   ├── BotStrategy.java
    │   │   ├── CortarCustosStrategy.java
    │   │   ├── DecisaoFactory.java
    │   │   ├── DecisaoStrategy.java
    │   │   ├── EquipeStrategy.java
    │   │   ├── InvestidoresStrategy.java
    │   │   ├── MarketingStrategy.java
    │   │   └── ProdutoStrategy.java
    │   ├── config/
    │   │   └── Config.java
    │   ├── engine/
    │   │   ├── GameEngine.java
    │   │   └── ScoreService.java
    │   ├── exception/
    │   │   ├── DomainException.java
    │   │   ├── PersistenceException.java
    │   │   └── SaldoInsuficienteException.java
    │   ├── model/
    │   │   ├── vo/
    │   │   │   ├── Deltas.java
    │   │   │   └── Rodada.java
    │   │   └── Startup.java
    │   ├── persistencia/
    │   │   ├── DataSourceProvider.java
    │   │   ├── DecisaoAplicadaRepository.java
    │   │   ├── RodadaRepository.java
    │   │   └── StartupRepository.java
    │   ├── resources/
    │   ├── service/
    │   │   └── ReportService.java
    │   └── ui/
    │       ├── ConsoleApp.java
    │       └── Main.java
    ├── historico_lais.csv
    ├── Projeo StartupGame.iml
    ├── README.md
    ├── startupdb.mv.db
    └── startupdb.trace.db    
```

--- 
# 🕹️ Modos de Jogo e Funcionalidades
O ConsoleApp oferece um menu de escolha no início de cada rodada:

[1] Manual: O jogador escolhe cada decisão (Marketing, Equipe, Cortar Custos, Investidores) até esgotar o limite.

[2] Bot (Automático): O sistema executa a BotStrategy para tomar todas as decisões da rodada automaticamente, ideal para simulações ou testes.

[0] Pular Rodada: Nenhuma decisão é tomada, a rodada avança.

Relatório Final

No encerramento do jogo, a aplicação gera um arquivo CSV (historico_[NomeStartup].csv) contendo o histórico detalhado de todas as ações e os valores da Startup ao longo do tempo. Este arquivo será salvo no diretório de execução.

---







https://github.com/user-attachments/assets/0e9f8733-cc2b-4401-810b-bc71a4b15fb8





