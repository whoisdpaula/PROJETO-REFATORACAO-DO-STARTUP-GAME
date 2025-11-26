CREATE TABLE IF NOT EXISTS startup(
    id IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,

    caixa DOUBLE NOT NULL,
    receita_base DOUBLE NOT NULL,

    reputacao INT NOT NULL,
    moral INT NOT NULL,

    rodada_atual INT NOT NULL DEFAULT 1,
    bonus_percent_receita DOUBLE DEFAULT 0
);



CREATE TABLE IF NOT EXISTS rodada(
    id IDENTITY PRIMARY KEY,
    startup_id BIGINT NOT NULL,

    numero INT NOT NULL,

    caixa_antes DOUBLE NOT NULL,
    caixa_depois DOUBLE NOT NULL,
    receita DOUBLE NOT NULL,

    moral INT NOT NULL,
    reputacao INT NOT NULL,

    FOREIGN KEY(startup_id)
        REFERENCES startup(id)
        ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS decisao_aplicada(
    id IDENTITY PRIMARY KEY,
    startup_id BIGINT NOT NULL,

    rodada INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,

    caixa_delta DOUBLE NOT NULL,
    reputacao_delta INT NOT NULL,
    moral_delta INT NOT NULL,
    bonus_delta DOUBLE NOT NULL,

    FOREIGN KEY (startup_id)
        REFERENCES startup(id)
        ON DELETE CASCADE
);
