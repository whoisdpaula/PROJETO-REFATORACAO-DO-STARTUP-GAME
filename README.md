# PROJETO: REFATORAÇÃO DO STARTUP GAME 
---
# Descrição do Game
O Startup Game é uma simulação em turnos (rodadas) onde cada usuário gerencia uma startup. Em cada
rodada, os jogadores podem tomar até N decisões (configurável) dentre opções como Marketing, Equipe,
Produto, Investidores e Cortar Custos. As decisões impactam Caixa, Receita Base, Reputação e Moral da
startup. Após o número total de rodadas, o jogo calcula um score final e apresenta o ranking




---

# Arquitetura

---
```
PROJETO-REFATORACAO-DO-STARTUP-GAME-main/
├── .devcontainer/
├── .idea/
├── Projeto StartupGame/
│   ├── src/
│   │   ├── actions/                               
│   │   │   ├── CortarCustosStrategy.java
│   │   │   ├── DecisaoFactory.java               (fábrica)
│   │   │   ├── DecisaoStrategy.java              (interface Strategy)
│   │   │   ├── EquipeStrategy.java
│   │   │   ├── InvestidoresStrategy.java
│   │   │   ├── MarketingStrategy.java
│   │   │   └── ProdutoStrategy.java
│   │   ├── config/                               (leitura de game.properties)
│   │   │   └── Config.java
│   │   ├── engine/
│   │   │   ├── GameEngine.java
│   │   │   └── ScoreService.java
│   │   ├── model/
│   │   │   └── vo/
│   │   │   │   ├── Dinheiro.java
│   │   │   │   ├── Humor.java
│   │   │   │   ├── Percentual.java
│   │   │   ├── Deltas.java
│   │   │   ├── Startup.java
│   │   ├── persistencia/
│   │   │   ├── DataSourceProvider.java
│   │   │   ├── DecisaoAplicadaRepository.java
│   │   │   ├── RodadaRepository.java
│   │   │   └── StartupRepository.java
│   │   ├── resources/
│   │   │   └── game.properties
│   │   ├── ui/
│   │   │   ├── ConsoleApp.java
│   │   ├── Main.java                           (iniciia ConsoleApp)
│   ├── Projeto StartupGame.iml
└─ README.md  (como compilar e rodar)        
```

--- 

# 🕹️ 3. Instruções de Jogo

A. Fluxo Básico

O jogo é dividido em rodadas, e em cada rodada, você tem um número limitado de decisões

B. Modos de Decisão (ConsoleApp)

Ao iniciar cada rodada, o ConsoleApp oferece três opções para o modo de jogo:

Opção

Modo

Descrição

[1]

Manual

O jogador escolhe sequencialmente cada uma das ações disponíveis (Marketing, Equipe, Cortar Custos, Investidores) até esgotar o limite de decisões.

[2]

Bot (Automático)

O sistema aciona a BotStrategy para executar automaticamente todas as decisões permitidas, tomando ações baseadas em uma IA simples (incluindo lógica de recuperação de caixa).

[0]

Pular Rodada

Nenhuma decisão é tomada. A rodada é encerrada e a Startup recebe a receita base.

C. Geração de Relatório

Ao final do jogo (após o término da última rodada), o ReportService gera um arquivo CSV contendo o histórico detalhado de todas as ações e estados da Startup. Este arquivo será salvo no diretório raiz de execução com um nome baseado no nome da Startup (ex: historico_TechGrow.csv).