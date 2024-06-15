-- SQLBook: Code
-- DROP-Script
DROP DATABASE IF EXISTS der_grupo_5;

DROP USER 'client'@'%';
FLUSH PRIVILEGES;

-- CREATE-Script
CREATE DATABASE der_grupo_5;

CREATE USER 'client'@'%' identified by 'Client123$';
GRANT SELECT, UPDATE, DELETE, INSERT ON der_grupo_5.* TO 'client';
flush privileges;

USE der_grupo_5;

CREATE TABLE plano (
    idPlano INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    descricao VARCHAR(200) NOT NULL
);

CREATE TABLE empresa (
    idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    cnpj CHAR(18),
    fkPlano INT,
    constraint fkPlano foreign key (fkPlano) references plano(idPlano)
);

CREATE TABLE endereco (
    idEndereco INT PRIMARY KEY AUTO_INCREMENT,
    cep CHAR(9) NOT NULL,
    logradouro VARCHAR(60) NOT NULL,
    numero VARCHAR(4) NOT NULL,
    bairro VARCHAR(40),
    estado VARCHAR(30),
    complemento VARCHAR(45),
    fkEmpresa INT NOT NULL,
        CONSTRAINT fkEmpresaEndereco FOREIGN KEY (fkEmpresa) REFERENCES empresa(idEmpresa)
);

CREATE TABLE usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
	imagemPerfil VARCHAR(100),
    email VARCHAR(60) NOT NULL UNIQUE,
    senha VARCHAR(45) NOT NULL,
    cargo varchar(45),
    fkEmpresa INT NOT NULL, 
        CONSTRAINT fkEmpresaUsuario FOREIGN KEY (fkEmpresa) REFERENCES empresa(idEmpresa) ON DELETE CASCADE
); 

CREATE TABLE contato (
    idContato INT AUTO_INCREMENT,
    telefone CHAR(12) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    fkUsuario INT NOT NULL,
        CONSTRAINT fkUsuarioContato FOREIGN KEY (fkUsuario) REFERENCES usuario(idUsuario) ON DELETE CASCADE,
    PRIMARY KEY (idContato, fkUsuario)
);

CREATE TABLE maquina (
    idMaquina INT PRIMARY KEY AUTO_INCREMENT,
    numeroIdentificacao VARCHAR(60),
    modelo VARCHAR(45),
    marca VARCHAR (45),
    mac VARCHAR(20),
    fkUsuario INT,
        CONSTRAINT fkUsuarioMaquina FOREIGN KEY (fkUsuario) REFERENCES usuario(idUsuario) ON DELETE CASCADE,
	fkEmpresa INT NOT NULL,
		CONSTRAINT fkEmpresaMaquina FOREIGN KEY (fkEmpresa) REFERENCES empresa(idEmpresa) ON DELETE CASCADE
);

CREATE TABLE apps (
    idApp INT PRIMARY KEY AUTO_INCREMENT,
    nomeApp VARCHAR(500) NOT NULL,
    pid INT NOT NULL,
    ramConsumida INT NOT NULL, 
    localidade VARCHAR(500) NOT NULL
);

CREATE TABLE appAcessado (
    fkApp INT NOT NULL,
    CONSTRAINT fkAppMaquina FOREIGN KEY (fkApp) REFERENCES apps(idApp),
    fkMaquina INT NOT NULL,
    CONSTRAINT fkMaquina FOREIGN KEY (fkMaquina) REFERENCES maquina(idMaquina) ON DELETE CASCADE,
    hora TIMESTAMP,
    PRIMARY KEY (fkApp, fkMaquina)
);


CREATE TABLE rede (
    idRede INT PRIMARY KEY AUTO_INCREMENT,
    nomeRede VARCHAR(45) NOT NULL,
    interfaceRede VARCHAR(45) NOT NULL,
    sinalRede INT NOT NULL,
    transmissaoRede DOUBLE NOT NULL,
    bssidRede VARCHAR(20) NOT NULL
);

CREATE TABLE ipv4(
    idIpv4 INT PRIMARY KEY AUTO_INCREMENT,
    numeroIP VARCHAR(18),
    nomeLocal VARCHAR(45),
    fkMaquina INT NOT NULL,
        CONSTRAINT fkMaquinaIPV4 FOREIGN KEY (fkMaquina) REFERENCES maquina(idMaquina) ON DELETE CASCADE
);

CREATE TABLE relRedeIpv4 (
    fkRede INT NOT NULL,
		CONSTRAINT fkRedeRRI FOREIGN KEY (fkRede) REFERENCES rede(idRede),
	fkIpv4 INT NOT NULL,
		CONSTRAINT fkIpv4RRI FOREIGN KEY (fkIpv4) REFERENCES ipv4(idIpv4) ON DELETE CASCADE,
	PRIMARY KEY (fkRede, fkIpv4),
	dataConexao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE componente (
    idComponente INT PRIMARY KEY AUTO_INCREMENT,
    componente VARCHAR(200) NOT NULL,
    modelo VARCHAR(200) NOT NULL,
    fabricante VARCHAR(200) NOT NULL,
    fkMaquina INT NOT NULL,
        CONSTRAINT fkMaquinaComponente FOREIGN KEY (fkMaquina) REFERENCES maquina(idMaquina) ON DELETE CASCADE
);

CREATE TABLE configuracao (
	idConfig INT PRIMARY KEY AUTO_INCREMENT,
    minimoParaSerMedio FLOAT NOT NULL DEFAULT 30,
		CONSTRAINT ckMinimoParaSerMedio CHECK (minimoParaSerMedio < 100 AND minimoParaSerMedio > 0 AND minimoParaSerMedio < minimoParaSerRuim),
	minimoParaSerRuim FLOAT NOT NULL DEFAULT 60,
        CONSTRAINT ckMinimoParaSerRuim CHECK (minimoParaSerRuim < 100 AND minimoParaSerRuim > 0),    
    dataModificacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fkComponente INT NOT NULL,
		CONSTRAINT fkConfigComponente FOREIGN KEY (fkComponente) REFERENCES componente(idComponente) ON DELETE CASCADE
);

CREATE TABLE alerta (
    idAlerta INT PRIMARY KEY AUTO_INCREMENT,
    mensagem VARCHAR(60) NOT NULL,
	tipoAlerta VARCHAR(6) NOT NULL,
		CONSTRAINT ckTipoAlerta CHECK (tipoAlerta IN ('BOM', 'MÉDIO', 'RUIM'))
);

CREATE TABLE captura (
    idCaptura INT PRIMARY KEY AUTO_INCREMENT,
    dadoCaptura DOUBLE NOT NULL,
    unidadeMedida VARCHAR(5) NOT NULL,
    dataCaptura DATETIME NOT NULL,
    dadoCapturaPercent FLOAT NOT NULL,
    fkComponente INT NOT NULL,
        CONSTRAINT fkComponenteCaptura FOREIGN KEY (fkComponente) REFERENCES componente(idComponente) ON DELETE CASCADE
);

CREATE TABLE registroAlerta (
    idRegistroAlertas INT PRIMARY KEY AUTO_INCREMENT,
    horario TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    visualizado BOOL DEFAULT FALSE,
    fkAlerta INT NOT NULL,
    constraint fkRegistroAlertasAlerta foreign key (fkAlerta) references alerta(idAlerta),
    fkCaptura INT NOT NULL,
        CONSTRAINT fkRegistroAlertasCaptura FOREIGN KEY (fkCaptura) REFERENCES captura(idCaptura) ON DELETE CASCADE
);

-- INSERT-Script
INSERT INTO plano VALUES
	(NULL, 'Plano Freelancer', 'Foco: Freelancers (monitora uma máquina). Monitoramento de Hardware: Processador, RAM, disco, conexão USB, placa gráfica'),
	(NULL, 'Plano Empresarial', 'Foco: Pequenas e Médias Empresas (monitora até 100 máquinas). Monitoramento de Hardware: Processador, RAM, disco, conexão USB, placa gráfica.'),
	(NULL, 'Plano Corporativo', 'Foco: Grandes Empresas. Monitoramento de Hardware: Processador, RAM, disco, conexão USB, placa gráfica.');

INSERT INTO empresa (nome, cnpj, fkPlano) VALUES
('VisualOps', '1574201230001', 1),
('Empresa B', '98765432109876', 2),
('Empresa C', '56789012345678', 3);

INSERT INTO endereco (cep, logradouro, numero, fkEmpresa) VALUES
('12345-678', 'Rua A', '123', 1),
('98765-432', 'Rua B', '456', 2),
('56789-012', 'Rua C', '789', 3);

INSERT INTO usuario (nome, email, senha, cargo, fkEmpresa) VALUES
('Cláudio Araújo', 'l_claudio.araujo@hotmail.com', 'senha123', 'Designer', 1),
('Maria Eduarda', 'l_maria.girote@outlook.com', 'senha123', 'Designer', 1),
('Thiago Santos', 'l_thiago.santos@gmail.com', 'senha123', 'Designer', 1),
('Guilherme Neves', 'l_guilherme.neves@hotmail.com', 'senha123', 'Designer', 1),
('Diego Santos', 'l_diego.santos@outlook.com', 'senha123', 'Designer', 1),
('Julia Campioto', 'l_julia.campioto@gmail.com', 'senha123', 'Designer', 1),
('Cláudio Araújo', 'r_claudio.araujo@hotmail.com', 'senha123', 'Designer', 1),
('Maria Eduarda', 'r_maria.girote@outlook.com', 'senha123', 'Designer', 1),
('Thiago Santos', 'r_thiago.santos@gmail.com', 'senha123', 'Designer', 1),
('Guilherme Neves', 'r_guilherme.neves@hotmail.com', 'senha123', 'Designer', 1),
('Diego Santos', 'r_diego.santos@outlook.com', 'senha123', 'Designer', 1),
('Julia Campioto', 'r_julia.campioto@gmail.com', 'senha123', 'Designer', 1),
('Maria Eduarda', 'l_maria.girote@gmail.com', 'senha123', 'Designer', 1),
('VisualOps', 'visualops@outlook.com', 'senha123', 'Gerente', 1);

SELECT * FROM maquina JOIN usuario on maquina.fkUsuario = usuario.idUsuario JOIN ipv4 ON ipv4.fkMaquina = maquina.idMaquina WHERE idUsuario = 3;

SELECT * FROM maquina JOIN usuario on maquina.fkUsuario = usuario.idUsuario WHERE idUsuario = 3;

INSERT INTO maquina (numeroIdentificacao, fkUsuario, fkEmpresa) VALUES 
('929882', 1, 1),
('529272', 2, 1),
('317510', 3, 1),
('492365', 4, 1),
('180878', 5, 1),
('466580', 6, 1),
('380003', 7, 1),
('573671', 8, 1),
('289970', 9, 1),
('328697', 10, 1),
('692571', 11, 1),
('650397', 12, 1),
('325130', 13, 1);

INSERT INTO alerta (mensagem, tipoAlerta) VALUES
('Componente em ótimo estado!', 'BOM'),
('Componente bom porém está sendo comprometido!', 'MÉDIO'),
('Componente está comprometido!!', 'RUIM');

-- IPV4 Faculdade= {
--  duda: 10.18.7.80
--  claudio: 10.18.33.208
--  thiago: 
--  guilherme: 
--  diego: 
--  julia: 
-- }

INSERT INTO ipv4(numeroIP, nomeLocal, fkMaquina) VALUES 
('192.168.15.28', 'Home', 1),
('192.168.0.891', 'Home', 2),
('192.168.15.5', 'Home', 3),
('26.245.80.14', 'Home', 4),
('10.0.0.109', 'Home', 5),
('0.0.0.0', 'Home', 6),
('0.0.0.0', 'Home', 7),
('0.0.0.0', 'Home', 8),
('172.31.61.112', 'Home', 9),
('172.31.55.118', 'Home', 10),
('0.0.0.0', 'Home', 11),
('0.0.0.0', 'Home', 12),
('192.168.0.89', 'Home', 13);


INSERT INTO rede (nomeRede, interfaceRede, sinalRede, transmissaoRede, bssidRede) 
VALUES ('Net SPTech', 'Wi-Fi', 80, 2.4, '00:11:22:33:44:55');

INSERT INTO relRedeIpv4 (fkRede, fkIpv4) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12);
-- SELECT-Script
SHOW TABLES;

SELECT * FROM alerta;

SELECT * FROM captura;

SELECT componente, MAX(cap.dadoCaptura) dado, cap.unidadeMedida uni, COUNT(DAY(cap.dataCaptura)) dia FROM captura cap
	JOIN componente comp ON fkComponente = idComponente
		GROUP BY componente, uni;

SELECT * FROM componente;

SELECT * FROM configuracao;

SELECT * FROM contato;

SELECT * FROM empresa;

SELECT * FROM endereco;

SELECT * FROM ipv4;

SELECT * FROM ipv4 WHERE fkMaquina = 4;

SELECT * FROM maquina;

SELECT * FROM maquina JOIN usuario on maquina.fkUsuario = usuario.idUsuario JOIN ipv4 ON ipv4.fkMaquina = maquina.idMaquina;

SELECT * FROM maquina JOIN usuario on maquina.fkUsuario = usuario.idUsuario WHERE idUsuario = 3;

SELECT * FROM maquina AS mac 
	JOIN componente ON idMaquina = fkMaquina 
		JOIN captura ON idComponente = fkComponente 
			JOIN registroalerta ON idCaptura = fkCaptura 
				ORDER BY fkAlerta;

SELECT idMaquina, fkAlerta, MONTH(dataCaptura) as mes, COUNT(fkAlerta) AS qtdAlerta FROM maquina AS mac 
	JOIN componente ON idMaquina = fkMaquina 
		JOIN captura ON idComponente = fkComponente 
			JOIN registroalerta ON idCaptura = fkCaptura 
				WHERE fkEmpresa = 1 
					GROUP BY idMaquina, fkAlerta, mes;

SELECT idMaquina, componente, fkAlerta, MAX(DAY(dataCaptura)) dia FROM maquina AS mac 
	JOIN componente AS comp ON idMaquina = fkMaquina 
		JOIN captura ON idComponente = fkComponente 
			JOIN registroalerta ON idCaptura = fkCaptura 
				WHERE componente IN ('MemoriaRam', 'CPU', 'GPU', 'HDD') 
					AND fkAlerta > 1 AND fkEmpresa = 1
						GROUP BY idMaquina, componente, fkAlerta;

SELECT idMaquina, componente, fkAlerta, MAX(dataCaptura) maxCap FROM maquina AS mac
	JOIN componente AS comp ON idMaquina = fkMaquina
		JOIN captura AS cap ON idComponente = fkComponente
			JOIN registroalerta ON idCaptura = fkCaptura
				WHERE fkEmpresa = 1
                    AND componente IN ('MemoriaRam', 'CPU', 'GPU', 'HDD') 
						GROUP BY idMaquina, idComponente, fkAlerta
							ORDER BY idMaquina DESC;
                            
SELECT comp.*, MAX(DAY(dataCaptura)) dia FROM maquina mac 
	JOIN componente comp ON idMaquina = fkMaquina
		JOIN captura cap ON idComponente = fkComponente
			WHERE fkEmpresa = 1
				GROUP BY idComponente;
        
SELECT DISTINCT componente FROM maquina mac
	JOIN componente comp ON idMaquina = fkMaquina
		WHERE fkEmpresa = 1;
        
SELECT componente, MAX(cap.dadoCaptura) dado, cap.unidadeMedida uni, COUNT(DAY(cap.dataCaptura)) dia FROM maquina mac
	RIGHT JOIN componente comp ON idMaquina = fkMaquina
		LEFT JOIN captura cap ON idComponente = fkComponente
			WHERE fkEmpresa = 1
				GROUP BY componente, uni;
        
SELECT idMaquina, cap.dadoCaptura, cap.unidadeMedida, componente, MAX(DAY(dataCaptura)) dia FROM maquina mac
	JOIN componente comp ON idMaquina = fkMaquina
		JOIN captura cap ON idComponente = fkComponente
			WHERE fkEmpresa = 1 AND componente = 'HDD'
				GROUP BY idMaquina, cap.dadoCaptura, cap.unidadeMedida;        
        
SELECT cap.dadoCaptura, cap.unidadeMedida, componente, MINUTE(dataCaptura) minuto FROM maquina mac
	JOIN componente comp ON idMaquina = fkMaquina
		JOIN captura cap ON idComponente = fkComponente
			WHERE fkEmpresa = 1 AND componente = 'GPU'
				GROUP BY idMaquina, cap.dadoCaptura, cap.unidadeMedida, minuto
					ORDER BY minuto;
        
SELECT numeroIp IpMaquina, componente, minimoParaSerMedio minMedio, minimoParaSerRuim minRuim, dadoCaptura dado FROM maquina mac 
	JOIN ipv4 ON idMaquina = ipv4.fkMaquina
		JOIN componente comp ON idMaquina = comp.fkMaquina
			JOIN configuracao conf ON fkComponente = idComponente
				JOIN captura cap ON idComponente = cap.fkComponente
					JOIN registroAlerta reg ON idCaptura = fkCaptura
						WHERE idCaptura = 1;

SELECT idMaquina, numeroIP ip, usua.idUsuario, usua.nome userName, usua.imagemPerfil userImg, usua.email userEmail, fkAlerta, MAX(DAY(dataCaptura)) as mes, COUNT(fkAlerta) qtdAlerta FROM maquina mac
	JOIN usuario usua ON fkUsuario = idUsuario
		JOIN ipv4 ip ON idMaquina = ip.fkMaquina
			JOIN componente comp ON idMaquina = comp.fkMaquina
				JOIN captura cap ON idComponente = cap.fKComponente
					JOIN registroAlerta reg ON idCaptura = fkCaptura
						WHERE mac.fKEmpresa = 1
							GROUP BY idMaquina, idIpv4, fKAlerta
								ORDER BY idMaquina;

SELECT idMaquina, numeroIP ip, usua.nome userName, usua.imagemPerfil userImg, usua.email userEmail, tipoAlerta, MAX(MONTH(dataCaptura)) as mes, COUNT(fkAlerta) qtdAlerta FROM maquina mac
	JOIN usuario usua ON fkUsuario = idUsuario
		JOIN ipv4 ip ON idMaquina = ip.fkMaquina
			JOIN componente comp ON idMaquina = comp.fkMaquina
				JOIN captura cap ON idComponente = cap.fKComponente
					JOIN registroAlerta reg ON idCaptura = fkCaptura
						JOIN alerta ON fKAlerta = idAlerta
							WHERE mac.fKEmpresa = 1
								GROUP BY idMaquina, idIpv4, fKAlerta
									ORDER BY idMaquina;

SELECT * FROM plano;

SELECT * FROM rede;

SELECT * FROM registroalerta;

SELECT * FROM relredeipv4;

SELECT * FROM usuario;

SELECT * FROM maquina JOIN usuario ON maquina.fkUsuario = usuario.idUsuario JOIN ipv4 ON ipv4.fkMaquina = maquina.idMaquina WHERE idUsuario = 2;

SELECT * FROM ipv4 JOIN usuario ON fkUsuario = idUsuario WHERE fkEmpresa = 1;

SELECT * FROM maquina JOIN ipv4 ON idMaquina = fkMaquina;

SELECT * FROM ipv4 WHERE numeroIP = '192.168.15.6' AND fkMaquina = '1';

SELECT * FROM rede WHERE bssidRede = 'e6:54:20:c3:b5:2e';

SELECT COUNT(*) from maquina as m
	JOIN componente as c ON m.fkComponente = c.idComponente WHERE fkUsuario = 1;

SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_DEFAULT, IS_NULLABLE, COLUMN_KEY, EXTRA, COLUMN_COMMENT FROM information_schema.columns WHERE TABLE_SCHEMA = 'der_grupo_5' AND TABLE_NAME = "alertas";

SELECT * from captura join componente on idComponente = fkComponente;

SELECT componente, cap.unidadeMedida uni, COUNT(DAY(cap.dataCaptura)) dia FROM maquina mac
	RIGHT JOIN componente comp ON idMaquina = fkMaquina
		LEFT JOIN captura cap ON idComponente = fkComponente
            WHERE fkEmpresa = 1
		    	GROUP BY componente, uni;

