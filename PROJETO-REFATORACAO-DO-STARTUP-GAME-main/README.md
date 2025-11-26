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
│   │   ├── java.actions/                               
│   │   │   ├── CortarCustosStrategy.java
│   │   │   ├── DecisaoFactory.java               (fábrica)
│   │   │   ├── DecisaoStrategy.java              (interface Strategy)
│   │   │   ├── EquipeStrategy.java
│   │   │   ├── InvestidoresStrategy.java
│   │   │   ├── MarketingStrategy.java
│   │   │   └── ProdutoStrategy.java
│   │   ├── java.config/                               (leitura de game.properties)
│   │   │   └── Config.java
│   │   ├── java.engine/
│   │   │   ├── GameEngine.java
│   │   │   └── ScoreService.java
│   │   ├── java.model/
│   │   │   └── vo/
│   │   │   │   ├── Dinheiro.java
│   │   │   │   ├── Humor.java
│   │   │   │   ├── Percentual.java
│   │   │   ├── Deltas.java
│   │   │   ├── Startup.java
│   │   ├── java.java.persistencia/
│   │   │   ├── DataSourceProvider.java
│   │   │   ├── DecisaoAplicadaRepository.java
│   │   │   ├── RodadaRepository.java
│   │   │   └── StartupRepository.java
│   │   ├── resources/
│   │   │   └── game.properties
│   │   ├── java.ui/
│   │   │   ├── ConsoleApp.java
│   │   ├── Main.java                           (iniciia ConsoleApp)
│   ├── Projeto StartupGame.iml
└─ README.md  (como compilar e rodar)        
```
