// --- CONFIGURAÇÃO DA API ---
const API_BASE = 'http://localhost:8080/api/financeiro';
const API_TRANSACTIONS = API_BASE + '/transacoes';
const API_SALDO = API_BASE + '/saldo';

// --- ELEMENTOS DO DOM ---
const receitasTotal = document.getElementById('receitas-total');
const despesasTotal = document.getElementById('despesas-total');
const balancoTotal = document.getElementById('balanco-total');
const transactionsBody = document.getElementById('transactions-body');
const transactionForm = document.getElementById('transaction-form');

const addTransactionBtn = document.getElementById('add-transaction-btn');
const transactionModal = document.getElementById('transaction-modal');
const closeModalBtn = document.getElementById('close-modal-btn');
const cancelBtn = document.getElementById('cancel-btn');


// --- FUNÇÕES DE UTILIDADE ---

function formatCurrency(value) {
    if (typeof value !== 'number') value = parseFloat(value) || 0;
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
}

function formatDate(dateString) {
    if (!dateString) return '';
    // A data do Java vem como YYYY-MM-DD
    const date = new Date(dateString + 'T00:00:00');
    return new Intl.DateTimeFormat('pt-BR').format(date);
}


// --- 1. FUNÇÃO PRINCIPAL DE BUSCA ---

async function fetchFinanceiroData() {
    try {
        // 1. Buscar Saldo (Cartões)
        const summaryResponse = await fetch(API_SALDO);
        const summary = await summaryResponse.json();

        // 2. Buscar Transações (Tabela)
        const transResponse = await fetch(API_TRANSACTIONS);
        const transactions = await transResponse.json();

        // Chamar as funções de renderização
        renderSummary(summary);
        renderTransactions(transactions);

    } catch (error) {
        console.error("Erro ao buscar dados da API Java:", error);
        // Exibir erro ou valores zerados em caso de falha
        transactionsBody.innerHTML = `<tr><td colspan="6" style="text-align:center; color: var(--negative-value);">Falha ao carregar dados. Verifique o servidor Java.</td></tr>`;
        renderSummary({ saldoAtual: 0, totalReceitas: 0, totalDespesas: 0 });
    }
}

// --- 2. FUNÇÕES DE RENDERIZAÇÃO ---

function renderSummary(summary) {
    const saldoAtual = summary.saldoAtual;
    const totalReceitas = summary.totalReceitas;
    const totalDespesas = summary.totalDespesas;

    receitasTotal.textContent = formatCurrency(totalReceitas);
    despesasTotal.textContent = formatCurrency(totalDespesas);
    balancoTotal.textContent = formatCurrency(saldoAtual);

    // Ajustar a cor do Balanço
    balancoTotal.classList.remove('positive', 'negative');
    if (saldoAtual >= 0) {
        balancoTotal.classList.add('positive');
    } else {
        balancoTotal.classList.add('negative');
    }
}

function renderTransactions(transactions) {
    transactionsBody.innerHTML = '';

    // Ordenar as transações pela data (mais recente primeiro)
    transactions.sort((a, b) => new Date(b.date) - new Date(a.date));

    transactions.forEach(t => {
        const isExpense = t.type === 'despesa';
        const valuePrefix = isExpense ? '- ' : '+ ';
        const valueClass = isExpense ? 'negative' : 'positive';
        const typeIcon = isExpense ? 'bxs-up-arrow-circle' : 'bxs-down-arrow-circle';

        const row = `
            <tr>
                <td>${formatDate(t.date)}</td>
                <td>${t.description}</td>
                <td>${t.category}</td>
                <td>
                    <span class="transaction-type ${valueClass}">
                        <i class='bx ${typeIcon}'></i> ${isExpense ? 'Despesa' : 'Receita'}
                    </span>
                </td>
                <td class="${valueClass}">${valuePrefix} ${formatCurrency(t.value)}</td>
                <td class="table-actions">
                    <a href="#" onclick="handleEditClick(${t.id})"><i class='bx bx-edit'></i></a>
                    <a href="#" onclick="handleDeleteClick(${t.id})"><i class='bx bx-trash'></i></a>
                </td>
            </tr>
        `;
        transactionsBody.insertAdjacentHTML('beforeend', row);
    });
}


// --- 3. EVENT LISTENERS E INICIALIZAÇÃO ---

// Iniciar a busca de dados assim que o DOM estiver pronto
document.addEventListener('DOMContentLoaded', fetchFinanceiroData);

// Controle do Modal (Copied from your base script)
addTransactionBtn.addEventListener('click', () => {
    transactionModal.classList.add('visible');
    // NOTE: Você precisará implementar o formulário (transaction-form) no HTML
});

closeModalBtn.addEventListener('click', () => {
    transactionModal.classList.remove('visible');
});

cancelBtn.addEventListener('click', () => {
    transactionModal.classList.remove('visible');
});

transactionModal.addEventListener('click', (event) => {
    if (event.target === transactionModal) {
        transactionModal.classList.remove('visible');
    }
});

// Nota: A lógica de salvar (POST) e deletar (DELETE) precisa ser implementada
// usando 'fetch' para os respectivos endpoints no FinanceiroApiController.java.
// Ex: transactionForm.addEventListener('submit', async (e) => { ... fetch(API_TRANSACTIONS, {method: 'POST'}) ... })