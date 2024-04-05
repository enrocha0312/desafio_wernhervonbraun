CREATE TABLE equipamentos(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    fabricante VARCHAR(60) NOT NULL,
    densidade DECIMAL(5,2),
    comando VARCHAR(30) NOT NULL,
    host VARCHAR(20) NOT NULL,
    porta INT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO equipamentos VALUES(NULL, "nome 1", "fabricante 1", 25.20, "comando 1", "localhost", 8090);
INSERT INTO equipamentos VALUES(NULL, "nome 2", "fabricante 2", 100.15, "comando 2", "localhost", 8091);
INSERT INTO equipamentos VALUES(NULL, "nome 2a", "fabricante 2", 25.25, "comando 2", "localhost", 8092);
INSERT INTO equipamentos VALUES(NULL, "Predict Weather Equipment", "Predict Weater", 11.10, "get_rainfall_intensity", "localhost", 8093);
INSERT INTO equipamentos VALUES(NULL, "Predict Super", "Predict Weater", 0.0, "get_rainfall_intensity", "localhost", 8094);
INSERT INTO equipamentos VALUES(NULL, "nome 1a", "fabricante 1", 5.75, "comando 1", "localhost", 8095);
INSERT INTO equipamentos VALUES(NULL, "nome 3", "fabricante 3", 15.5, "comando 3", "localhost", 8096);
INSERT INTO equipamentos VALUES(NULL, "Equipamento Previsao", "Predict Weater", 10.55, "get_rain", "localhost",8097);
INSERT INTO equipamentos VALUES(NULL, "Equipamento Previsor", "Predict Weater", 5.5, "get_rainfall_intensity", "localhost", 8098);
INSERT INTO equipamentos VALUES(NULL, "nome 3a", "fabricante 3", 140.4, "comando 3", "localhost", 8099);




