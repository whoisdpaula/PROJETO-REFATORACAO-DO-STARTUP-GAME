
import config.Config;
import engine.GameEngine;
import persistencia.StartupRepository;
import persistencia.DecisaoAplicadaRepository;
import persistencia.RodadaRepository;
import service.ReportService;
import ui.ConsoleApp;


public class Main {
    public static void main(String[] args) {


        StartupRepository startupRepo = new StartupRepository();
        DecisaoAplicadaRepository decisaoAplicadaRepo = new DecisaoAplicadaRepository();
        RodadaRepository rodadaRepo = new RodadaRepository();


        ReportService reportService = new ReportService(rodadaRepo);
        Config config = new Config();


        GameEngine engine = new GameEngine(
                startupRepo,
                decisaoAplicadaRepo,
                rodadaRepo
        );

        ConsoleApp app = new ConsoleApp(
                config,
                engine,
                startupRepo,
                reportService
        );

        app.start();
    }
}