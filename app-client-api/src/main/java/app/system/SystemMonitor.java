package app.system;

import app.integration.ShellIntegration;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.mysql.cj.util.StringUtils;
import model.componentes.*;
import oshi.SystemInfo;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import util.exception.ExceptionMonitoring;
import util.logs.LogBanco;
import util.logs.LogMonitoramento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SystemMonitor {
	private final Looca looca = new Looca();
	private final Conversor conversor = new Conversor();
	private final HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
	private final ShellIntegration shellIntegration = new ShellIntegration();

	public CPU monitorarCPU() throws IOException {
		LogMonitoramento.logInfo("Capturando dados da sua CPU.", LogBanco.LogType.INFO);
		CPU cpu = new CPU();
		try {
			Processador processor = looca.getProcessador();
			cpu.setNumeroDeCpusLogicas(processor.getNumeroCpusLogicas());
			if (cpu.getNumeroDeCpusLogicas() == null) {
				LogMonitoramento.logWarning("Número de CPUs lógicas não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Número de CPUs lógicas: " + cpu.getNumeroDeCpusLogicas(), LogBanco.LogType.INFO);
			}
			cpu.setNumeroDeCpusFisicas(processor.getNumeroCpusFisicas());
			if (cpu.getNumeroDeCpusFisicas() == null) {
				LogMonitoramento.logWarning("Número de CPUs físicas não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Número de CPUs físicas: " + cpu.getNumeroDeCpusFisicas(), LogBanco.LogType.INFO);
			}
			cpu.setMicroarquitetura(processor.getMicroarquitetura());
			if (cpu.getMicroarquitetura() == null || cpu.getMicroarquitetura().isEmpty()) {
				LogMonitoramento.logWarning("Microarquitetura não foi capturada.");
			} else {
				LogMonitoramento.logInfo("Microarquitetura: " + cpu.getMicroarquitetura(), LogBanco.LogType.INFO);
			}
			cpu.setIdentificador(processor.getIdentificador());
			if (cpu.getIdentificador() == null || cpu.getIdentificador().isEmpty()) {
				LogMonitoramento.logWarning("Identificador não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Identificador: " + cpu.getIdentificador(), LogBanco.LogType.INFO);
			}
			cpu.setIdCpuLooca(processor.getId());
			if (cpu.getIdCpuLooca() == null || cpu.getIdCpuLooca().isEmpty()) {
				LogMonitoramento.logWarning("ID da CPU Looca não foi capturado.");
			} else {
				LogMonitoramento.logInfo("ID da CPU Looca: " + cpu.getIdCpuLooca(), LogBanco.LogType.INFO);
			}
			cpu.setFabricante(processor.getFabricante());
			if (cpu.getFabricante() == null || cpu.getFabricante().isEmpty()) {
				LogMonitoramento.logWarning("Fabricante não foi capturado.");
				LogMonitoramento.logInfo("ID da CPU Looca: " + cpu.getIdCpuLooca(), LogBanco.LogType.INFO);
			} else {
				LogMonitoramento.logInfo("Fabricante: " + cpu.getFabricante(), LogBanco.LogType.INFO);
			}
			cpu.setFrequencia(Double.valueOf(processor.getFrequencia()));
			if (cpu.getFrequencia() == null) {
				LogMonitoramento.logWarning("Frequência não foi capturada.");
			} else {
				LogMonitoramento.logInfo("Frequência: " + cpu.getFrequencia(), LogBanco.LogType.INFO);
			}
			cpu.setNumeroPacotesFisicos(processor.getNumeroPacotesFisicos());
			if (cpu.getNumeroPacotesFisicos() == null) {
				LogMonitoramento.logWarning("Número de pacotes físicos não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Número de pacotes físicos: " + cpu.getNumeroPacotesFisicos(), LogBanco.LogType.INFO);
			}
			cpu.setUso(processor.getUso());
			if (cpu.getUso() == null) {
				LogMonitoramento.logWarning("Uso da CPU não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Uso da CPU: " + cpu.getUso(), LogBanco.LogType.INFO);
			}
			cpu.setModelo(processor.getNome());
			if (cpu.getModelo() == null || cpu.getModelo().isEmpty()) {
				LogMonitoramento.logWarning("Nome da CPU não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Nome da CPU: " + cpu.getModelo(), LogBanco.LogType.INFO);
			}
			Double temperatura = shellIntegration.monitorarTemperatura();
			cpu.setPercentDadoCaptura((Optional.ofNullable(temperatura).orElse(0.0) / 100) * 100);
			cpu.setTemperatura(temperatura);
			if (cpu.getTemperatura() == null) {
				LogMonitoramento.logWarning("Temperatura da CPU não foi capturada com sucesso, será apresentada como 0.00 .");
				cpu.setTemperatura(0.00);
			} else {
				LogMonitoramento.logInfo("Temperatura da CPU: " + cpu.getTemperatura(), LogBanco.LogType.INFO);
			}
			cpu.setDadoCaptura(cpu.getTemperatura());
			LogMonitoramento.logInfo("Dados da CPU capturados com sucesso.", LogBanco.LogType.INFO);
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao capturar dados da CPU: ", e.getMessage(), e);
		}
		return cpu;
	}

	public List<APP> monitorarDisplay() throws IOException {
		LogMonitoramento.logInfo("Capturando dados dos seus Apps.", LogBanco.LogType.INFO);
		List<APP> apps = new ArrayList<>();
		try {
			looca.getGrupoDeJanelas().getJanelas().forEach(janela -> {
				APP app = new APP();
				try {
					app.setNome(janela.getTitulo());
					if (StringUtils.isNullOrEmpty(app.getNome())) {
						LogMonitoramento.logWarning("Título da janela não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Título da janela: " + app.getNome(), LogBanco.LogType.INFO);
					}
					app.setComando(janela.getComando());
					if (StringUtils.isNullOrEmpty(app.getComando())) {
						LogMonitoramento.logWarning("Comando da janela não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Comando da janela: " + app.getComando(), LogBanco.LogType.INFO);
					}
					app.setFabricante(janela.getJanelaId().toString());
					if (StringUtils.isNullOrEmpty(app.getFabricante())) {
						LogMonitoramento.logWarning("ID da janela não foi capturado.");
					} else {
						LogMonitoramento.logInfo("ID da janela: " + app.getFabricante(), LogBanco.LogType.INFO);
					}
					app.setModelo(janela.getPid().toString());
					if (StringUtils.isNullOrEmpty(app.getModelo())) {
						LogMonitoramento.logWarning("PID da janela não foi capturado.");
					} else {
						LogMonitoramento.logInfo("PID da janela: " + Optional.ofNullable(app.getModelo()).orElse("PID não encontrado."), LogBanco.LogType.INFO);
					}
					app.setLocalizacaoEtamanho(janela.getLocalizacaoETamanho());
					if (app.getLocalizacaoEtamanho() == null) {
						LogMonitoramento.logWarning("Localização e tamanho da janela não foram capturados.");
					} else {
						LogMonitoramento.logInfo("Localização e tamanho da janela: " + app.getLocalizacaoEtamanho(), LogBanco.LogType.INFO);
					}
					app.setDadoCaptura();
					if (app.getDadoCaptura() == null){
						LogMonitoramento.logWarning("O uso da RAM pela janela %s não foi capturado.".formatted(app.getModelo()));
					}else {
						LogMonitoramento.logInfo("Uso da ram pela janela: " + app.getDadoCaptura(), LogBanco.LogType.INFO);
					}
					LogMonitoramento.logInfo("Dados da janela gravados.", LogBanco.LogType.INFO);
				} catch (Exception e) {
					try {
						LogMonitoramento.logError("Erro ao processar janela: ", e.getMessage(), e);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
				apps.add(app);
			});
			LogMonitoramento.logInfo("Todas as janelas verificadas.", LogBanco.LogType.INFO);
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}
		return apps;
	}

	public List<HDD> monitorarHDD() throws IOException {
		LogMonitoramento.logInfo("Capturando dados do seu HDD", LogBanco.LogType.INFO);
		List<HDD> hdds = new ArrayList<>();

		try {
			looca.getGrupoDeDiscos().getDiscos().forEach(disco -> {
				HDD hdd = new HDD();

				try {
					hdd.setNome(disco.getNome());
					if (StringUtils.isNullOrEmpty(hdd.getNome())) {
						LogMonitoramento.logWarning("Nome do disco não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Nome do disco: " + hdd.getNome(), LogBanco.LogType.INFO);
					}

					hdd.setSerial(disco.getSerial());
					if (StringUtils.isNullOrEmpty(hdd.getSerial())) {
						LogMonitoramento.logWarning("Serial do disco não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Serial do disco: " + hdd.getSerial(), LogBanco.LogType.INFO);
					}

					hdd.setModelo(disco.getModelo());
					if (StringUtils.isNullOrEmpty(hdd.getModelo())) {
						LogMonitoramento.logWarning("Modelo do disco não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Modelo do disco: " + hdd.getModelo(), LogBanco.LogType.INFO);
					}

					double escritas = conversor.formatarBytes(disco.getEscritas());
					hdd.setEscritas(escritas);
					if (escritas == 0) {
						LogMonitoramento.logWarning("Escritas do disco não foram capturadas.");
					} else {
						LogMonitoramento.logInfo("Escritas do disco: " + escritas, LogBanco.LogType.INFO);
					}

					com.github.britooo.looca.api.group.discos.Volume volume = looca.getGrupoDeDiscos().getVolumes().get(0);
					double total = conversor.formatarBytes(volume.getTotal());
					double disponivel = conversor.formatarBytes(volume.getDisponivel());
					double tamanhoDisco = conversor.formatarBytes(disco.getTamanho());

					double percentDadoCaptura = ((total - disponivel) / tamanhoDisco) * 100;
					hdd.setPercentDadoCaptura(percentDadoCaptura);

					double leituras = conversor.formatarBytes(disco.getLeituras());
					hdd.setLeituras(leituras);
					if (leituras == 0) {
						LogMonitoramento.logWarning("Leituras do disco não foram capturadas.");
					} else {
						LogMonitoramento.logInfo("Leituras do disco: " + leituras, LogBanco.LogType.INFO);
					}

					double bytesDeEscrita = conversor.formatarBytes(disco.getBytesDeEscritas());
					hdd.setBytesDeEscrita(bytesDeEscrita);
					if (bytesDeEscrita == 0) {
						LogMonitoramento.logWarning("Bytes de escritas do disco não foram capturados.");
					} else {
						LogMonitoramento.logInfo("Bytes de escritas do disco: " + bytesDeEscrita, LogBanco.LogType.INFO);
					}

					double bytesDeLeitura = conversor.formatarBytes(disco.getBytesDeLeitura());
					hdd.setBytesDeLeitura(bytesDeLeitura);
					if (bytesDeLeitura == 0) {
						LogMonitoramento.logWarning("Bytes de leitura do disco não foram capturados.");
					} else {
						LogMonitoramento.logInfo("Bytes de leitura do disco: " + bytesDeLeitura, LogBanco.LogType.INFO);
					}

					double tamanho = conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanho()), 2);
					hdd.setTamanho(tamanho);
					if (tamanho == 0) {
						LogMonitoramento.logWarning("Tamanho do disco não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Tamanho do disco: " + tamanho, LogBanco.LogType.INFO);
					}

					double tempoDeTransferencia = conversor.converterSegundosParaHoras(disco.getTempoDeTransferencia());
					hdd.setTempoDeTransferencia(tempoDeTransferencia);
					if (tempoDeTransferencia == 0) {
						LogMonitoramento.logWarning("Tempo de transferência do disco não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Tempo de transferência do disco: " + tempoDeTransferencia, LogBanco.LogType.INFO);
					}
					hdd.setDadoCaptura(hdd.getPercentDadoCaptura());

					hdds.add(hdd);
					LogMonitoramento.logInfo("Dados do disco gravados.", LogBanco.LogType.INFO);

				} catch (Exception e) {
					try {
						LogMonitoramento.logError("Erro ao processar dados do HDD: " + e.getMessage(), e);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao acessar informações do sistema: " + e.getMessage(), e);
		}

		return hdds;
	}

	public List<Bateria> monitorarBateria() throws IOException {
		LogMonitoramento.logInfo("Capturando dados da sua bateria.", LogBanco.LogType.INFO);
		List<Bateria> baterias = new ArrayList<>();

		try {
			hardware.getPowerSources().forEach(power -> {
				Bateria bateria = new Bateria();

				try {
					bateria.setAmperagem(power.getAmperage());
					if (bateria.getAmperagem() == null) {
						LogMonitoramento.logWarning("Amperagem da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Amperagem da bateria: " + bateria.getAmperagem(), LogBanco.LogType.INFO);
					}

					bateria.setNomeDispositivo(power.getDeviceName());
					if (StringUtils.isNullOrEmpty(bateria.getNomeDispositivo())) {
						LogMonitoramento.logWarning("Nome do dispositivo da bateria não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Nome do dispositivo da bateria: " + bateria.getNomeDispositivo(), LogBanco.LogType.INFO);
					}

					bateria.setNumeroSerial(power.getSerialNumber());
					if (StringUtils.isNullOrEmpty(bateria.getNumeroSerial())) {
						LogMonitoramento.logWarning("Número de série da bateria não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Número de série da bateria: " + bateria.getNumeroSerial(), LogBanco.LogType.INFO);
					}

					bateria.setQuimica(power.getChemistry());
					if (StringUtils.isNullOrEmpty(bateria.getQuimica())) {
						LogMonitoramento.logWarning("Química da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Química da bateria: " + bateria.getQuimica(), LogBanco.LogType.INFO);
					}

					bateria.setModelo(power.getName());
					if (StringUtils.isNullOrEmpty(bateria.getModelo())) {
						LogMonitoramento.logWarning("Nome da bateria não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Nome da bateria: " + bateria.getModelo(), LogBanco.LogType.INFO);
					}

					bateria.setVoltagem(power.getVoltage());
					if (bateria.getVoltagem() == null) {
						LogMonitoramento.logWarning("Voltagem da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Voltagem da bateria: " + bateria.getVoltagem(), LogBanco.LogType.INFO);
					}

					double capacidadeAtual = conversor.formatarBytes(power.getCurrentCapacity());
					bateria.setCapacidadeAtual(capacidadeAtual);
					if (capacidadeAtual == 0) {
						LogMonitoramento.logWarning("Capacidade atual da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Capacidade atual da bateria: " + capacidadeAtual, LogBanco.LogType.INFO);
					}

					bateria.setCiclos(power.getCycleCount());
					if (bateria.getCiclos() == null) {
						LogMonitoramento.logWarning("Número de ciclos da bateria não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Número de ciclos da bateria: " + bateria.getCiclos(), LogBanco.LogType.INFO);
					}

					double capacidadeDesign = conversor.formatarBytes(power.getDesignCapacity());
					bateria.setCapacidadeDesign(capacidadeDesign);
					if (capacidadeDesign == 0) {
						LogMonitoramento.logWarning("Capacidade de design da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Capacidade de design da bateria: " + capacidadeDesign, LogBanco.LogType.INFO);
					}

					bateria.setUnidadesCapacidade(power.getCapacityUnits().toString());
					if (StringUtils.isNullOrEmpty(bateria.getUnidadesCapacidade())) {
						LogMonitoramento.logWarning("Unidade de capacidade da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Unidade de capacidade da bateria: " + bateria.getUnidadesCapacidade(), LogBanco.LogType.INFO);
					}

					bateria.setTempoRestanteInstantaneo(power.getTimeRemainingInstant());
					if (bateria.getTempoRestanteInstantaneo() == null) {
						LogMonitoramento.logWarning("Tempo restante da bateria (instantâneo) não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Tempo restante da bateria (instantâneo): " + bateria.getTempoRestanteInstantaneo(), LogBanco.LogType.INFO);
					}

					bateria.setTempoRestanteEstimado(power.getTimeRemainingEstimated());
					if (bateria.getTempoRestanteEstimado() == null) {
						LogMonitoramento.logWarning("Tempo restante estimado da bateria não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Tempo restante estimado da bateria: " + bateria.getTempoRestanteEstimado(), LogBanco.LogType.INFO);
					}

					bateria.setTaxaUsoEnergia(power.getPowerUsageRate());
					if (bateria.getTaxaUsoEnergia() == null) {
						LogMonitoramento.logWarning("Taxa de uso de energia da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Taxa de uso de energia da bateria: " + bateria.getTaxaUsoEnergia(), LogBanco.LogType.INFO);
					}

					bateria.setTemperatura(power.getTemperature());
					if (bateria.getTemperatura() == null) {
						LogMonitoramento.logWarning("Temperatura da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Temperatura da bateria: " + bateria.getTemperatura(), LogBanco.LogType.INFO);
					}

					double capacidadeMaxima = conversor.formatarBytes(power.getMaxCapacity());
					bateria.setCapacidadeMaxima(capacidadeMaxima);
					if (capacidadeMaxima == 0) {
						LogMonitoramento.logWarning("Capacidade máxima da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Capacidade máxima da bateria: " + capacidadeMaxima, LogBanco.LogType.INFO);
					}

					Double percentualCapacidadeRestante = conversor.convertePorcentagem(power.getMaxCapacity(),
							power.getRemainingCapacityPercent() * power.getMaxCapacity() / 100);
					bateria.setPercentualCapacidadeRestante(percentualCapacidadeRestante);
					if (percentualCapacidadeRestante == null) {
						LogMonitoramento.logWarning("Porcentagem de capacidade restante da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Porcentagem de capacidade restante da bateria: " + percentualCapacidadeRestante, LogBanco.LogType.INFO);
					}

					String manufactureDateStr = power.getManufactureDate() != null ? power.getManufactureDate().toString() : "N/A";
					bateria.setDataFabricacao(manufactureDateStr);
					if (StringUtils.isNullOrEmpty(bateria.getDataFabricacao())) {
						LogMonitoramento.logWarning("Data de fabricação da bateria não foi capturada.");
					} else {
						LogMonitoramento.logInfo("Data de fabricação da bateria: " + bateria.getDataFabricacao(), LogBanco.LogType.INFO);
					}

					bateria.setFabricante(power.getManufacturer());
					if (StringUtils.isNullOrEmpty(bateria.getFabricante())) {
						LogMonitoramento.logWarning("Fabricante da bateria não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Fabricante da bateria: " + bateria.getFabricante(), LogBanco.LogType.INFO);
					}

					Double bateriaAtual = shellIntegration.monitorarBateria();
					bateria.setBateriaAtual(bateriaAtual);
					bateria.setPercentDadoCaptura(Optional.ofNullable(bateriaAtual).orElse(0.0));
					if (bateriaAtual == null) {
						LogMonitoramento.logWarning("Dados da bateria atual não foram capturados, será apresentada como 0.");
						bateria.setBateriaAtual(0.0);
					} else {
						LogMonitoramento.logInfo("Dados da bateria capturados com sucesso: " + bateriaAtual, LogBanco.LogType.INFO);
					}
					bateria.setDadoCaptura(bateria.getBateriaAtual());

					baterias.add(bateria);
					LogMonitoramento.logInfo("Dados da bateria gravados.", LogBanco.LogType.INFO);

				} catch (Exception e) {
					try {
						LogMonitoramento.logError("Erro ao processar dados da bateria: " + e.getMessage(), e);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao acessar informações do sistema: " + e.getMessage(), e);
		}

		return baterias;
	}





	public List<GPU> monitorarGPU() throws Exception {
		LogMonitoramento.logInfo("Capturando dados da GPU.", LogBanco.LogType.INFO);
		List<GPU> gpus = new ArrayList<>();
		List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();

		try {
			if (graphicsCards == null || graphicsCards.isEmpty()) {
				LogMonitoramento.logWarning("Dados da GPU não puderam ser capturados: nenhuma placa gráfica encontrada.");
			} else {
				graphicsCards.forEach(gpu -> {
					GPU myGpu = new GPU();
					try {
						myGpu.setFabricante(gpu.getVendor());
						if (StringUtils.isNullOrEmpty(myGpu.getFabricante())) {
							LogMonitoramento.logWarning("Não foi possível capturar informações sobre o fabricante da GPU.");
						} else {
							LogMonitoramento.logInfo("Dados do fabricante da GPU capturados com sucesso: " + myGpu.getFabricante(), LogBanco.LogType.INFO);
						}

						myGpu.setIdDevice(gpu.getDeviceId());
						if (StringUtils.isNullOrEmpty(myGpu.getIdDevice())) {
							LogMonitoramento.logWarning("Não foi possível capturar informações sobre o ID do dispositivo.");
						} else {
							LogMonitoramento.logInfo("Dados do ID da GPU capturados com sucesso: " + myGpu.getIdDevice(), LogBanco.LogType.INFO);
						}

						myGpu.setModelo(gpu.getName());
						if (StringUtils.isNullOrEmpty(myGpu.getModelo())) {
							LogMonitoramento.logWarning("Não foi possível capturar informações sobre o nome da GPU.");
						} else {
							LogMonitoramento.logInfo("O nome da GPU foi capturado com sucesso: " + myGpu.getModelo(), LogBanco.LogType.INFO);
						}

						double vRam = conversor.formatarBytes(gpu.getVRam());
						myGpu.setvRam(vRam);
						if (vRam == 0) {
							LogMonitoramento.logWarning("Não foi possível capturar informações sobre a VRam da GPU.");
						} else {
							LogMonitoramento.logInfo("A VRam da GPU foi capturada com sucesso: " + vRam, LogBanco.LogType.INFO);
						}

						myGpu.setVersao(gpu.getVersionInfo());
						if (StringUtils.isNullOrEmpty(myGpu.getVersao())) {
							LogMonitoramento.logWarning("Não foi possível capturar informações sobre a versão da GPU.");
						} else {
							LogMonitoramento.logInfo("A Versão da GPU foi capturada com sucesso: " + myGpu.getVersao(), LogBanco.LogType.INFO);
						}

						Double temperatura = shellIntegration.monitorarTemperatura();
						if (temperatura == null) {
							LogMonitoramento.logWarning("Temperatura da GPU não foi capturada com sucesso, será apresentada como 0.00.");
							temperatura = 0.0;
						} else {
							LogMonitoramento.logInfo("Temperatura da GPU: " + temperatura, LogBanco.LogType.INFO);
						}
						myGpu.setTemperatura(temperatura);
						myGpu.setDadoCaptura(myGpu.getTemperatura());

						myGpu.setPercentDadoCaptura(temperatura);

					} catch (Exception e) {
						try {
							throw new ExceptionMonitoring("Erro ao monitorar GPU" + e.getMessage(), e);
						} catch (ExceptionMonitoring ex) {
							throw new RuntimeException(ex);
						}
					}
					gpus.add(myGpu);
				});
				LogMonitoramento.logInfo("Dados da GPU gravados.", LogBanco.LogType.INFO);
			}
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao acessar informações do sistema: " + e.getMessage(), e);
		}
		return gpus;
	}


	public MemoriaRam monitorarRAM() throws IOException {
		LogMonitoramento.logInfo("Capturando dados da sua memória RAM.", LogBanco.LogType.INFO);
		Memoria memoria = looca.getMemoria();
		if (memoria == null) {
			LogMonitoramento.logWarning("Dados da memória RAM não puderam ser capturados: memória é nula.");
			return null;
		}
		MemoriaRam ram = new MemoriaRam();
		try {
			double memoriaDisponivel = conversor.formatarBytes(memoria.getDisponivel());
			double memoriaTotal = conversor.formatarBytes(memoria.getTotal());
			double memoriaEmUso = conversor.formatarBytes(memoria.getEmUso());

			ram.setMemoriaDisponivel(memoriaDisponivel);
			if (memoriaDisponivel == 0) {
				LogMonitoramento.logWarning("Memória disponível não foi capturada.");
			} else {
				LogMonitoramento.logInfo("Memória disponível: " + memoriaDisponivel, LogBanco.LogType.INFO);
			}

			ram.setMemoriaTotal(memoriaTotal);
			if (memoriaTotal == 0) {
				LogMonitoramento.logWarning("Memória total não foi capturada.");
			} else {
				LogMonitoramento.logInfo("Memória total: " + memoriaTotal, LogBanco.LogType.INFO);
			}

			ram.setMemoriaEmUso(memoriaEmUso);
			if (memoriaEmUso == 0) {
				LogMonitoramento.logWarning("Memória em uso não foi capturada.");
			} else {
				LogMonitoramento.logInfo("Memória em uso: " + memoriaEmUso, LogBanco.LogType.INFO);
			}

			if (memoriaTotal > 0) {
				double percentualUso = (memoriaEmUso / memoriaTotal) * 100;
				ram.setPercentDadoCaptura(percentualUso);
			} else {
				LogMonitoramento.logWarning("Memória total é zero, não é possível calcular o percentual de uso.");
			}
			ram.setDadoCaptura(ram.getMemoriaEmUso());

			LogMonitoramento.logInfo("Dados da memória RAM capturados com sucesso.", LogBanco.LogType.INFO);
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao capturar dados da memória RAM: " + e.getMessage(), e);
		}
		return ram;
	}

	public SistemaOp monitorarSistemaOperacional() throws IOException {
		LogMonitoramento.logInfo("Capturando dados do sistema operacional.", LogBanco.LogType.INFO);
		Sistema sistema = looca.getSistema();
		SistemaOp sistemaOp = new SistemaOp();
		if (sistema == null) {
			LogMonitoramento.logWarning("Dados do sistema operacional não puderam ser capturados: sistema é nulo.");
		}
		try {

			sistemaOp.setModelo(sistema.getSistemaOperacional());
			if (StringUtils.isNullOrEmpty(sistemaOp.getModelo())) {
				LogMonitoramento.logWarning("Não foi possível identificar o nome do sistema operacional");
			} else {
				LogMonitoramento.logInfo("Nome do sistema operacional coletado: " + sistemaOp.getModelo(), LogBanco.LogType.INFO);
			}

			sistemaOp.setFabricante(sistema.getFabricante());
			if (StringUtils.isNullOrEmpty(sistemaOp.getFabricante())) {
				LogMonitoramento.logWarning("Não foi possível identificar o nome do fabricante do sistema operacional");
			} else {
				LogMonitoramento.logInfo("Nome do fabricante do sistema operacional coletado: " + sistemaOp.getFabricante(), LogBanco.LogType.INFO);
			}

			sistemaOp.setArquitetura(sistema.getArquitetura().toString());
			if (StringUtils.isNullOrEmpty(sistemaOp.getArquitetura())) {
				LogMonitoramento.logWarning("Não foi possível identificar a arquitetura do sistema operacional");
			} else {
				LogMonitoramento.logInfo("Arquitetura do sistema operacional coletada: " + sistemaOp.getArquitetura(), LogBanco.LogType.INFO);
			}

			sistemaOp.setInicializado(sistema.getInicializado().toString());
			if (StringUtils.isNullOrEmpty(sistemaOp.getInicializado())) {
				LogMonitoramento.logWarning("Não foi possível identificar a inicialização do sistema operacional");
			} else {
				LogMonitoramento.logInfo("A inicialização do sistema operacional coletada: " + sistemaOp.getArquitetura(), LogBanco.LogType.INFO);
			}

			sistemaOp.setTempoDeAtividade(sistema.getTempoDeAtividade().toString());
			if (StringUtils.isNullOrEmpty(sistemaOp.getTempoDeAtividade())) {
				LogMonitoramento.logWarning("Não foi possível identificar o tempo de atividade do sistema operacional");
			} else {
				LogMonitoramento.logInfo(
						"O tempo de atividade do sistema operacional foi coletada: " + sistemaOp.getTempoDeAtividade(), LogBanco.LogType.INFO);
			}

			sistemaOp.setPermissao(sistema.getPermissao() ? "SIM" : "NÃO");
			if (StringUtils.isNullOrEmpty(sistemaOp.getPermissao())) {
				LogMonitoramento.logWarning("Não foi possível identificar a permissão do sistema operacional");
			} else {
				LogMonitoramento.logInfo("A permisão do sistema operacional foi coletada: " + sistemaOp.getTempoDeAtividade(), LogBanco.LogType.INFO);
			}

		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao capturar dados do seu Sistema Operacional: ", e.getMessage(), e);
		}
		return sistemaOp;
	}



	public List<ConexaoUSB> monitorarUSB() throws IOException {
		LogMonitoramento.logInfo("Capturando dados das suas conexões USB.", LogBanco.LogType.INFO);
		List<ConexaoUSB> usbs = new ArrayList<>();
		try {
			looca.getDispositivosUsbGrupo().getDispositivosUsbConectados().forEach(usb -> {
				try {
					ConexaoUSB conexaoUSB = new ConexaoUSB();
					conexaoUSB.setModelo(usb.getNome());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getModelo())) {
						LogMonitoramento.logWarning("Nome do usb não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Nome do usb capturado: " + conexaoUSB.getModelo(), LogBanco.LogType.INFO);
					}

					conexaoUSB.setModelo(usb.getForncecedor());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getModelo())) {
						LogMonitoramento.logWarning("Fornecedor do usb não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Nome do fornecedor do usb capturado: " + conexaoUSB.getModelo(), LogBanco.LogType.INFO);
					}

					conexaoUSB.setIdDispositivoUSBExclusivo(usb.getIdDispositivoUsbExclusivo());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getIdDispositivoUSBExclusivo())) {
						LogMonitoramento.logWarning("Id exclusivo do dispositivo usb não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Id exclusivo do usb capturado: " + conexaoUSB.getIdDispositivoUSBExclusivo(), LogBanco.LogType.INFO);
					}

					conexaoUSB.setIdFornecedor(usb.getIdFornecedor());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getIdFornecedor())) {
						LogMonitoramento.logWarning("Id do fornecedor do dispositivo usb não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Id do fornecedor do usb capturado: " + conexaoUSB.getIdFornecedor(), LogBanco.LogType.INFO);
					}

					conexaoUSB.setIdProduto(usb.getIdProduto());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getIdProduto())) {
						LogMonitoramento.logWarning("Id do produto não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Id do produto do usb capturado: " + conexaoUSB.getIdProduto(), LogBanco.LogType.INFO);
					}

					conexaoUSB.setNumeroSerie(usb.getNumeroDeSerie());
					if (StringUtils.isNullOrEmpty(conexaoUSB.getNumeroSerie())) {
						LogMonitoramento.logWarning("Número série do usb não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Número série do usb capturado: " + conexaoUSB.getNumeroSerie(), LogBanco.LogType.INFO);
					}

					usbs.add(conexaoUSB);
					LogMonitoramento.logInfo("Dados do usb gravados.", LogBanco.LogType.INFO);
				} catch (Exception e) {
					try {
						LogMonitoramento.logError("Erro ao processar dados do usb: ", e.getMessage(), e);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
			});
			LogMonitoramento.logInfo("Todos os usbs verificadas.", LogBanco.LogType.INFO);
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
		}
		return usbs;
	}

	public List<Volume> monitorarVolumeLogico() throws IOException {
		LogMonitoramento.logInfo("Capturando dados dos volumes lógicos.", LogBanco.LogType.INFO);

		List<Volume> volumes = new ArrayList<>();

		try {
			looca.getGrupoDeDiscos().getVolumes().stream().forEach(volume -> {
				Volume volumeLogico = new Volume();
				try {
					volumeLogico.setModelo(volume.getNome());
					if (StringUtils.isNullOrEmpty(volumeLogico.getModelo())) {
						LogMonitoramento.logWarning("Nome do volume lógico não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Nome do volume lógico capturado: " + volumeLogico.getModelo(), LogBanco.LogType.INFO);
					}

					volumeLogico.setVolume(volume.getVolume());
					if (StringUtils.isNullOrEmpty(volumeLogico.getVolume())) {
						LogMonitoramento.logWarning("Nome do volume não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Nome do volume capturado: " + volumeLogico.getVolume(), LogBanco.LogType.INFO);
					}

					double disponivelBytes = conversor.formatarBytes(volume.getDisponivel());
					double totalBytes = conversor.formatarBytes(volume.getTotal());

					volumeLogico.setDisponivel(disponivelBytes);
					if (StringUtils.isNullOrEmpty(String.valueOf(volumeLogico.getDisponivel()))) {
						LogMonitoramento.logWarning("Espaço disponível no volume lógico não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Espaço disponível no volume lógico capturado: " + volumeLogico.getDisponivel(), LogBanco.LogType.INFO);
					}

					volumeLogico.setDadoCaptura(disponivelBytes - totalBytes);

					volumeLogico.setTotal(totalBytes);
					if (StringUtils.isNullOrEmpty(String.valueOf(volumeLogico.getTotal()))) {
						LogMonitoramento.logWarning("Espaço total do volume lógico não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Espaço total do volume lógico capturado com sucesso: " + volumeLogico.getTotal(), LogBanco.LogType.INFO);
					}

					volumeLogico.setTipo(volume.getTipo());
					if (StringUtils.isNullOrEmpty(volumeLogico.getTipo())) {
						LogMonitoramento.logWarning("Tipo do volume lógico não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Tipo do volume lógico capturado: " + volumeLogico.getTipo(), LogBanco.LogType.INFO);
					}

					volumeLogico.setFabricante(volume.getUUID());
					if (StringUtils.isNullOrEmpty(volumeLogico.getFabricante())) {
						LogMonitoramento.logWarning("UUID do volume lógico não foi capturado.");
					} else {
						LogMonitoramento.logInfo("UUID do volume lógico capturado: " + volumeLogico.getFabricante(), LogBanco.LogType.INFO);
					}

					volumeLogico.setPontoDeMontagem(volume.getPontoDeMontagem());
					if (StringUtils.isNullOrEmpty(volumeLogico.getPontoDeMontagem())) {
						LogMonitoramento.logWarning("Ponto de montagem do volume lógico não foi capturado.");
					} else {
						LogMonitoramento.logInfo("Ponto de montagem do volume lógico capturado: " + volumeLogico.getPontoDeMontagem(), LogBanco.LogType.INFO);
					}

					double percentualUso = ((totalBytes - disponivelBytes) / totalBytes) * 100;
					volumeLogico.setPercentDadoCaptura(percentualUso);
					volumeLogico.setDadoCaptura(disponivelBytes - totalBytes);

					LogMonitoramento.logInfo("Dados do volume lógico gravados.", LogBanco.LogType.INFO);
				} catch (Exception e) {
					try {
						LogMonitoramento.logError("Erro ao processar dados do volume lógico: " + e.getMessage(), e);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
				volumes.add(volumeLogico);
			});
			LogMonitoramento.logInfo("Todos os volumes lógicos verificados.", LogBanco.LogType.INFO);
		} catch (Exception e) {
			LogMonitoramento.logError("Erro ao acessar informações do sistema: " + e.getMessage(), e);
		}

		return volumes;
	}

	public PlacaMae capturarInformacoesPlacaMae() throws IOException {
		SystemInfo systemInfo = new SystemInfo();
		HardwareAbstractionLayer hardware = systemInfo.getHardware();
		ComputerSystem computerSystem = hardware.getComputerSystem();

		String fabricantePlacaMae = computerSystem.getManufacturer();
		String modeloPlacaMae = computerSystem.getModel();

		try {
			if (fabricantePlacaMae == null || fabricantePlacaMae.isEmpty()) {
				LogMonitoramento.logWarning("Fabricante da placa mãe não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Fabricante da placa mãe capturado: " + fabricantePlacaMae, LogBanco.LogType.INFO);
			}

			if (modeloPlacaMae == null || modeloPlacaMae.isEmpty()) {
				LogMonitoramento.logWarning("Modelo da placa mãe não foi capturado.");
			} else {
				LogMonitoramento.logInfo("Modelo da placa mãe capturado: " + modeloPlacaMae, LogBanco.LogType.INFO);
			}

			PlacaMae placaMae = new PlacaMae();
			placaMae.setFabricante(fabricantePlacaMae);
			placaMae.setModelo(modeloPlacaMae);
			LogMonitoramento.logInfo("Dados da placa mãe gravados.", LogBanco.LogType.INFO);
			return placaMae;
		} catch (IllegalArgumentException e) {
			LogMonitoramento.logError("Erro ao capturar informações da placa mãe: ", e.getMessage(), e);
			return null;
		}
	}

}