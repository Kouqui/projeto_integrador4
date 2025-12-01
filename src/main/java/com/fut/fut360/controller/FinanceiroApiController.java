package com.fut.fut360.controller;

import com.fut.fut360.Model.Contract;
import com.fut.fut360.Model.PayrollItem;
import com.fut.fut360.Model.Transaction;
import com.fut.fut360.service.ContractService;
import com.fut.fut360.service.PayrollService;
import com.fut.fut360.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ESTE CONTROLLER SERVE APENAS DADOS JSON PARA O FRONTEND (VIA JAVASCRIPT/FETCH).
 * O Controller MVC ('FinanceiroController') serve o HTML.
 */
@Controller

@CrossOrigin(origins = "*")
public class FinanceiroApiController {

    // Injeção de todos os Serviços necessários
    private final TransactionService transactionService;
    private final PayrollService payrollService;
    private final ContractService contractService;

    @Autowired
    public FinanceiroApiController(TransactionService transactionService, PayrollService payrollService, ContractService contractService) {
        this.transactionService = transactionService;
        this.payrollService = payrollService;
        this.contractService = contractService;
    }

    @GetMapping("/financeiro")
    public String exibirPaginaFinanceira() {
        return "Financeiro"; // Retorna o arquivo financeiro.html
    }

    // ======================================================================
    // MÓDULO 1: TRANSAÇÕES FINANCEIRAS (FLUXO DE CAIXA)
    // ENDPOINTS: /api/financeiro/...
    // ======================================================================

    @GetMapping("/api/financeiro/transacoes")
    @ResponseBody
    public ResponseEntity<List<Transaction>> listarTransacoes() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @PostMapping("/api/financeiro/transacoes")
    @ResponseBody
    public ResponseEntity<Transaction> salvarTransacao(@RequestBody Transaction transacao) {
        Transaction salva = transactionService.save(transacao);
        return new ResponseEntity<>(salva, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/financeiro/transacoes/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/financeiro/saldo")
    @ResponseBody
    public ResponseEntity<Map<String, BigDecimal>> calcularSaldo() {
        return ResponseEntity.ok(transactionService.calculateSummary());
    }

    // ======================================================================
    // MÓDULO 2: FOLHA SALARIAL (RECURSOS HUMANOS)
    // ======================================================================

    @GetMapping("/api/rh/payroll")
    @ResponseBody
    public ResponseEntity<List<PayrollItem>> listarPagamentos() {
        return ResponseEntity.ok(payrollService.findAll());
    }

    // ... (restante dos endpoints de Folha Salarial e Contratos) ...
    // ... (incluídos no código anterior, mas omitidos aqui por brevidade) ...

    @PostMapping("/api/rh/payroll")
    @ResponseBody
    public ResponseEntity<PayrollItem> salvarPagamento(@RequestBody PayrollItem item) {
        PayrollItem salva = payrollService.save(item);
        return new ResponseEntity<>(salva, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/rh/payroll/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id) {
        payrollService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/rh/payroll/resumo")
    @ResponseBody
    public ResponseEntity<Map<String, BigDecimal>> obterResumoPagamento() {
        return ResponseEntity.ok(payrollService.calculatePayrollSummary());
    }

    // ======================================================================
    // MÓDULO 3: CONTRATOS DE ATLETAS (RECURSOS HUMANOS)
    // ======================================================================

    @GetMapping("/api/rh/contracts")
    @ResponseBody
    public ResponseEntity<List<Contract>> listarContratos() {
        return ResponseEntity.ok(contractService.findAll());
    }

    @PostMapping("/api/rh/contracts")
    @ResponseBody
    public ResponseEntity<Contract> salvarContrato(@RequestBody Contract contract) {
        // ... (Lógica de validação do contrato) ...
        Contract salva = contractService.save(contract);
        return new ResponseEntity<>(salva, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/rh/contracts/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletarContrato(@PathVariable Long id) {
        contractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
