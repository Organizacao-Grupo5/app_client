package service.componente;

import app.system.SystemMonitor;
import dao.componente.CapturaDAO;
import dao.componente.ComponenteDAO;
import model.componentes.*;
import model.Maquina;
import util.logs.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceComponente {
	private ComponenteDAO componenteDAO = new ComponenteDAO();
	private SystemMonitor systemMonitor = new SystemMonitor();
	private CapturaDAO capturaDAO = new CapturaDAO();

	public void obterComponentes(Maquina maquina) {
		try {
			List<Componente> componentesRegistrados = componenteDAO.getComponentes(maquina);

			componentesRegistrados.forEach(componente -> {
				maquina.getComponentes().add(componente);
			});

			List<Componente> listaComponente = new ArrayList<>();

			listaComponente.add(systemMonitor.monitorarCPU());
			systemMonitor.monitorarHDD().forEach(hdd -> listaComponente.add(hdd));
			systemMonitor.monitorarGPU().forEach(gpu -> listaComponente.add(gpu));
			listaComponente.add(systemMonitor.monitorarRAM());
			systemMonitor.monitorarBateria().forEach(bateria -> listaComponente.add(bateria));
			systemMonitor.monitorarDisplay().forEach(janela -> listaComponente.add(janela));
			listaComponente.add(systemMonitor.monitorarSistemaOperacional());
			systemMonitor.monitorarVolumeLogico().forEach(volume -> listaComponente.add(volume));

			listaComponente.forEach(novoComponente -> {
				try {
					boolean existe = false;
					for (Componente registrado : componentesRegistrados) {
						if (registrado.getModelo().equalsIgnoreCase(novoComponente.getModelo())
								&& registrado.getFabricante().equalsIgnoreCase(novoComponente.getFabricante())
								&& registrado.getComponente().equalsIgnoreCase(novoComponente.getComponente())) {
							existe = true;
							break;
						}
					}
					if (!existe) {
						componenteDAO.salvarComponente(maquina, novoComponente);
						maquina.getComponentes().add(novoComponente);
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			});

			if (!componentesRegistrados.isEmpty()) {
				Logger.logInfo("Sua máquina contém componentes registrados");
			} else {
				Logger.logInfo("Todos os componentes da sua máquina foram registrados");
			}

		} catch (Exception e) {
			Logger.logError("Ocorreu um erro ao obter seus componentes:", e.getMessage(), e);
		}
	}

	public void iniciarCapturas(Maquina maquina) {
		try {
			maquina.getComponentes().forEach(componente -> {
				atualizarComponente(componente);
				capturaDAO.inserirCaptura(maquina, componente);
			});
		} catch (Exception e) {
			Logger.logError("Ocorreu um erro durante a captura:", e.getMessage(), e);
		}
	}

	public void atualizarComponente(Componente componente) {
		if (componente instanceof CPU) {
			CPU cpu = systemMonitor.monitorarCPU();
			((CPU) componente).setTemperatura(cpu.getTemperatura());
			((CPU) componente).setDataHoraCaptura(cpu.getDataHoraCaptura());
			((CPU) componente).setIdentificador(cpu.getIdentificador());
			((CPU) componente).setIdCpuLooca(cpu.getIdCpuLooca());
			((CPU) componente).setMicroarquitetura(cpu.getMicroarquitetura());
			((CPU) componente).setNumeroDeCpusFisicas(cpu.getNumeroDeCpusFisicas());
			((CPU) componente).setNumeroPacotesFisicos(cpu.getNumeroPacotesFisicos());
			((CPU) componente).setUso(cpu.getUso());
		} else if (componente instanceof GPU) {
			List<GPU> listaGPU = systemMonitor.monitorarGPU();
			listaGPU.forEach(gpu -> {
				if (componente.getModelo().equalsIgnoreCase(gpu.getModelo())
						&& componente.getFabricante().equalsIgnoreCase(gpu.getFabricante())) {
					((GPU) componente).setTemperatura(gpu.getTemperatura());
					((GPU) componente).setDataHoraCaptura(gpu.getDataHoraCaptura());
					((GPU) componente).setIdDevice(gpu.getIdDevice());
					((GPU) componente).setVersao(gpu.getVersao());
					((GPU) componente).setvRam(gpu.getvRam());
				}
			});
		} else if (componente instanceof HDD) {
			List<HDD> listaHDD = systemMonitor.monitorarHDD();
			listaHDD.forEach(hdd -> {
				if (componente.getModelo().equalsIgnoreCase(hdd.getModelo())
						&& componente.getFabricante().equalsIgnoreCase(hdd.getFabricante())) {
					((HDD) componente).setTamanho(hdd.getTamanho());
					((HDD) componente).setDataHoraCaptura(hdd.getDataHoraCaptura());
					((HDD) componente).setLeituras(hdd.getLeituras());
					((HDD) componente).setSerial(hdd.getSerial());
					((HDD) componente).setBytesDeEscrita(hdd.getBytesDeEscrita());
					((HDD) componente).setEscritas(hdd.getEscritas());
					((HDD) componente).setTempoDeTransferencia(hdd.getTempoDeTransferencia());
					((HDD) componente).setBytesDeLeitura(hdd.getBytesDeLeitura());
					((HDD) componente).setNome(hdd.getNome());
				}
			});
		} else if (componente instanceof Volume) {
			List<Volume> listaVolume = systemMonitor.monitorarVolumeLogico();
			listaVolume.forEach(volume -> {
				if (componente.getModelo().equalsIgnoreCase(volume.getModelo())
						&& componente.getFabricante().equalsIgnoreCase(volume.getFabricante())) {
					((Volume) componente).setDisponivel(volume.getDisponivel());
					((Volume) componente).setDataHoraCaptura(volume.getDataHoraCaptura());
					((Volume) componente).setVolume(volume.getVolume());
					((Volume) componente).setDisponivel(volume.getDisponivel());
					((Volume) componente).setPontoDeMontagem(volume.getPontoDeMontagem());
					((Volume) componente).setTotal(volume.getTotal());
					((Volume) componente).setTipo(volume.getTipo());
					((Volume) componente).setUuid(volume.getUuid());
					((Volume) componente).setFabricante(volume.getFabricante());
					((Volume) componente).setModelo(volume.getModelo());
					((Volume) componente).setDadoCaptura(volume.getDadoCaptura());
				}
			});
		} else if (componente instanceof Bateria) {
			List<Bateria> listaBateria = systemMonitor.monitorarBateria();
			listaBateria.forEach(bateria -> {
				if (componente.getModelo().equalsIgnoreCase(bateria.getModelo())
						&& componente.getFabricante().equalsIgnoreCase(bateria.getFabricante())) {
					((Bateria) componente).setBateriaAtual(bateria.getBateriaAtual());
					((Bateria) componente).setDataHoraCaptura(bateria.getDataHoraCaptura());
					((Bateria) componente).setAmperagem(bateria.getAmperagem());
					((Bateria) componente).setTemperatura(bateria.getTemperatura());
					((Bateria) componente).setCapacidadeAtual(bateria.getCapacidadeAtual());
					((Bateria) componente).setCapacidadeDesign(bateria.getCapacidadeDesign());
					((Bateria) componente).setCapacidadeMaxima(bateria.getCapacidadeMaxima());
					((Bateria) componente).setCiclos(bateria.getCiclos());
					((Bateria) componente).setNomeDispositivo(bateria.getNomeDispositivo());
					((Bateria) componente).setUnidadesCapacidade(bateria.getUnidadesCapacidade());
					((Bateria) componente).setTaxaUsoEnergia(bateria.getTaxaUsoEnergia());
					((Bateria) componente).setNumeroSerial(bateria.getNumeroSerial());
					((Bateria) componente).setQuimica(bateria.getQuimica());
					((Bateria) componente).setVoltagem(bateria.getVoltagem());
					((Bateria) componente).setTempoRestanteEstimado(bateria.getTempoRestanteEstimado());
					((Bateria) componente).setTempoRestanteInstantaneo(bateria.getTempoRestanteInstantaneo());
					((Bateria) componente).setPercentualCapacidadeRestante(bateria.getPercentualCapacidadeRestante());
					((Bateria) componente).setDataFabricacao(bateria.getDataFabricacao());
				}
			});
		} else if (componente instanceof MemoriaRam) {
			MemoriaRam ram = systemMonitor.monitorarRAM();
			((MemoriaRam) componente).setMemoriaEmUso(ram.getMemoriaEmUso());
			((MemoriaRam) componente).setDataHoraCaptura(ram.getDataHoraCaptura());
			((MemoriaRam) componente).setMemoriaDisponivel(ram.getMemoriaDisponivel());
			((MemoriaRam) componente).setMemoriaTotal(ram.getMemoriaTotal());
		} else if (componente instanceof SistemaOp) {
			SistemaOp sistemaOp = systemMonitor.monitorarSistemaOperacional();
			((SistemaOp) componente).setArquitetura(sistemaOp.getArquitetura());
			((SistemaOp) componente).setDataHoraCaptura(sistemaOp.getDataHoraCaptura());
			((SistemaOp) componente).setInicializado(sistemaOp.getInicializado());
			((SistemaOp) componente).setPermissao(sistemaOp.getPermissao());
			((SistemaOp) componente).setTempoDeAtividade(sistemaOp.getTempoDeAtividade());
		}else if (componente instanceof APP) {
			List<APP> listaJanelas = systemMonitor.monitorarDisplay();
			listaJanelas.forEach(janela -> {
				if (((APP) componente).getFabricante().equalsIgnoreCase(janela.getFabricante()) &&
						((APP) componente).getModelo().equalsIgnoreCase(janela.getModelo())){
					((APP) componente).setDataHoraCaptura(janela.getDataHoraCaptura());
					((APP) componente).setLocalizacaoEtamanho(janela.getLocalizacaoEtamanho());
					((APP) componente).setDataCaptura(janela.getDataCaptura());
					((APP) componente).setNome(janela.getNome());
					((APP) componente).setDadoCaptura();
				}
			});
		}
	}

}
