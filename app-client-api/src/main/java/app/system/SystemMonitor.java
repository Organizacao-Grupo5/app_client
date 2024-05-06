package app.system;

import app.integration.ShellIntegration;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.mysql.cj.util.StringUtils;
import model.componentes.*;
import oshi.SystemInfo;
import util.logs.Logger;
import oshi.hardware.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SystemMonitor {
	private final Looca looca = new Looca();
	private final Conversor conversor = new Conversor();
	private final HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
	private final ShellIntegration shellIntegration = new ShellIntegration();

	public CPU monitorarCPU() {
		Logger.logInfo("Capturando dados da sua CPU.");
		CPU cpu = new CPU();
		try {
			Processador processor = looca.getProcessador();
			cpu.setNumeroDeCpusLogicas(processor.getNumeroCpusLogicas());
			if (cpu.getNumeroDeCpusLogicas() == null) {
				Logger.logWarning("Número de CPUs lógicas não foi capturado.");
			} else {
				Logger.logInfo("Número de CPUs lógicas: " + cpu.getNumeroDeCpusLogicas());
			}
			cpu.setNumeroDeCpusFisicas(processor.getNumeroCpusFisicas());
			if (cpu.getNumeroDeCpusFisicas() == null) {
				Logger.logWarning("Número de CPUs físicas não foi capturado.");
			} else {
				Logger.logInfo("Número de CPUs físicas: " + cpu.getNumeroDeCpusFisicas());
			}
			cpu.setMicroarquitetura(processor.getMicroarquitetura());
			if (cpu.getMicroarquitetura() == null || cpu.getMicroarquitetura().isEmpty()) {
				Logger.logWarning("Microarquitetura não foi capturada.");
			} else {
				Logger.logInfo("Microarquitetura: " + cpu.getMicroarquitetura());
			}
			cpu.setIdentificador(processor.getIdentificador());
			if (cpu.getIdentificador() == null || cpu.getIdentificador().isEmpty()) {
				Logger.logWarning("Identificador não foi capturado.");
			} else {
				Logger.logInfo("Identificador: " + cpu.getIdentificador());
			}
			cpu.setIdCpuLooca(processor.getId());
			if (cpu.getIdCpuLooca() == null || cpu.getIdCpuLooca().isEmpty()) {
				Logger.logWarning("ID da CPU Looca não foi capturado.");
			} else {
				Logger.logInfo("ID da CPU Looca: " + cpu.getIdCpuLooca());
			}
			cpu.setFabricante(processor.getFabricante());
			if (cpu.getFabricante() == null || cpu.getFabricante().isEmpty()) {
				Logger.logWarning("Fabricante não foi capturado.");
			} else {
				Logger.logInfo("Fabricante: " + cpu.getFabricante());
			}
			cpu.setFrequencia(Double.valueOf(processor.getFrequencia()));
			if (cpu.getFrequencia() == null) {
				Logger.logWarning("Frequência não foi capturada.");
			} else {
				Logger.logInfo("Frequência: " + cpu.getFrequencia());
			}
			cpu.setNumeroPacotesFisicos(processor.getNumeroPacotesFisicos());
			if (cpu.getNumeroPacotesFisicos() == null) {
				Logger.logWarning("Número de pacotes físicos não foi capturado.");
			} else {
				Logger.logInfo("Número de pacotes físicos: " + cpu.getNumeroPacotesFisicos());
			}
			cpu.setUso(processor.getUso());
			if (cpu.getUso() == null) {
				Logger.logWarning("Uso da CPU não foi capturado.");
			} else {
				Logger.logInfo("Uso da CPU: " + cpu.getUso());
			}
			cpu.setModelo(processor.getNome());
			if (cpu.getModelo() == null || cpu.getModelo().isEmpty()) {
				Logger.logWarning("Nome da CPU não foi capturado.");
			} else {
				Logger.logInfo("Nome da CPU: " + cpu.getModelo());
			}
			Double temperatura = shellIntegration.monitorarTemperatura();
			cpu.setTemperatura(temperatura);
			if (cpu.getTemperatura() == null) {
				Logger.logWarning("Temperatura da CPU não foi capturada com sucesso, será apresentada como 0.00 .");
				cpu.setTemperatura(0.00);
			} else {
				Logger.logInfo("Temperatura da CPU: " + cpu.getTemperatura());
			}
			Logger.logInfo("Dados da CPU capturados com sucesso.");
		} catch (Exception e) {
			Logger.logError("Erro ao capturar dados da CPU: ", e.getMessage(), e);
		}
		return cpu;
	}

	public List<APP> monitorarDisplay() {
		Logger.logInfo("Capturando dados dos seus Apps.");
		List<APP> apps = new ArrayList<>();
		try {
			looca.getGrupoDeJanelas().getJanelas().forEach(janela -> {
				APP app = new APP();
				try {
					app.setNome(janela.getTitulo());
					if (StringUtils.isNullOrEmpty(app.getNome())) {
						Logger.logWarning("Título da janela não foi capturado.");
					} else {
						Logger.logInfo("Título da janela: " + app.getNome());
					}
					app.setComando(janela.getComando());
					if (StringUtils.isNullOrEmpty(app.getComando())) {
						Logger.logWarning("Comando da janela não foi capturado.");
					} else {
						Logger.logInfo("Comando da janela: " + app.getComando());
					}
					app.setFabricante(janela.getJanelaId().toString());
					if (StringUtils.isNullOrEmpty(app.getFabricante())) {
						Logger.logWarning("ID da janela não foi capturado.");
					} else {
						Logger.logInfo("ID da janela: " + app.getFabricante());
					}
					app.setModelo(janela.getPid().toString());
					if (StringUtils.isNullOrEmpty(app.getModelo())) {
						Logger.logWarning("PID da janela não foi capturado.");
					} else {
						Logger.logInfo("PID da janela: " + Optional.ofNullable(app.getModelo()).orElse("PID não encontrado."));
					}
					app.setLocalizacaoEtamanho(janela.getLocalizacaoETamanho());
					if (app.getLocalizacaoEtamanho() == null) {
						Logger.logWarning("Localização e tamanho da janela não foram capturados.");
					} else {
						Logger.logInfo("Localização e tamanho da janela: " + app.getLocalizacaoEtamanho());
					}
					app.setDadoCaptura();
					if (app.getDadoCaptura() == null){
						Logger.logWarning("O uso da RAM pela janela %s não foi capturado.".formatted(app.getModelo()));
					}else {
						Logger.logInfo("Uso da ram pela janela: " + app.getDadoCaptura());
					}
					Logger.logInfo("Dados da janela gravados.");
				} catch (Exception e) {
					Logger.logError("Erro ao processar janela: ", e.getMessage(), e);
				}
				apps.add(app);
			});
			Logger.logInfo("Todas as janelas verificadas.");
		} catch (Exception e) {
			Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}
		return apps;
	}

	public List<HDD> monitorarHDD() {
		Logger.logInfo("Capturando dados do seu HDD");
		List<HDD> hdds = new ArrayList<>();
		try {
			looca.getGrupoDeDiscos().getDiscos().forEach(disco -> {
				try {
					HDD hdd = new HDD();
					hdd.setNome(disco.getNome());
					if (StringUtils.isNullOrEmpty(hdd.getNome())) {
						Logger.logWarning("Nome do disco não foi capturado.");
					} else {
						Logger.logInfo("Nome do disco: " + hdd.getNome());
					}

					hdd.setSerial(disco.getSerial());
					if (StringUtils.isNullOrEmpty(hdd.getSerial())) {
						Logger.logWarning("Serial do disco não foi capturado.");
					} else {
						Logger.logInfo("Serial do disco: " + hdd.getSerial());
					}

					hdd.setModelo(disco.getModelo());
					if (StringUtils.isNullOrEmpty(hdd.getModelo())) {
						Logger.logWarning("Modelo do disco não foi capturado.");
					} else {
						Logger.logInfo("Modelo do disco: " + hdd.getModelo());
					}

					hdd.setEscritas(conversor.formatarBytes(disco.getEscritas()));
					if (StringUtils.isNullOrEmpty(hdd.getEscritas().toString())) {
						Logger.logWarning("Escritas do disco não foram capturadas.");
					} else {
						Logger.logInfo("Escritas do disco: " + hdd.getEscritas());
					}

					hdd.setLeituras(conversor.formatarBytes(disco.getLeituras()));
					if (StringUtils.isNullOrEmpty(hdd.getLeituras().toString())) {
						Logger.logWarning("Leituras do disco não foram capturadas.");
					} else {
						Logger.logInfo("Leituras do disco: " + hdd.getLeituras());
					}

					hdd.setBytesDeEscrita(conversor.formatarBytes(disco.getBytesDeEscritas()));
					if (StringUtils.isNullOrEmpty(hdd.getBytesDeEscrita().toString())) {
						Logger.logWarning("Bytes de escritas do disco não foram capturados.");
					} else {
						Logger.logInfo("Bytes de escritas do disco: " + hdd.getBytesDeEscrita());
					}

					hdd.setBytesDeLeitura(conversor.formatarBytes(disco.getBytesDeLeitura()));
					if (StringUtils.isNullOrEmpty(hdd.getBytesDeLeitura().toString())) {
						Logger.logWarning("Bytes de leitura do disco não foram capturados.");
					} else {
						Logger.logInfo("Bytes de leitura do disco: " + hdd.getBytesDeLeitura());
					}

					hdd.setTamanho(conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanho()), 2));
					if (hdd.getTamanho() == null) {
						Logger.logWarning("Tamanho do disco não foi capturado.");
					} else {
						Logger.logInfo("Tamanho do disco: " + hdd.getTamanho());
					}

					hdd.setTempoDeTransferencia(conversor.converterSegundosParaHoras(disco.getTempoDeTransferencia()));
					if (hdd.getTempoDeTransferencia() == null) {
						Logger.logWarning("Tempo de transferência do disco não foi capturado.");
					} else {
						Logger.logInfo("Tempo de transferência do disco: " + hdd.getTempoDeTransferencia());
					}

					hdds.add(hdd);
					Logger.logInfo("Dados do disco gravados.");
				} catch (Exception e) {
					Logger.logError("Erro ao processar dados do HDD: ", e.getMessage(), e);
				}
			});
		} catch (Exception e) {
			Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}
		return hdds;
	}

	public List<Bateria> monitorarBateria() {
		Logger.logInfo("Capturando dados da sua bateria.");
		List<Bateria> baterias = new ArrayList<>();
		try {
			hardware.getPowerSources().forEach(power -> {
				try {
					Bateria bateria = new Bateria();

					bateria.setAmperagem(power.getAmperage());
					if (bateria.getAmperagem() == null) {
						Logger.logWarning("Amperagem da bateria não foi capturada.");
					} else {
						Logger.logInfo("Amperagem da bateria: " + bateria.getAmperagem());
					}

					bateria.setNomeDispositivo(power.getDeviceName());
					if (StringUtils.isNullOrEmpty(bateria.getNomeDispositivo())) {
						Logger.logWarning("Nome do dispositivo da bateria não foi capturado.");
					} else {
						Logger.logInfo("Nome do dispositivo da bateria: " + bateria.getNomeDispositivo());
					}

					bateria.setNumeroSerial(power.getSerialNumber());
					if (StringUtils.isNullOrEmpty(bateria.getNumeroSerial())) {
						Logger.logWarning("Número de série da bateria não foi capturado.");
					} else {
						Logger.logInfo("Número de série da bateria: " + bateria.getNumeroSerial());
					}

					bateria.setQuimica(power.getChemistry());
					if (StringUtils.isNullOrEmpty(bateria.getQuimica())) {
						Logger.logWarning("Química da bateria não foi capturada.");
					} else {
						Logger.logInfo("Química da bateria: " + bateria.getQuimica());
					}

					bateria.setModelo(power.getName());
					if (StringUtils.isNullOrEmpty(bateria.getModelo())) {
						Logger.logWarning("Nome da bateria não foi capturado.");
					} else {
						Logger.logInfo("Nome da bateria: " + bateria.getModelo());
					}

					bateria.setVoltagem(power.getVoltage());
					if (bateria.getVoltagem() == null) {
						Logger.logWarning("Voltagem da bateria não foi capturada.");
					} else {
						Logger.logInfo("Voltagem da bateria: " + bateria.getVoltagem());
					}

					bateria.setCapacidadeAtual(conversor.formatarBytes(power.getCurrentCapacity()));
					if (StringUtils.isNullOrEmpty(bateria.getCapacidadeAtual().toString())) {
						Logger.logWarning("Capacidade atual da bateria não foi capturada.");
					} else {
						Logger.logInfo("Capacidade atual da bateria: " + bateria.getCapacidadeAtual());
					}

					bateria.setCiclos(power.getCycleCount());
					if (bateria.getCiclos() == null) {
						Logger.logWarning("Número de ciclos da bateria não foi capturado.");
					} else {
						Logger.logInfo("Número de ciclos da bateria: " + bateria.getCiclos());
					}

					bateria.setCapacidadeDesign(conversor.formatarBytes(power.getDesignCapacity()));
					if (StringUtils.isNullOrEmpty(bateria.getCapacidadeDesign().toString())) {
						Logger.logWarning("Capacidade de design da bateria não foi capturada.");
					} else {
						Logger.logInfo("Capacidade de design da bateria: " + bateria.getCapacidadeDesign());
					}

					bateria.setUnidadesCapacidade(power.getCapacityUnits().toString());
					if (StringUtils.isNullOrEmpty(bateria.getUnidadesCapacidade())) {
						Logger.logWarning("Unidade de capacidade da bateria não foi capturada.");
					} else {
						Logger.logInfo("Unidade de capacidade da bateria: " + bateria.getUnidadesCapacidade());
					}

					bateria.setTempoRestanteInstantaneo(power.getTimeRemainingInstant());
					if (bateria.getTempoRestanteInstantaneo() == null) {
						Logger.logWarning("Tempo restante da bateria (instantâneo) não foi capturado.");
					} else {
						Logger.logInfo(
								"Tempo restante da bateria (instantâneo): " + bateria.getTempoRestanteInstantaneo());
					}

					bateria.setTempoRestanteEstimado(power.getTimeRemainingEstimated());
					if (bateria.getTempoRestanteEstimado() == null) {
						Logger.logWarning("Tempo restante estimado da bateria não foi capturado.");
					} else {
						Logger.logInfo("Tempo restante estimado da bateria: " + bateria.getTempoRestanteEstimado());
					}

					bateria.setTaxaUsoEnergia(power.getPowerUsageRate());
					if (bateria.getTaxaUsoEnergia() == null) {
						Logger.logWarning("Taxa de uso de energia da bateria não foi capturada.");
					} else {
						Logger.logInfo("Taxa de uso de energia da bateria: " + bateria.getTaxaUsoEnergia());
					}

					bateria.setTemperatura(power.getTemperature());
					if (bateria.getTemperatura() == null) {
						Logger.logWarning("Temperatura da bateria não foi capturada.");
					} else {
						Logger.logInfo("Temperatura da bateria: " + bateria.getTemperatura());
					}

					bateria.setCapacidadeMaxima(conversor.formatarBytes(power.getMaxCapacity()));
					if (StringUtils.isNullOrEmpty(bateria.getCapacidadeMaxima().toString())) {
						Logger.logWarning("Capacidade máxima da bateria não foi capturada.");
					} else {
						Logger.logInfo("Capacidade máxima da bateria: " + bateria.getCapacidadeMaxima());
					}

					bateria.setPercentualCapacidadeRestante(conversor.convertePorcentagem(power.getMaxCapacity(),
							power.getRemainingCapacityPercent() * power.getMaxCapacity() / 100));
					if (bateria.getPercentualCapacidadeRestante() == null) {
						Logger.logWarning("Porcentagem de capacidade restante da bateria não foi capturada.");
					} else {
						Logger.logInfo("Porcentagem de capacidade restante da bateria: "
								+ bateria.getPercentualCapacidadeRestante());
					}

					String manufactureDateStr = power.getManufactureDate() != null
							? power.getManufactureDate().toString()
							: "N/A";
					bateria.setDataFabricacao(manufactureDateStr);
					if (StringUtils.isNullOrEmpty(bateria.getDataFabricacao())) {
						Logger.logWarning("Data de fabricação da bateria não foi capturada.");
					} else {
						Logger.logInfo("Data de fabricação da bateria: " + bateria.getDataFabricacao());
					}

					bateria.setFabricante(power.getManufacturer());
					if (StringUtils.isNullOrEmpty(bateria.getFabricante())) {
						Logger.logWarning("Fabricante da bateria não foi capturado.");
					} else {
						Logger.logInfo("Fabricante da bateria: " + bateria.getFabricante());
					}

					bateria.setBateriaAtual(shellIntegration.monitorarBateria());
					if (bateria.getBateriaAtual() == null) {
						Logger.logWarning("Dados da bateria atual não foram capturados, será apresentada como 0.");
						bateria.setBateriaAtual(0.0);
					} else {
						Logger.logInfo("Dados da bateria capturados com sucesso: " + bateria.getBateriaAtual());
					}

					baterias.add(bateria);
					Logger.logInfo("Dados da bateria gravados.");
				} catch (Exception e) {
					Logger.logError("Erro ao processar dados da bateria: ", e.getMessage(), e);
				}
			});
		} catch (Exception e) {
			Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}
		return baterias;
	}

	public List<GPU> monitorarGPU() {
		Logger.logInfo("Capturando dados da GPU.");
		List<GPU> gpus = new ArrayList<>();
		List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();
		try {
			if (graphicsCards == null || graphicsCards.isEmpty()) {
				Logger.logWarning("Dados da GPU não puderam ser capturados: nenhuma placa gráfica encontrada.");
			} else {
				graphicsCards.forEach(gpu -> {
					GPU myGpu = new GPU();
					try {

						myGpu.setFabricante(gpu.getVendor());
						if (StringUtils.isNullOrEmpty(myGpu.getFabricante())) {
							Logger.logWarning("Não foi possível capturar informações sobre o fabricante da sua GPU");
						} else {
							Logger.logInfo(
									"Dados do fabricante da sua GPU capturados com sucesso: " + myGpu.getFabricante());
						}

						myGpu.setIdDevice(gpu.getDeviceId());
						if (StringUtils.isNullOrEmpty(myGpu.getIdDevice())) {
							Logger.logWarning("Não foi possível capturar informações sobre o ID do dispositivo");
						} else {
							Logger.logInfo("Dados do ID da sua GPU capturados com sucesso: " + myGpu.getIdDevice());
						}

						myGpu.setModelo(gpu.getName());
						if (StringUtils.isNullOrEmpty(myGpu.getModelo())) {
							Logger.logWarning("Não foi possível capturar informações sobre o nome da GPU");
						} else {
							Logger.logInfo("O nome da sua GPU foi capturado com sucesso: " + myGpu.getModelo());
						}

						myGpu.setvRam(conversor.formatarBytes(gpu.getVRam()));
						if (StringUtils.isNullOrEmpty(myGpu.getvRam().toString())) {
							Logger.logWarning("Não foi possível capturar informações sobre a VRam da sua GPU");
						} else {
							Logger.logInfo("A VRam da sua GPU foi capturada com sucesso: " + myGpu.getvRam());
						}

						myGpu.setVersao(gpu.getVersionInfo());
						if (StringUtils.isNullOrEmpty(myGpu.getVersao())) {
							Logger.logWarning("Não foi possível capturar informações sobre a versão da sua GPU");
						} else {
							Logger.logInfo("A Versão da sua GPU foi capturada com sucesso: " + myGpu.getVersao());
						}

						myGpu.setTemperatura(shellIntegration.monitorarTemperatura());
						if (myGpu.getTemperatura() == null) {
							Logger.logWarning(
									"Temperatura da GPU não foi capturada com sucesso, será apresentada como 0.00 .");
							myGpu.setTemperatura(0.00);
						} else {
							Logger.logInfo("Temperatura da GPU: " + myGpu.getTemperatura());
						}
					} catch (Exception e) {
						Logger.logWarning("Algo inesperado aconteceu ao tentar acessar alguma informação da sua GPU: "
								+ e.getMessage());
					}
					gpus.add(myGpu);
				});
				Logger.logInfo("Dados da GPU gravados.");
			}
		} catch (Exception e) {
			Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}
		return gpus;
	}

	public MemoriaRam monitorarRAM() {
		Logger.logInfo("Capturando dados da sua memória RAM.");
		Memoria memoria = looca.getMemoria();
		if (memoria == null) {
			Logger.logWarning("Dados da memória RAM não puderam ser capturados: memória é nula.");
			return null;
		}
		MemoriaRam memoriaRam = new MemoriaRam();
		try {
			memoriaRam.setMemoriaDisponivel(conversor.formatarBytes(memoria.getDisponivel()));
			if (memoriaRam.getMemoriaDisponivel() == null || memoriaRam.getMemoriaDisponivel().toString().isEmpty()) {
				Logger.logWarning("Memória disponível não foi capturada.");
			} else {
				Logger.logInfo("Memória disponível: " + memoriaRam.getMemoriaDisponivel());
			}

			memoriaRam.setMemoriaTotal(conversor.formatarBytes(memoria.getTotal()));
			if (memoriaRam.getMemoriaTotal() == null || memoriaRam.getMemoriaTotal().toString().isEmpty()) {
				Logger.logWarning("Memória total não foi capturada.");
			} else {
				Logger.logInfo("Memória total: " + memoriaRam.getMemoriaTotal());
			}

			memoriaRam.setMemoriaEmUso(conversor.formatarBytes(memoria.getEmUso()));
			if (memoriaRam.getMemoriaEmUso() == null || memoriaRam.getMemoriaEmUso().toString().isEmpty()) {
				Logger.logWarning("Memória em uso não foi capturada.");
			} else {
				Logger.logInfo("Memória em uso: " + memoriaRam.getMemoriaEmUso());
			}

			Logger.logInfo("Dados da memória RAM capturados com sucesso.");
		} catch (Exception e) {
			Logger.logError("Erro ao capturar dados da memória RAM: ", e.getMessage(), e);
		}
		return memoriaRam;
	}

	public SistemaOp monitorarSistemaOperacional() {
		Logger.logInfo("Capturando dados do sistema operacional.");
		Sistema sistema = looca.getSistema();
		SistemaOp sistemaOp = new SistemaOp();
		if (sistema == null) {
			Logger.logWarning("Dados do sistema operacional não puderam ser capturados: sistema é nulo.");
		}
		try {

			sistemaOp.setModelo(sistema.getSistemaOperacional());
			if (StringUtils.isNullOrEmpty(sistemaOp.getModelo())) {
				Logger.logWarning("Não foi possível identificar o nome do sistema operacional");
			} else {
				Logger.logInfo("Nome do sistema operacional coletado: " + sistemaOp.getModelo());
			}

			sistemaOp.setFabricante(sistema.getFabricante());
			if (StringUtils.isNullOrEmpty(sistemaOp.getFabricante())) {
				Logger.logWarning("Não foi possível identificar o nome do fabricante do sistema operacional");
			} else {
				Logger.logInfo("Nome do fabricante do sistema operacional coletado: " + sistemaOp.getFabricante());
			}

			sistemaOp.setArquitetura(sistema.getArquitetura().toString());
			if (StringUtils.isNullOrEmpty(sistemaOp.getArquitetura())) {
				Logger.logWarning("Não foi possível identificar a arquitetura do sistema operacional");
			} else {
				Logger.logInfo("Arquitetura do sistema operacional coletada: " + sistemaOp.getArquitetura());
			}

			sistemaOp.setInicializado(sistema.getInicializado().toString());
			if (StringUtils.isNullOrEmpty(sistemaOp.getInicializado())) {
				Logger.logWarning("Não foi possível identificar a inicialização do sistema operacional");
			} else {
				Logger.logInfo("A inicialização do sistema operacional coletada: " + sistemaOp.getArquitetura());
			}

			sistemaOp.setTempoDeAtividade(sistema.getTempoDeAtividade().toString());
			if (StringUtils.isNullOrEmpty(sistemaOp.getTempoDeAtividade())) {
				Logger.logWarning("Não foi possível identificar o tempo de atividade do sistema operacional");
			} else {
				Logger.logInfo(
						"O tempo de atividade do sistema operacional foi coletada: " + sistemaOp.getTempoDeAtividade());
			}

			sistemaOp.setPermissao(sistema.getPermissao() ? "SIM" : "NÃO");
			if (StringUtils.isNullOrEmpty(sistemaOp.getPermissao())) {
				Logger.logWarning("Não foi possível identificar a permissão do sistema operacional");
			} else {
				Logger.logInfo("A permisão do sistema operacional foi coletada: " + sistemaOp.getTempoDeAtividade());
			}

		} catch (Exception e) {
			Logger.logError("Erro ao capturar dados do seu Sistema Operacional: ", e.getMessage(), e);
		}
		return sistemaOp;
	}

	public List<ConexaoUSB> monitorarUSB() {
		Logger.logInfo("Capturando dados das suas conexões USB.");
		List<ConexaoUSB> usbs = new ArrayList<>();
		try {
			looca.getDispositivosUsbGrupo().getDispositivosUsbConectados().forEach(usb -> {
				try {
					ConexaoUSB conexaoUSB = new ConexaoUSB();
					conexaoUSB.setModelo(usb.getNome());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getModelo())) {
						Logger.logWarning("Nome do usb não foi capturado.");
					} else {
						Logger.logInfo("Nome do usb capturado: " + conexaoUSB.getModelo());
					}

					conexaoUSB.setModelo(usb.getForncecedor());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getModelo())) {
						Logger.logWarning("Fornecedor do usb não foi capturado.");
					} else {
						Logger.logInfo("Nome do fornecedor do usb capturado: " + conexaoUSB.getModelo());
					}

					conexaoUSB.setIdDispositivoUSBExclusivo(usb.getIdDispositivoUsbExclusivo());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getIdDispositivoUSBExclusivo())) {
						Logger.logWarning("Id exclusivo do dispositivo usb não foi capturado.");
					} else {
						Logger.logInfo("Id exclusivo do usb capturado: " + conexaoUSB.getIdDispositivoUSBExclusivo());
					}

					conexaoUSB.setIdFornecedor(usb.getIdFornecedor());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getIdFornecedor())) {
						Logger.logWarning("Id do fornecedor do dispositivo usb não foi capturado.");
					} else {
						Logger.logInfo("Id do fornecedor do usb capturado: " + conexaoUSB.getIdFornecedor());
					}

					conexaoUSB.setIdProduto(usb.getIdProduto());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getIdProduto())) {
						Logger.logWarning("Id do produto não foi capturado.");
					} else {
						Logger.logInfo("Id do produto do usb capturado: " + conexaoUSB.getIdProduto());
					}

					conexaoUSB.setNumeroSerie(usb.getNumeroDeSerie());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getNumeroSerie())) {
						Logger.logWarning("Número série do usb não foi capturado.");
					} else {
						Logger.logInfo("Número série do usb capturado: " + conexaoUSB.getNumeroSerie());
					}

					usbs.add(conexaoUSB);
					Logger.logInfo("Dados do usb gravados.");
				} catch (Exception e) {
					Logger.logError("Erro ao processar dados do usb: ", e.getMessage(), e);
				}
			});
			Logger.logInfo("Todos os usbs verificadas.");
		} catch (Exception e) {
			Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}
		return usbs;
	}

	public List<Volume> monitorarVolumeLogico() {
		Logger.logInfo("Capturando dados dos volumes lógicos.");

		List<Volume> volumes = new ArrayList<>();

		try {
			looca.getGrupoDeDiscos().getVolumes().stream().forEach(volume -> {
				Volume volumeLogico = new Volume();
				try {

					volumeLogico.setModelo(volume.getNome());
					if (StringUtils.isNullOrEmpty(volumeLogico.getModelo())) {
						Logger.logWarning("Nome do volume lógico não foi capturado.");
					} else {
						Logger.logInfo("Nome do volume lógico capturado: " + volumeLogico.getModelo());
					}

					volumeLogico.setVolume(volume.getVolume());
					if (StringUtils.isNullOrEmpty(volumeLogico.getVolume())) {
						Logger.logWarning("Nome do volume não foi capturado.");
					} else {
						Logger.logInfo("Nome do volume capturado: " + volumeLogico.getVolume());
					}

					volumeLogico.setDisponivel(conversor.formatarBytes(volume.getDisponivel()));
					if (StringUtils.isNullOrEmpty(volumeLogico.getDisponivel().toString())) {
						Logger.logWarning("Espaço disponível no volume lógico não foi capturado.");
					} else {
						Logger.logInfo(
								"Espaço disponível no volume lógico capturado : " + volumeLogico.getDisponivel());
					}

					volumeLogico.setDadoCaptura(conversor.formatarBytes(volume.getDisponivel()) - conversor.formatarBytes(volume.getTotal()));

					volumeLogico.setTotal(conversor.formatarBytes(volume.getTotal()));
					if (StringUtils.isNullOrEmpty(volumeLogico.getTotal().toString())) {
						Logger.logWarning("Espaço total do volume lógico não foi capturado.");
					} else {
						Logger.logInfo(
								"Espaço total do volume lógico capturado com sucesso: " + volumeLogico.getTotal());
					}

					volumeLogico.setTipo(volume.getTipo());
					if (StringUtils.isNullOrEmpty(volumeLogico.getTipo())) {
						Logger.logWarning("Tipo do volume lógico não foi capturado.");
					} else {
						Logger.logInfo("Tipo do volume lógico capturado: " + volumeLogico.getTipo());
					}

					volumeLogico.setFabricante(volume.getUUID());
					if (StringUtils.isNullOrEmpty(volumeLogico.getFabricante())) {
						Logger.logWarning("UUID do volume lógico não foi capturado.");
					} else {
						Logger.logInfo("UUID do volume lógico capturado: " + volumeLogico.getFabricante());
					}

					volumeLogico.setPontoDeMontagem(volume.getPontoDeMontagem());
					if (StringUtils.isNullOrEmpty(volumeLogico.getPontoDeMontagem())) {
						Logger.logWarning("Ponto de montagem do volume lógico não foi capturado.");
					} else {
						Logger.logInfo(
								"Ponto de montagem do volume lógico capturado: " + volumeLogico.getPontoDeMontagem());
					}

					Logger.logInfo("Dados do volume lógico gravados.");
				} catch (Exception e) {
					Logger.logError("Erro ao processar dados do volume lógico: ", e.getMessage(), e);
				}
				volumes.add(volumeLogico);
			});
			Logger.logInfo("Todos os volumes lógicos verificados.");
		} catch (Exception e) {
			Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}

		return volumes;
	}

	public PlacaMae capturarInformacoesPlacaMae() {
		SystemInfo systemInfo = new SystemInfo();
		HardwareAbstractionLayer hardware = systemInfo.getHardware();
		ComputerSystem computerSystem = hardware.getComputerSystem();

		String fabricantePlacaMae = computerSystem.getManufacturer();
		String modeloPlacaMae = computerSystem.getModel();

		try {
			if (fabricantePlacaMae == null || fabricantePlacaMae.isEmpty()) {
				Logger.logWarning("Fabricante da placa mãe não foi capturado.");
			} else {
				Logger.logInfo("Fabricante da placa mãe capturado: " + fabricantePlacaMae);
			}

			if (modeloPlacaMae == null || modeloPlacaMae.isEmpty()) {
				Logger.logWarning("Modelo da placa mãe não foi capturado.");
			} else {
				Logger.logInfo("Modelo da placa mãe capturado: " + modeloPlacaMae);
			}

			PlacaMae placaMae = new PlacaMae();
			placaMae.setFabricante(fabricantePlacaMae);
			placaMae.setModelo(modeloPlacaMae);
			Logger.logInfo("Dados da placa mãe gravados.");
			return placaMae;
		} catch (IllegalArgumentException e) {
			Logger.logError("Erro ao capturar informações da placa mãe: ", e.getMessage(), e);
			return null;
		}
	}

}