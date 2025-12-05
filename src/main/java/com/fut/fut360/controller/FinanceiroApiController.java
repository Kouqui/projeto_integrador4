//Criação pelo autor: Jefferson Andrey Dias Cardoso - Ra: 24017498

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
@CrossOrigin(origins = "*") // Permite acesso de qualquer lugar (útil para testes)
public class FinanceiroApiController {

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
        return "Financeiro";
    }

    // ======================================================================
    // MÓDULO 1: TRANSAÇÕES FINANCEIRAS
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
    // MÓDULO 2: FOLHA SALARIAL
    // ======================================================================

    @GetMapping("/api/rh/payroll")
    @ResponseBody
    public ResponseEntity<List<PayrollItem>> listarPagamentos() {
        return ResponseEntity.ok(payrollService.findAll());
    }

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

    @GetMapping("/api/rh/contracts")
    @ResponseBody
    public ResponseEntity<List<Contract>> listarContratos() {
        return ResponseEntity.ok(contractService.findAll());
    }

    @PostMapping("/api/rh/contracts")
    @ResponseBody
    public ResponseEntity<?> salvarContrato(@RequestBody Contract contract) {
        try {
            // 1. Validação básica de idade (se veio preenchida)
            if (contract.getAge() != null && (contract.getAge() < 16 || contract.getAge() > 100)) {
                return ResponseEntity.badRequest().body("Erro: A idade deve ser entre 16 e 100 anos.");
            }

            // 2. Tenta salvar (O Service vai verificar se o Atleta existe)
            Contract salva = contractService.save(contract);

            // 3. Se der certo, retorna o contrato criado (201 Created)
            return new ResponseEntity<>(salva, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // 4. CAPTURA O ERRO DO SERVICE: "Atleta não encontrado"
            // Retorna erro 400 Bad Request com a mensagem explicativa para o SweetAlert
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            // 5. Erro genérico de servidor
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor ao salvar contrato.");
        }
    }

    @DeleteMapping("/api/rh/contracts/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletarContrato(@PathVariable Long id) {
        contractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}