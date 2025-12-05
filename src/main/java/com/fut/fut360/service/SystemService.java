//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.service;

import com.fut.fut360.Repository.AtletaRepository;
import com.fut.fut360.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean; // Importante para CPU no Windows

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SystemService {

    @Autowired
    private AtletaRepository atletaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    // Mapa de acessos (Thread-safe)
    private final Map<String, Integer> contadoresReais = new ConcurrentHashMap<>();

    // Marca a hora que o servidor ligou
    private final Instant horaInicio = Instant.now();

    // Lista segura para guardar logs (máximo 10)
    private final List<String> systemLogs = new CopyOnWriteArrayList<>();

    public SystemService() {
        // Inicializa contadores
        contadoresReais.put("/dashboard", 0);
        contadoresReais.put("/atletas", 0);
        contadoresReais.put("/financeiro", 0);
        contadoresReais.put("/calendario", 0);

        adicionarLog("Sistema Fut360 inicializado.");
        adicionarLog("Monitoramento de Hardware ativo.");
    }

    // --- AÇÕES DO SISTEMA (BOTÕES) ---

    public void registrarAcesso(String pagina) {
        contadoresReais.merge(pagina, 1, Integer::sum);
        // Log aleatório (10% de chance) para não poluir
        if(new Random().nextInt(10) > 8) {
            adicionarLog("Acesso registrado: " + pagina);
        }
    }

    public void limparLogs() {
        systemLogs.clear();
        adicionarLog("Logs limpos pelo Administrador.");
    }

    public void resetarContadores() {
        contadoresReais.replaceAll((k, v) -> 0);
        adicionarLog("Contadores de tráfego resetados.");
    }

    public void executarGC() {
        System.gc(); // Solicita limpeza de memória RAM ao Java
        adicionarLog("Otimização de RAM executada.");
    }

    public Map<String, Integer> getPaginasMaisAcessadas() { return contadoresReais; }

    // --- SISTEMA DE LOGS ---
    public void adicionarLog(String msg) {
        String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        systemLogs.add(0, "[" + hora + "] " + msg);
        if (systemLogs.size() > 10) systemLogs.remove(systemLogs.size() - 1);
    }

    // --- MONITORAMENTO DE HARDWARE ---
    public Map<String, Object> getServerHealth() {
        long inicioPing = System.currentTimeMillis();
        Map<String, Object> health = new HashMap<>();

        // 1. CPU
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getCpuLoad();
        double cpuValor = (cpuLoad < 0 || Double.isNaN(cpuLoad)) ? 0.0 : (cpuLoad * 100);

        // 2. RAM
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long usedMB = (totalMemory - freeMemory) / (1024 * 1024);
        long totalMB = totalMemory / (1024 * 1024);

        // 3. DISCO (HD)
        File disk = new File(".");
        long totalSpace = disk.getTotalSpace();
        long freeSpace = disk.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;

        long diskTotalGB = totalSpace / (1024 * 1024 * 1024);
        long diskUsedGB = usedSpace / (1024 * 1024 * 1024);
        double diskPercent = (totalSpace > 0) ? ((double) usedSpace / totalSpace * 100) : 0;
        String diskTxt = diskUsedGB + "GB / " + diskTotalGB + "GB";

        // 4. UPTIME & PING
        Duration uptime = Duration.between(horaInicio, Instant.now());
        String tempoLigado = String.format("%02d:%02d:%02d", uptime.toHours(), uptime.toMinutesPart(), uptime.toSecondsPart());
        long latencia = System.currentTimeMillis() - inicioPing;

        // --- PACOTE JSON FINAL ---
        health.put("status", "ONLINE 🟢");
        health.put("uptime", tempoLigado);
        health.put("ping", latencia + "ms");

        health.put("cpuRaw", cpuValor);
        health.put("ramUsed", usedMB);
        health.put("ramTotal", totalMB);

        health.put("diskPercent", diskPercent);
        health.put("diskTxt", diskTxt);

        health.put("logs", systemLogs);
        health.put("activeThreads", Thread.activeCount());

        health.put("totalAtletas", atletaRepository.count());
        health.put("totalEventos", eventoRepository.count());

        return health;
    }
}