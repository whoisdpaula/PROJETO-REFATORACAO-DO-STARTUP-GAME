package service;

import model.Startup;
import model.vo.RodadaResumo;
import persistencia.RodadaRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportService {

    private final RodadaRepository rodadaRepository;

    public ReportService(RodadaRepository rodadaRepository) {
        this.rodadaRepository = rodadaRepository;
    }

    public void exportarHistoricoCSV(Startup startup, String nomeArquivo) throws IOException {

        List<RodadaResumo> historico = rodadaRepository.buscarHistorico(startup.getId());

        try (FileWriter writer = new FileWriter(nomeArquivo + ".csv")) {

            writer.append("Rodada,Caixa Antes,Receita Gerada,Caixa Depois,Moral,Reputacao\n");

            for (RodadaResumo resumo : historico) {
                writer.append(String.valueOf(resumo.numeroRodada())).append(",");
                writer.append(String.format("%.2f", resumo.caixaAntes().valor())).append(",");
                writer.append(String.format("%.2f", resumo.receitaGerada().valor())).append(",");
                writer.append(String.format("%.2f", resumo.caixaDepois().valor())).append(",");
                writer.append(String.valueOf(resumo.moral().valor())).append(",");
                writer.append(String.valueOf(resumo.reputacao())).append("\n");
            }

            writer.append("\nRESULTADO FINAL\n");
            writer.append("Nome,Caixa Final,Score Final\n");
            writer.append(startup.getNome()).append(",");
            writer.append(String.format("%.2f", startup.getCaixa().valor())).append(",");
            writer.append(String.format("%.2f", startup.scoreFinal())).append("\n");

        }
    }
}