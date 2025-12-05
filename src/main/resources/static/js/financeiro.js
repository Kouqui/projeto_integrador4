const API_BASE = 'http://localhost:8080/api';
const API_TRANSACTIONS = API_BASE + '/financeiro/transacoes';
const API_SALDO = API_BASE + '/financeiro/saldo';
const API_PAYROLL = API_BASE + '/rh/payroll';
const API_CONTRACTS = API_BASE + '/rh/contracts';


// Formatador de Moeda (R$)
function formatCurrency(value) {
    if (value === null || value === undefined) return 'R$ 0,00';
    if (typeof value !== 'number') value = parseFloat(value) || 0;
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
}

// Formatador de Data (DD/MM/AAAA)
function formatDate(dateString) {
    if (!dateString) return '';
    // Adiciona horário para garantir que o navegador não volte 1 dia por causa do fuso
    const date = new Date(dateString + 'T00:00:00');
    return new Intl.DateTimeFormat('pt-BR').format(date);
}


window.mudarAba = function(aba) {
    // Esconder todas as seções
    document.getElementById('cashflow-view').style.display = 'none';
    document.getElementById('payroll-view').style.display = 'none';
    document.getElementById('contracts-view').style.display = 'none';

    // Resetar botões para cinza
    document.getElementById('btn-tab-cashflow').className = 'btn-secondary';
    document.getElementById('btn-tab-payroll').className = 'btn-secondary';
    document.getElementById('btn-tab-contracts').className = 'btn-secondary';

    // Ativar a aba selecionada (Azul)
    document.getElementById(aba + '-view').style.display = 'block';
    document.getElementById('btn-tab-' + aba).className = 'btn-primary';

    // Carregar dados da aba específica
    if (aba === 'cashflow') fetchFinanceiroData();
    if (aba === 'payroll') fetchPayroll();
    if (aba === 'contracts') fetchContracts();
}


async function fetchFinanceiroData() {
    try {
        // 1. Buscar Saldo
        const resSaldo = await fetch(API_SALDO);
        const dadosSaldo = await resSaldo.json();

        document.getElementById('receitas-total').textContent = formatCurrency(dadosSaldo.totalReceitas);
        document.getElementById('despesas-total').textContent = formatCurrency(dadosSaldo.totalDespesas);

        const elBalanco = document.getElementById('balanco-total');
        elBalanco.textContent = formatCurrency(dadosSaldo.saldoAtual);

        // Ajustar cor do texto do balanço (Verde ou Vermelho)
        elBalanco.classList.remove('positive', 'negative');
        elBalanco.classList.add(dadosSaldo.saldoAtual >= 0 ? 'positive' : 'negative');

        // 2. Buscar Lista de Transações
        const resTrans = await fetch(API_TRANSACTIONS);
        const transacoes = await resTrans.json();
        const tbody = document.getElementById('transactions-body');
        tbody.innerHTML = '';

        // Ordenar por data (mais recente primeiro)
        transacoes.sort((a, b) => new Date(b.date) - new Date(a.date));

        transacoes.forEach(t => {
            const isExpense = t.type.toLowerCase() === 'despesa';
            const classeValor = isExpense ? 'negative' : 'positive';
            const icone = isExpense ? 'bxs-up-arrow-circle' : 'bxs-down-arrow-circle';
            const sinal = isExpense ? '- ' : '+ ';

            const row = `<tr>
                <td>${formatDate(t.date)}</td>
                <td>${t.description}</td>
                <td>${t.category}</td>
                <td><span class="transaction-type ${classeValor}"><i class='bx ${icone}'></i> ${t.type}</span></td>
                <td class="${classeValor}">${sinal}${formatCurrency(t.value)}</td>
                <td class="table-actions"><a href="#" onclick="deletarTransacao(${t.id})"><i class='bx bx-trash'></i></a></td>
            </tr>`;
            tbody.insertAdjacentHTML('beforeend', row);
        });
    } catch (e) { console.error("Erro Financeiro:", e); }
}

// Listener: Salvar Transação
const formTrans = document.getElementById('transaction-form');
if (formTrans) {
    formTrans.addEventListener('submit', async (e) => {
        e.preventDefault();
        const data = {
            type: document.getElementById('input-tipo').value,
            description: document.getElementById('input-descricao').value,
            value: document.getElementById('input-valor').value,
            date: document.getElementById('input-data').value,
            category: document.getElementById('input-categoria').value,
            responsible: document.getElementById('input-responsavel').value
        };
        // Chama a função genérica que tem SweetAlert
        await enviarDados(API_TRANSACTIONS, data, 'Transação Salva!', 'transaction-modal', fetchFinanceiroData);
    });
}

// Ação: Deletar Transação (Com SweetAlert)
window.deletarTransacao = (id) => {
    Swal.fire({
        title: 'Tem certeza?',
        text: "Essa ação não pode ser desfeita!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim, excluir!',
        cancelButtonText: 'Cancelar'
    }).then(async (result) => {
        if (result.isConfirmed) {
            await fetch(`${API_TRANSACTIONS}/${id}`, { method: 'DELETE' });
            fetchFinanceiroData(); // Atualiza a tabela
            Swal.fire('Deletado!', 'Transação removida.', 'success');
        }
    });
}


async function fetchPayroll() {
    try {
        const res = await fetch(API_PAYROLL);
        const itens = await res.json();

        const resResumo = await fetch(API_PAYROLL + '/resumo');
        const resumo = await resResumo.json();

        document.getElementById('payroll-total').textContent = formatCurrency(resumo.totalCost);
        document.getElementById('payroll-pending').textContent = formatCurrency(resumo.totalPending);

        const tbody = document.getElementById('payroll-body');
        tbody.innerHTML = '';

        itens.forEach(p => {
            const statusStyle = p.status.toLowerCase() === 'pendente' ? 'color: orange;' : 'color: #33ff99;';
            const row = `<tr>
                <td>${p.name}</td>
                <td>${p.role}</td>
                <td>${formatCurrency(p.value)}</td>
                <td>${formatDate(p.paymentDate)}</td>
                <td style="${statusStyle} font-weight:bold;">${p.status.toUpperCase()}</td>
                <td class="table-actions"><a href="#" onclick="deletarPayroll(${p.id})"><i class='bx bx-trash'></i></a></td>
            </tr>`;
            tbody.insertAdjacentHTML('beforeend', row);
        });
    } catch (e) { console.error("Erro Payroll:", e); }
}

// Listener: Salvar Pagamento
const formPayroll = document.getElementById('payroll-form');
if (formPayroll) {
    formPayroll.addEventListener('submit', async (e) => {
        e.preventDefault();
        const data = {
            name: document.getElementById('pay-name').value,
            role: document.getElementById('pay-role').value,
            value: document.getElementById('pay-value').value,
            paymentDate: document.getElementById('pay-date').value,
            status: document.getElementById('pay-status').value
        };
        await enviarDados(API_PAYROLL, data, 'Pagamento Registrado!', 'payroll-modal', fetchPayroll);
    });
}

// Ação: Deletar Payroll (Com SweetAlert)
window.deletarPayroll = (id) => {
    Swal.fire({
        title: 'Remover Pagamento?',
        text: "Confirma a exclusão deste registro?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim, remover!'
    }).then(async (result) => {
        if (result.isConfirmed) {
            await fetch(`${API_PAYROLL}/${id}`, { method: 'DELETE' });
            fetchPayroll();
            Swal.fire('Removido!', 'Registro de pagamento apagado.', 'success');
        }
    });
}

async function fetchContracts() {
    try {
        const res = await fetch(API_CONTRACTS);
        const contratos = await res.json();
        const tbody = document.getElementById('contracts-body');
        tbody.innerHTML = '';

        contratos.forEach(c => {
            // Lógica de Vencimento
            const hoje = new Date();
            const fim = new Date(c.endDate);
            const diffDias = Math.ceil((fim - hoje) / (1000 * 60 * 60 * 24));

            let statusHTML = '<span style="color: #33ff99">Vigente</span>';
            if (diffDias < 0) statusHTML = '<span style="color: #ff4d4d; font-weight:bold">VENCIDO</span>';
            else if (diffDias < 90) statusHTML = `<span style="color: orange; font-weight:bold">Vence em ${diffDias} dias</span>`;

            const row = `<tr>
                <td>${c.name}</td>
                <td>${c.position}</td>
                <td>${formatCurrency(c.salario)}</td>
                <td>${formatDate(c.startDate)}</td>
                <td>${formatDate(c.endDate)}</td>
                <td>${statusHTML}</td>
                <td class="table-actions"><a href="#" onclick="deletarContrato(${c.id})"><i class='bx bx-trash'></i></a></td>
            </tr>`;
            tbody.insertAdjacentHTML('beforeend', row);
        });
    } catch (e) { console.error("Erro Contratos:", e); }
}

// Listener: Salvar Contrato
const formContract = document.getElementById('contract-form');
if (formContract) {
    formContract.addEventListener('submit', async (e) => {
        e.preventDefault();

        // Mapeamento correto para o Java (Contract.java)
        const data = {
            id: null,
            name: document.getElementById('cont-nome').value,
            age: parseInt(document.getElementById('cont-idade').value),
            position: document.getElementById('cont-posicao').value,
            salario: parseFloat(document.getElementById('cont-salary').value),
            startDate: document.getElementById('cont-start').value,
            endDate: document.getElementById('cont-end').value,
            documentUrl: "pendente.pdf"
        };

        await enviarDados(API_CONTRACTS, data, 'Contrato Salvo!', 'contract-modal', fetchContracts);
    });
}

// Ação: Deletar Contrato (Com SweetAlert)
window.deletarContrato = (id) => {
    Swal.fire({
        title: 'Encerrar Contrato?',
        text: "O jogador/funcionário será removido da base.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim, excluir!'
    }).then(async (result) => {
        if (result.isConfirmed) {
            await fetch(`${API_CONTRACTS}/${id}`, { method: 'DELETE' });
            fetchContracts();
            Swal.fire('Excluído!', 'O contrato foi encerrado.', 'success');
        }
    });
}


async function enviarDados(url, data, msgSucesso, modalId, callbackUpdate) {
    try {
        const res = await fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        if (res.ok) {
            // 1. Fecha o Modal
            document.getElementById(modalId).classList.remove('visible');

            // 2. Limpa o formulário
            document.querySelector(`#${modalId} form`).reset();

            // 3. Atualiza a tabela na tela
            callbackUpdate();

            // 4. Mostra alerta
            Swal.fire({
                icon: 'success',
                title: 'Sucesso!',
                text: msgSucesso,
                timer: 1500,
                showConfirmButton: false
            });

        } else {
            // Erro vindo do Java (ex: Idade inválida)
            const errorText = await res.text();
            Swal.fire({
                icon: 'error',
                title: 'Atenção',
                text: errorText // Mostra a mensagem que veio do Java
            });
        }
    } catch (e) {
        console.error(e);
        Swal.fire({
            icon: 'error',
            title: 'Erro de conexão',
            text: 'Não foi possível conectar ao servidor.'
        });
    }
}


window.abrirModalPayroll = () => document.getElementById('payroll-modal').classList.add('visible');
window.fecharModalPayroll = () => document.getElementById('payroll-modal').classList.remove('visible');

window.abrirModalContract = () => document.getElementById('contract-modal').classList.add('visible');
window.fecharModalContract = () => document.getElementById('contract-modal').classList.remove('visible');

// Modal Transação (Controle Manual)
const modalTransacao = document.getElementById('transaction-modal');
const btnAddTransacao = document.getElementById('add-transaction-btn');
const btnCloseTransacao = document.getElementById('close-modal-btn');
const btnCancelTransacao = document.getElementById('cancel-btn');

if (btnAddTransacao) btnAddTransacao.addEventListener('click', () => modalTransacao.classList.add('visible'));
if (btnCloseTransacao) btnCloseTransacao.addEventListener('click', () => modalTransacao.classList.remove('visible'));
if (btnCancelTransacao) btnCancelTransacao.addEventListener('click', () => modalTransacao.classList.remove('visible'));

// Inicialização
window.onload = () => {
    console.log("Financeiro JS carregado.");
    mudarAba('cashflow');
};