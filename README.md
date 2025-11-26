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

🕹️ 3. Instruções de Jogo

A. Fluxo Básico

O jogo é dividido em rodadas, e em cada rodada, você tem um número limitado de decisões
